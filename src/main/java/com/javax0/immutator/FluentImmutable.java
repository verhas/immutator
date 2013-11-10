package com.javax0.immutator;

import com.javax0.djcproxy.ProxyFactory;
import com.javax0.djcproxy.interceptors.RuntimeExceptionInterceptor;

public class FluentImmutable {

	private static final ProxyFactory<?> fluentFactory = new ProxyFactory<>();
	static {
		fluentFactory.setCallbackFilter(FluentMethodFilter
				.getInstance());
	}

	/**
	 * Returns an immutable version of the {@code original} object, similar to
	 * {@link #chain(Object)} but does not allow any method that returns any
	 * subclass of {@code T}.
	 * <p>
	 * This way this method creates an immutable object for any fluent API
	 * implementation.
	 * 
	 * @param original
	 * @return
	 * @throws Exception
	 */
	public <T> T of(T original) throws Exception {
		@SuppressWarnings("unchecked")
		ProxyFactory<T> factory = (ProxyFactory<T>) fluentFactory;
		T proxy = factory.create(original,
				RuntimeExceptionInterceptor.getInstance());
		return proxy;
	}
}
