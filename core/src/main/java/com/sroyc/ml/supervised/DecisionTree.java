package com.sroyc.ml.supervised;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

public abstract class DecisionTree implements SupervisedRoutine {

	/**
	 * This method splits the dataset on the given axis(index) with the matching
	 * value. Assume last index of each record is the label.
	 */
	protected List<String[]> splitDataset(List<String[]> dataset, int axis, String value) {
		List<String[]> splitSet = new ArrayList<>();
		int features = dataset.get(0).length - 1;
		String[] splitRecord;
		int count = 0;
		Assert.assertTrue("Invalid axis considering the last index as label", axis < features);
		for (String[] record : dataset) {
			splitRecord = new String[features];
			count = 0;
			for (int i = 0; i < features; i++) {
				if (axis != i && record[i].equals(value)) {
					splitRecord[count++] = record[i];
				}
			}
			splitSet.add(splitRecord);
		}
		return splitSet;
	}

}
