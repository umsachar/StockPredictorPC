package com.stockpredictor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class StockFileParser {
	
	private double[] data;
	private double max;
	
	public StockFileParser(String surl) {
		max = Double.MIN_VALUE;
		LinkedList<Double> input = new LinkedList<Double>();

		try {
			URL url = new URL(surl);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = br.readLine();
			line = br.readLine();

			while (line != null) {
				String[] lineArr = line.split(",");
				double endPrice = Double.parseDouble(lineArr[lineArr.length - 1]);
				max = Math.max(max, endPrice);
				input.addFirst(endPrice);
				line = br.readLine();
			}

			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		data = new double[input.size()];
		for(int i = 0; i < input.size(); i++)
			data[i] = (input.get(i) / max * 0.8) + 0.1;
	}

	public double[] getData() {
		return data;
	}

	public double getMax() {
		return max;
	}

	public double[] denormalize(double[] pred) {
		double[] predictions = new double[pred.length];
		
		for(int i = 0; i < pred.length; i++)
			predictions[i] = (pred[i] - 0.1) / 0.8 * max;
		
		return predictions;
	}
}
