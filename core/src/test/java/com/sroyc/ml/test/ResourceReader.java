package com.sroyc.ml.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ResourceReader {

	public static List<String[]> read(String fileName, String separator) {
		List<String[]> dataSet = new ArrayList<String[]>();
		Reader reader = null;
		BufferedReader br = null;
		try {
			InputStream stream = ResourceReader.class.getResourceAsStream(fileName);
			reader = new InputStreamReader(stream);
			br = new BufferedReader(reader);
			String line = null;

			while ((line = br.readLine()) != null) {
				dataSet.add(line.split(separator));
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				br.close();
				reader.close();
			} catch (Exception ex) {
			}
		}
		return dataSet;
	}

}
