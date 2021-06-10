package fr.olympa.manager.api.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;

public class ListCompareList<T> {

	BiFunction<T, T, Boolean> compare;
	BiFunction<T, T, Integer> compareInt;

	public ListCompareList() {}

	public ListCompareList<T> compare(BiFunction<T, T, Boolean> compare) {
		this.compare = compare;
		return this;
	}

	public ListCompareList<T> compareInt(BiFunction<T, T, Integer> compareInt) {
		this.compareInt = compareInt;
		return this;
	}

	public boolean contains(Iterable<T> iterable1, Iterable<T> iterable2) {
		if (iterable1 == null || iterable2 == null)
			return false;
		Iterator<T> compareIterator = iterable1.iterator();
		Iterator<T> thisIterator;
		T compareNext, thisCompare;
		while (compareIterator.hasNext()) {
			compareNext = compareIterator.next();
			thisIterator = iterable2.iterator();
			while (thisIterator.hasNext()) {
				thisCompare = thisIterator.next();
				if (compare.apply(thisCompare, compareNext))
					return true;
			}
		}
		return false;
	}

	public Map<T, T> getContains(Iterator<T> it1, Iterator<T> it2) {
		Map<T, T> r = new HashMap<>();
		T t1, t2;
		while (it1.hasNext()) {
			t1 = it1.next();
			while (it2.hasNext()) {
				t2 = it2.next();
				if (compare.apply(t2, t1))
					r.put(t1, t2);
			}
		}
		return r;
	}

	public Map<T, T> getContains(Iterable<T> iterable1, Iterable<T> iterable2) {
		if (iterable1 == null || iterable2 == null)
			return null;
		return getContains(iterable1.iterator(), iterable2.iterator());
	}
}
