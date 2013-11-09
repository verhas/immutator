package com.javax0.immutator;

import java.lang.reflect.Method;

import com.javax0.djcproxy.CallbackFilter;

public class FluentMethodFilter implements CallbackFilter {
	private static final FluentMethodFilter INSTANCE = new FluentMethodFilter();

	public static FluentMethodFilter getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean accept(Method method) {
		return method.getReturnType().toString().equals("void")
				|| method.getDeclaringClass().isAssignableFrom(
						method.getReturnType());
	}

}
