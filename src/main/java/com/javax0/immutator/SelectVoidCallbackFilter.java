package com.javax0.immutator;

import java.lang.reflect.Method;

import com.javax0.djcproxy.CallbackFilter;

public class SelectVoidCallbackFilter implements CallbackFilter{
	private static final SelectVoidCallbackFilter INSTANCE = new SelectVoidCallbackFilter();

	public static SelectVoidCallbackFilter getInstance() {
		return INSTANCE;
	}
	@Override
	public boolean accept(Method method) {
		return method.getReturnType().toString().equals("void");
	}

}
