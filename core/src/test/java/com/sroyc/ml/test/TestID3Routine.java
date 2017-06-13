package com.sroyc.ml.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;

import com.sroyc.ml.supervised.ID3Routine;

public class TestID3Routine {
	private static final Logger LOGGER = Logger.getLogger(TestID3Routine.class.getName());

	private List<String[]> dataset = new ArrayList<>();
	private String[] labels = { "no surfacing", "flippers" };

	@Before
	public void init() {
		dataset.addAll(ResourceReader.read("/shanon_entropy_data_set.txt", "\t"));
	}

	@Test
	public void test() {
		ID3Routine routine = new ID3Routine(dataset, labels);
		LOGGER.log(Level.INFO, "Best Split Index : " + routine.getBestFeature());
	}

}
