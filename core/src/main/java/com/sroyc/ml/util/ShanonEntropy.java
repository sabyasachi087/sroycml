package com.sroyc.ml.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ShanonEntropy {

	private List<String> labels = new ArrayList<>();
	private Map<String, Integer> labelCountMap = new HashMap<>();
	private int size;

	private static final Integer FLOATING_POINT_MULTPLIER = 1000000;

	public ShanonEntropy(List<String> labels) {
		this.labels = labels;
		this.populateLabelMap();
		this.size = this.labels.size();
	}

	private void populateLabelMap() {
		for (String label : labels) {
			labelCountMap.put(label, labelCountMap.get(label) == null ? 1 : (labelCountMap.get(label) + 1));
		}
	}

	public double calculate() {
		double shanonEntropy = 0.0, prob;
		for (Entry<String, Integer> entry : this.labelCountMap.entrySet()) {
			prob = ((double) entry.getValue() / size);
			shanonEntropy -= prob * this.log(prob, 2);
		}
		return shanonEntropy;
	}

	private double log(double x, int base) {
		double result = 0.0;
		result = ((Math.log(x) * FLOATING_POINT_MULTPLIER) / (Math.log(base) * FLOATING_POINT_MULTPLIER));
		return result;
	}

}
