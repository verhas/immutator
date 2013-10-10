package com.javax0.immutator;

import java.lang.reflect.Method;

import com.javax0.djcproxy.CallbackFilter;

public class SelectVoidAndThisCallbackFilter implements CallbackFilter {
	private static final SelectVoidAndThisCallbackFilter INSTANCE = new SelectVoidAndThisCallbackFilter();

	public static SelectVoidAndThisCallbackFilter getInstance() {
		return INSTANCE;
	}

	@Override
	public boolean accept(Method method) {
		return method.getReturnType().toString().equals("void")
				|| method.getDeclaringClass().equals(method.getReturnType());
	}

}
