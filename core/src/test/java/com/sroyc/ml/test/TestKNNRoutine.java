package com.sroyc.ml.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sroyc.ml.supervised.KNNRoutine;
import com.sroyc.ml.util.MLUtil;

public class TestKNNRoutine {

	private static final Logger LOGGER = Logger.getLogger(TestKNNRoutine.class.getName());
	private final List<String[]> dataSet = new ArrayList<String[]>();
	private final List<String[]> unknowns = new ArrayList<String[]>();
	private final List<String[]> knowns = new ArrayList<String[]>();
	private int dimension;
	private final int NBR_OF_TEST_DATA = 100;

	@Before
	public void init() {
		Reader reader = null;
		BufferedReader br = null;
		try {
			reader = new InputStreamReader(getClass().getResourceAsStream("/knn_data_set.txt"));
			br = new BufferedReader(reader);
			String line = null;

			while ((line = br.readLine()) != null) {
				dataSet.add(line.split("\t"));
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
		Assert.assertTrue(dataSet.size() > 0);
	}

	@Test
	public void test() {
		double errPercAvg = 0.0;
		for (int i = 0; i < NBR_OF_TEST_DATA; i++) {
			errPercAvg += this.extract();
		}
		errPercAvg /= 100;
		LOGGER.log(Level.INFO, "Average Error percentage is : " + errPercAvg);
	}

	private double extract() {
		this.knowns.clear();
		this.unknowns.clear();
		this.knowns.addAll(this.dataSet);
		List<String[]> unknown = new ArrayList<String[]>();
		int count = 0;
		while (count++ < NBR_OF_TEST_DATA) {
			unknown.add(knowns.remove((int) MLUtil.random(0, knowns.size())));
		}
		this.unknowns.addAll(unknown);
		Assert.assertTrue(this.unknowns.size() == NBR_OF_TEST_DATA);
		this.dimension = dataSet.get(0).length;
		return testKNN();
	}

	private double testKNN() {
		String[] ukLabels = unknownLabels();
		KNNRoutine routine = new KNNRoutine(knowns(), knownLabels(), 5);
		String[] results = routine.build(unknown());
		int incorrect = 0;
		for (int i = 0; i < NBR_OF_TEST_DATA; i++) {
			if (!ukLabels[i].equals(results[i])) {
				incorrect++;
			}
		}
		double errPercent = (double) incorrect / NBR_OF_TEST_DATA;
		return errPercent;
	}

	private double[][] unknown() {
		double[][] unknown = new double[NBR_OF_TEST_DATA][dimension - 1];
		for (int i = 0; i < NBR_OF_TEST_DATA; i++) {
			for (int j = 0; j < this.dimension - 1; j++) {
				unknown[i][j] = Double.parseDouble(unknowns.get(i)[j]);
			}
		}
		return unknown;
	}

	private double[][] knowns() {
		double[][] known = new double[knowns.size()][dimension - 1];
		for (int i = 0; i < knowns.size(); i++) {
			for (int j = 0; j < this.dimension - 1; j++) {
				known[i][j] = Double.parseDouble(knowns.get(i)[j]);
			}
		}
		return known;
	}

	private String[] knownLabels() {
		String[] labels = new String[knowns.size()];
		for (int i = 0; i < knowns.size(); i++) {
			labels[i] = knowns.get(i)[dimension - 1];
		}
		return labels;
	}

	private String[] unknownLabels() {
		String[] labels = new String[unknowns.size()];
		for (int i = 0; i < unknowns.size(); i++) {
			labels[i] = unknowns.get(i)[dimension - 1];
		}
		return labels;
	}

}
