package com.javax0.immutator;

import com.javax0.djcproxy.ProxyFactory;
import com.javax0.djcproxy.interceptors.RuntimeExceptionInterceptor;

public class Immutable {
	public static final ChainedImmutable of = new ChainedImmutable();

	private static final ProxyFactory<?> globalFactory = new ProxyFactory<>();
	static {
		globalFactory.setCallbackFilter(SelectVoidCallbackFilter.getInstance());
	}

	/**
	 * Return an immutable proxy version of the {@code original} object, which
	 * throws runtime exception when calling any method that does not return any
	 * value ({@code void}). This method assumes that all methods that do not
	 * return any value are mutators and any method that returns some value are
	 * query methods without altering the state of the object.
	 * 
	 * @param original
	 * @return
	 * @throws Exception
	 */
	public static <T> T of(T original) throws Exception {
		@SuppressWarnings("unchecked")
		ProxyFactory<T> factory = (ProxyFactory<T>) globalFactory;
		T proxy = factory.create(original,
				RuntimeExceptionInterceptor.getInstance());
		return proxy;
	}

}
