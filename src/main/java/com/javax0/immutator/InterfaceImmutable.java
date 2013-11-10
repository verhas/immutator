package com.javax0.immutator;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import com.javax0.djcproxy.ProxyFactory;
import com.javax0.djcproxy.interceptors.RuntimeExceptionInterceptor;

/**
 * Create a
 * 
 * @author verhasp
 * 
 */
public class InterfaceImmutable {

	private static final WeakHashMap<Class<?>, InterfaceImmutable> interfaceImmutableFactories = new WeakHashMap<>();
	private final InterfaceMethodFilter filter;
	private final ProxyFactory<?> factory;

	private InterfaceImmutable(Class<?> interFace) {
		Set<String> methodNames = new HashSet<>();
		addDeclaredMethodsToSet(methodNames, interFace);
		addOwnAndInheritedMethodsToSet(methodNames, interFace);
		filter = new InterfaceMethodFilter(methodNames);
		factory = new ProxyFactory<>();
		factory.setCallbackFilter(filter);
	}

	private void addDeclaredMethodsToSet(Set<String> methodNames,
			Class<?> interFace) {
		for (Method method : interFace.getDeclaredMethods()) {
			methodNames.add(method.getName());
		}
	}

	private void addOwnAndInheritedMethodsToSet(Set<String> methodNames,
			Class<?> interFace) {
		for (Method method : interFace.getMethods()) {
			methodNames.add(method.getName());
		}
	}

	static synchronized InterfaceImmutable getInstance(Class<?> interFace) {
		final InterfaceImmutable instance;
		if (interfaceImmutableFactories.containsKey(interFace)) {
			instance = interfaceImmutableFactories.get(interFace);
		} else {
			instance = new InterfaceImmutable(interFace);
			interfaceImmutableFactories.put(interFace, instance);
		}
		return instance;
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
		ProxyFactory<T> tFactory = (ProxyFactory<T>) factory;
		T proxy = tFactory.create(original,
				RuntimeExceptionInterceptor.getInstance());
		return proxy;
	}
}
