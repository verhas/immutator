package com.javax0.immutator;

import java.lang.reflect.Method;

import com.javax0.djcproxy.CallbackFilter;

public class SelectVoidAndSubClassCallbackFilter implements CallbackFilter {
	private static final SelectVoidAndSubClassCallbackFilter INSTANCE = new SelectVoidAndSubClassCallbackFilter();

	public static SelectVoidAndSubClassCallbackFilter getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean accept(Method method) {
		return method.getReturnType().toString().equals("void")
				|| method.getDeclaringClass().isAssignableFrom(
						method.getReturnType());
	}

}
