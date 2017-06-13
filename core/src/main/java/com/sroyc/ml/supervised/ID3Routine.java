package com.sroyc.ml.supervised;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sroyc.ml.util.ShanonEntropy;

public class ID3Routine extends DecisionTree {

	private static final Logger LOGGER = Logger.getLogger(ID3Routine.class.getName());

	private final List<String[]> dataset;
	private final int totalNumFeatures;
	private final double baseEntropy;
	private final int dsSize;
	private String[] labels;

	public ID3Routine(List<String[]> dataset, String[] labels) {
		super();
		this.dataset = dataset;
		this.totalNumFeatures = this.dataset.get(0).length - 1;
		this.baseEntropy = ShanonEntropy.loadFromDataset(dataset).calculate();
		this.dsSize = this.dataset.size();
		this.labels = labels;
		LOGGER.log(Level.INFO, "ID3Routine has been initialized !");
	}

	/**
	 * This method finds the best feature (axis) to split by comparing there
	 * entropy values
	 */
	public int getBestFeature() {
		int axis = -1;
		double bestInfoGain = 0.0, infoGain = 0.0;
		double newEntropy = 0.0;
		List<String[]> splitDataset;
		for (int featureIndx = 0; featureIndx < this.totalNumFeatures; featureIndx++) {
			newEntropy = 0.0;
			for (String feature : this.getUniqueValues(featureIndx)) {
				splitDataset = splitDataset(dataset, featureIndx, feature);
				newEntropy += ((splitDataset.size() / this.dsSize)
						* ShanonEntropy.loadFromDataset(splitDataset).calculate());
			}
			infoGain = baseEntropy - newEntropy;
			if (infoGain > bestInfoGain) {
				bestInfoGain = infoGain;
				axis = featureIndx;
			}
		}
		return axis;
	}

	private Set<String> getUniqueValues(int axis) {
		Set<String> uniqueDs = new HashSet<>();
		for (String[] record : this.dataset) {
			uniqueDs.add(record[axis]);
		}
		return uniqueDs;
	}

}
