package com.stockpredictor;

import java.io.File;
import java.util.Arrays;

import com.stockpredictor.ai.StockNNetwork;

public class Main {
	
	public static void main(String[] args) {
		String symbol = "HMC";
		String filePath = symbol + "\\";
		File file = new File(filePath);
		if(!file.exists())
			file.mkdirs();
		
		String surl = "http://real-chart.finance.yahoo.com/table.csv?s=ACHC&a=02&b=4&c=1994&d=11&e=13&f=2015&g=d&ignore=.csv";
		StockFileParser fileParser = new StockFileParser(surl);
		double[] data = fileParser.getData();
		StockNNetwork nnetwork = new StockNNetwork(symbol + "\\", symbol, 4, 31, data);
		double[] pred = nnetwork.prediction(10, data);
		double[] prediction = fileParser.denormalize(pred);
		System.out.println(Arrays.toString(prediction));
	}

	private static void getNNetwork() {
		// TODO Auto-generated method stub
		
	}	
}
