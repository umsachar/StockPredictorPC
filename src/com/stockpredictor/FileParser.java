package com.stockpredictor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class FileParser {
	
	private double[] input;
	
	public FileParser(String surl) {
		try {
			URL url = new URL(surl);
			InputStream is = url.openStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(is));
			String s = dis.readLine();
			System.out.println(s);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
