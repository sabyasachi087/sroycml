package com.sroyc.ml.util.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.sroyc.ml.test.ResourceReader;
import com.sroyc.ml.util.ShanonEntropy;

public class TestShanonEntropy {

	private static final Logger LOGGER = Logger.getLogger(TestShanonEntropy.class.getName());
	private static final List<String[]> dataSet = new ArrayList<>();

	@Before
	public void init() {
		dataSet.addAll(ResourceReader.read("/shanon_entropy_data_set.txt", "\t"));
	}

	@Test
	public void test() {
		List<String> labels = new ArrayList<>();
		int dimension = dataSet.get(0).length;
		for (String[] ds : dataSet) {
			labels.add(ds[dimension - 1]);
		}
		ShanonEntropy sh = ShanonEntropy.loadFromLabels(labels);
		LOGGER.log(Level.INFO, "Shenon Entropy = " + sh.calculate());
	}

}
