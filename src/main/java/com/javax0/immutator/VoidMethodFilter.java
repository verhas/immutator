package com.javax0.immutator;

import java.lang.reflect.Method;

import com.javax0.djcproxy.CallbackFilter;

public class VoidMethodFilter implements CallbackFilter{
	private static final VoidMethodFilter INSTANCE = new VoidMethodFilter();

	public static VoidMethodFilter getInstance() {
		return INSTANCE;
	}
	@Override
	public boolean accept(Method method) {
		return method.getReturnType().toString().equals("void");
	}

}
