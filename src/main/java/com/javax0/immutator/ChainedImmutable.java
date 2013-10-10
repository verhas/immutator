package com.javax0.immutator;

import com.javax0.djcproxy.ProxyFactory;
import com.javax0.djcproxy.interceptors.RuntimeExceptionInterceptor;

public class ChainedImmutable {

	private static final ProxyFactory<?> chainFactory = new ProxyFactory<>();
	static {
		chainFactory.setCallbackFilter(SelectVoidAndThisCallbackFilter
				.getInstance());
	}

	/**
	 * Return an immutable proxy version of the {@code original} object, which
	 * throws runtime exception when calling any method that does not return any
	 * value ({@code void}) or the return type is {@code T}. This method assumes
	 * that all methods that do not return any value are mutators and any method
	 * that returns some value are query methods without altering the state of
	 * the object.
	 * 
	 * @param original
	 * @return
	 * @throws Exception
	 */
	public <T> T chain(T original) throws Exception {
		@SuppressWarnings("unchecked")
		ProxyFactory<T> factory = (ProxyFactory<T>) chainFactory;
		T proxy = factory.create(original,
				RuntimeExceptionInterceptor.getInstance());
		return proxy;
	}

	private static final ProxyFactory<?> fluentFactory = new ProxyFactory<>();
	static {
		chainFactory.setCallbackFilter(SelectVoidAndSubClassCallbackFilter
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
	public <T> T fluent(T original) throws Exception {
		@SuppressWarnings("unchecked")
		ProxyFactory<T> factory = (ProxyFactory<T>) fluentFactory;
		T proxy = factory.create(original,
				RuntimeExceptionInterceptor.getInstance());
		return proxy;
	}
}
