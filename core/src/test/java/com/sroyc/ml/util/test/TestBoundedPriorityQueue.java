package com.sroyc.ml.util.test;

import org.junit.Assert;
import org.junit.Test;

import com.sroyc.ml.util.BoundedPriorityQueue;
import com.sroyc.ml.util.MLUtil;

public class TestBoundedPriorityQueue {

	public static final BoundedPriorityQueue<NNHolder> QUEUE = new BoundedPriorityQueue<TestBoundedPriorityQueue.NNHolder>(
			20);

	@Test
	public void test() {
		for (int i = 0; i < 200; i++) {
			QUEUE.add(new NNHolder(i, MLUtil.random(1, 1000)));
		}
		int count = 0;
		for (NNHolder nh : QUEUE) {
			System.out.println(nh);
			count++;
		}
		Assert.assertTrue(count == 20);
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

		@Override
		public String toString() {
			return "NNHolder [label=" + label + ", euclideanDistance=" + euclideanDistance + "]";
		}

	}

}
