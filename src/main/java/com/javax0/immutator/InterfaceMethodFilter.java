package com.javax0.immutator;

import java.lang.reflect.Method;
import java.util.Set;

import com.javax0.djcproxy.CallbackFilter;

public class InterfaceMethodFilter implements CallbackFilter {

	private final Set<String> methodNames;
	public InterfaceMethodFilter(Set<String> methodNames) {
		this.methodNames = methodNames;
	}
	
	@Override
	public boolean accept(Method method) {
		return methodNames.contains(method.getName());
	}

}
