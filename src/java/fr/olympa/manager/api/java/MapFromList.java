package fr.olympa.manager.api.java;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

public class MapFromList<K, V> extends HashMap<K, V> {

	private static final long serialVersionUID = -8145098086718132719L;

	@NotNull
	protected Function<V, K> functionForKey;

	public MapFromList(Function<V, K> functionForKey) {
		this.functionForKey = functionForKey;
	}

	public MapFromList(List<V> list, Function<V, K> functionForKey) {
		this(functionForKey);
		putAll(list.stream().collect(Collectors.toMap(v -> functionForKey.apply(v), v -> v)));
	}

	public V add(V v) {
		return put(functionForKey.apply(v), v);
	}

	//	@Override
	//	public boolean equals(Object obj) {
	//		if (!super.equals(obj))
	//			return false;
	//		@SuppressWarnings("unchecked")
	//		MapFromList<K, V> fobj = (MapFromList<K, V>) obj;
	//		if (functionForKey.equals(fobj.functionForKey))
	//			return true;
	//		return false;
	//	}
}
