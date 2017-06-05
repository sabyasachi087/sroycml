package com.sroyc.ml.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class SortedArrayList<E extends Comparable<E>> extends ArrayList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6482889707627430143L;
	private Comparator<E> comparator;

	public SortedArrayList(Comparator<E> comparator) {
		super();
		this.comparator = comparator;
	}

	public SortedArrayList() {
		super();
	}

	@Override
	public boolean add(E e) {
		if (this.comparator != null) {
			int indx = this.searchIndexComparator(e);
			super.add(indx, e);
		} else {
			int indx = this.searchIndexComparable(e);
			super.add(indx, e);
		}
		return true;
	}

	private int searchIndexComparable(E e) {
		int midIndx = 0;
		if (size() == 0) {
			return 0;
		}
		int upperIndx = size() - 1, lowerIndx = 0;
		while (true) {
			if ((upperIndx - lowerIndx) == 1) {
				if (this.get(upperIndx).compareTo(e) == -1) {
					return ++upperIndx;
				} else if (this.get(lowerIndx).compareTo(e) == 1) {
					return lowerIndx;
				} else {
					return ++lowerIndx;
				}
			} else if (upperIndx == 0) {
				if (this.get(upperIndx).compareTo(e) == 1) {
					return upperIndx;
				} else {
					return ++upperIndx;
				}
			}
			midIndx = (upperIndx + lowerIndx) / 2;
			if (this.get(midIndx).compareTo(e) == 1) {
				upperIndx = midIndx;
			} else if (this.get(midIndx).compareTo(e) == -1) {
				lowerIndx = midIndx;
			} else {
				return midIndx;
			}

		}
	}

	private int searchIndexComparator(E e) {
		int midIndx = 0;
		if (size() == 0) {
			return 0;
		}
		int upperIndx = size() - 1, lowerIndx = 0;
		while (true) {
			if ((upperIndx - lowerIndx) == 1) {
				if (this.comparator.compare(this.get(upperIndx), e) == -1) {
					return ++upperIndx;
				} else if (this.comparator.compare(this.get(upperIndx), e) == 1) {
					return lowerIndx;
				} else {
					return ++lowerIndx;
				}
			} else if (upperIndx == 0) {
				if (this.comparator.compare(this.get(upperIndx), e) == 1) {
					return upperIndx;
				} else {
					return ++upperIndx;
				}
			}
			midIndx = (upperIndx + lowerIndx) / 2;
			if (this.comparator.compare(this.get(upperIndx), e) == 1) {
				upperIndx = midIndx;
			} else if (this.comparator.compare(this.get(upperIndx), e) == -1) {
				lowerIndx = midIndx;
			} else {
				return midIndx;
			}

		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E e : c) {
			this.add(e);
		}
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new RuntimeException("Method not allowed for this data structure !!");
	}

	@Override
	public E set(int index, E element) {
		throw new RuntimeException("Method not allowed for this data structure !!");
	}

	@Override
	public void add(int index, E element) {
		throw new RuntimeException("Method not allowed for this data structure !!");
	}

}