package com.stockpredictor.ai;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.LMS;

public class StockNNetwork {
	
	private final int MAX_ITER = 10000;
	private final String EXT = ".nnet";
	
	private String savePath;
	private NeuralNetwork nnetwork;
	
	public StockNNetwork(String savePath, String symbol, int inputNode, int hiddenNode, double[] input) {
		this.savePath = savePath + inputNode + symbol + hiddenNode + EXT;
		File saveFile = new File(savePath);
		
		if(saveFile.exists()) {
			readNetwork();
		} else {
			makeNetwork(inputNode, hiddenNode, input);
			saveNetwork();
		}
	}
	
	private void makeNetwork(int inputNode, int hiddenNode, double[] input) {
		nnetwork = new MultiLayerPerceptron(inputNode, hiddenNode, 1);
        ((LMS) nnetwork.getLearningRule()).setMaxError(0.001);
        ((LMS) nnetwork.getLearningRule()).setLearningRate(0.7);
        ((LMS) nnetwork.getLearningRule()).setMaxIterations(MAX_ITER);
        
        DataSet learningSet = new DataSet(inputNode);
		int index = 0;
		while(index < input.length) {
			if(index + inputNode + 1 < input.length) {
				double[] smallInput = Arrays.copyOfRange(input, index, index + inputNode);
				index += inputNode + 1;
				double[] output = {input[index]};
			}
		}
		
		nnetwork.learn(learningSet);
	}

	private void readNetwork() {
		Thread t = new Thread(null, ReadNetwork, "ReadNetwork", 256000);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private Runnable ReadNetwork = new Runnable() {

		public void run() {
			try {
				InputStream inputStream = new FileInputStream(savePath);				
				ObjectInputStream iStream = new ObjectInputStream(inputStream);
				nnetwork = (NeuralNetwork) iStream.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	};
	
	public void saveNetwork() {
		Thread t = new Thread(null, SaveNetwork, "SaveNetwork", 32000);
		t.start();
	}
	
	private Runnable SaveNetwork = new Runnable() {

		public void run() {
			try {
				 ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(new File(savePath)));
				 oStream.writeObject(nnetwork);
				 oStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};
}
