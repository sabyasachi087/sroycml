package com.sroyc.ml.util;

import java.util.Iterator;

public class BoundedPriorityQueue<E extends Comparable<E>> implements Iterable<E> {

	private final int limit;
	private int size;
	private final SortedArrayList<E> DATA_STRUCTURE = new SortedArrayList<E>();

	public BoundedPriorityQueue(int limit) {
		this.limit = limit;
	}

	public void add(E e) {
		this.DATA_STRUCTURE.add(e);
		size++;
		while (size > limit) {
			this.DATA_STRUCTURE.remove(--size);
		}
	}

	public E poll() {
		size--;
		return DATA_STRUCTURE.remove(0);
	}

	public int size() {
		return size;
	}

	public E first() {
		return DATA_STRUCTURE.get(0);
	}

	public E last() {
		return DATA_STRUCTURE.get(size - 1);
	}

	public E get(int index) {
		return DATA_STRUCTURE.get(index);
	}

	public void clear() {
		DATA_STRUCTURE.clear();
		size = 0;
	}

	public Iterator<E> iterator() {
		return DATA_STRUCTURE.iterator();
	}

}
