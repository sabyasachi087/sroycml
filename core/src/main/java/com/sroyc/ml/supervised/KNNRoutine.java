package com.sroyc.ml.supervised;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sroyc.ml.util.BoundedPriorityQueue;
import com.sroyc.ml.util.MLUtil;

public class KNNRoutine implements SupervisedRoutine {

	private static final Logger LOGGER = Logger.getLogger(KNNRoutine.class.getName());
	private Integer K = 20;
	private final double[][] known;
	private int[] optLabels;
	private int dimensions;
	private int size;
	private Map<Integer, String> LABEL_MAP = new HashMap<Integer, String>();
	private final double[] autoNorm, min;

	public KNNRoutine(double[][] known, String[] labels, Integer K) {
		this.size = known.length;
		if (K >= this.size) {
			throw new IllegalArgumentException("Invalid K value , must be less than the size of the data set .");
		}
		this.dimensions = (known[0]).length;
		this.autoNorm = this.initialize(new double[this.dimensions], Double.MAX_VALUE);
		this.min = this.initialize(new double[this.dimensions], Double.MAX_VALUE);
		this.defineAutoNorm(known);
		this.known = this.normalize(known);
		this.optLabels = new int[labels.length];
		this.optimizeLabels(labels);
		this.K = K;
	}

	public String[] build(double[][] unknown) {
		double[][] normUnknown = this.normalize(unknown);
		String[] labels = new String[unknown.length];
		for (int i = 0; i < normUnknown.length; i++) {
			int[] nnIndx = this.nearestNeighbours(normUnknown[i]);
			labels[i] = LABEL_MAP.get(this.vote(nnIndx));
		}
		return labels;
	}

	private int vote(int[] nnIndx) {
		int highestIndex = Integer.MIN_VALUE;
		Map<Integer, Integer> voteMap = new HashMap<Integer, Integer>();
		for (int nnLabel : nnIndx) {
			voteMap.put(nnLabel, voteMap.get(nnLabel) != null ? (voteMap.get(nnLabel) + 1) : 1);
		}
		for (int key : voteMap.keySet()) {
			if (voteMap.get(key) > highestIndex) {
				highestIndex = key;
			}
		}
		return highestIndex;
	}

	private double[] initialize(double[] data, double value) {
		for (int i = 0; i < data.length; i++) {
			data[i] = value;
		}
		return data;
	}

	private void defineAutoNorm(double[][] dataset) {
		double[] max = this.initialize(new double[this.dimensions], Double.MIN_VALUE);
		for (int j = 0; j < dimensions; j++) {
			for (int i = 0; i < size; i++) {
				if (dataset[i][j] < min[j]) {
					min[j] = dataset[i][j];
				}
				if (dataset[i][j] > max[j]) {
					max[j] = dataset[i][j];
				}
			}
			autoNorm[j] = max[j] - min[j];
		}
		LOGGER.log(Level.CONFIG, "Min norm value : " + Arrays.toString(min));
		LOGGER.log(Level.CONFIG, "Auto norm value : " + Arrays.toString(autoNorm));
	}

	private double[][] normalize(double[][] dataset) {
		double[][] newKnown = new double[dataset.length][dimensions];
		for (int i = 0; i < dataset.length; i++) {
			for (int j = 0; j < dimensions; j++) {
				newKnown[i][j] = (dataset[i][j] - min[j]) / autoNorm[j];
			}
		}
		return newKnown;
	}

	private void optimizeLabels(String[] origLabels) {
		Map<String, Integer> LABEL_MAP_REVERSED = new HashMap<String, Integer>();
		Integer label = 0, currentLabel;
		for (int i = 0; i < origLabels.length; i++) {
			if ((currentLabel = LABEL_MAP_REVERSED.get(origLabels[i])) == null) {
				currentLabel = label++;
				LABEL_MAP_REVERSED.put(origLabels[i], currentLabel);
				LABEL_MAP.put(currentLabel, origLabels[i]);
			}
			this.optLabels[i] = currentLabel;
		}
	}

	private int[] nearestNeighbours(double[] normalizedUnknown) {
		int[] nnIndx = new int[K];
		BoundedPriorityQueue<NNHolder> knn = new BoundedPriorityQueue<NNHolder>(K);
		for (int i = 0; i < size; i++) {
			knn.add(new NNHolder(this.optLabels[i], MLUtil.euclideanDistance(known[i], normalizedUnknown)));
		}
		for (int i = 0; i < K; i++) {
			nnIndx[i] = knn.get(i).label;
		}
		return nnIndx;
	}

	private static class NNHolder implements Comparable<NNHolder> {
		private int label;
		private Double euclideanDistance;

		protected NNHolder(int label, double euclideanDistance) {
			this.label = label;
			this.euclideanDistance = euclideanDistance;
		}

		public int compareTo(NNHolder o) {
			return this.euclideanDistance.compareTo(o.euclideanDistance);
		}

	}

}
