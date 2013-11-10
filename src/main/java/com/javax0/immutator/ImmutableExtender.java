package com.javax0.immutator;


public class ImmutableExtender {
	private static FluentImmutable fluentImmutable = new FluentImmutable();
	
	public <T> T fluent(T original) throws Exception {
		return fluentImmutable.of(original);
	}
	
	public InterfaceImmutable using(Class<?> interFace) throws Exception {
		return InterfaceImmutable.getInstance(interFace);
	}
}
