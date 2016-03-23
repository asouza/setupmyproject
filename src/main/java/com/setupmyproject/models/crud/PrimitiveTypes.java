package com.setupmyproject.models.crud;

import java.util.Set;

import com.google.common.collect.Sets;

public class PrimitiveTypes {

	private Set<String> types = Sets
			.newHashSet(int.class.getName(), double.class.getName(),
					float.class.getName(), boolean.class.getName(),
					byte.class.getName(), char.class.getName(),long.class.getName());

	public boolean accepts(String type) {
		return types.contains(type);
	}

}
