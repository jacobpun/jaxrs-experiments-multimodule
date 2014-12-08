package org.punnoose.jersey.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;

public final class DozerHelper {

	private DozerHelper() {}
	public static <T, U> List<U> map(final Mapper mapper,
			final List<T> source, final Class<U> destType) {

		final ArrayList<U> dest = new ArrayList<U>();

		for (T element : source) {
			if (element == null) {
				continue;
			}
			dest.add(mapper.map(element, destType));
		}

		// finally remove all null values if any
		List<U> s1 = new ArrayList<U>();
		s1.add(null);
		dest.removeAll(s1);

		return dest;
	}
}