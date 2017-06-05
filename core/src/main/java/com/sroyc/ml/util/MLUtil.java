package com.sroyc.ml.util;

public class MLUtil {

	private MLUtil() {
		throw new IllegalAccessError("Access is not allowed !!");
	}

	public static double euclideanDistance(double[] X, double[] Y) {
		double distance = 0.0d;
		int dimension = X.length;
		if (Y.length != dimension) {
			throw new IllegalArgumentException("All cordinates should have same dimensions");
		}
		for (int i = 0; i < dimension; i++) {
			distance = distance + Math.pow((X[i] - Y[i]), 2.0);
		}
		return Math.pow(distance, 0.5);
	}

	public static long random(long min, long max) {
		double rand = Math.random();
		return (long) (rand * max + min);
	}

}
