package com.citsh.id;
public interface IdGenerator {
	Long generateId();

	Long generateId(String name);

	Long generateId(Class<?> clz);
}
