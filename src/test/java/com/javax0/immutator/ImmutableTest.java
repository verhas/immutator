package com.javax0.immutator;

import org.junit.Assert;
import org.junit.Test;

public class ImmutableTest {

	public static class A {
		private int i;

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

	}

	@Test(expected = RuntimeException.class)
	public void given_AnObject_when_CreatingImmutableVersion_then_CallingMutatorThrowsException()
			throws Exception {
		A a = new A();
		a.setI(10);
		A b = Immutable.of(a);
		b.setI(20);
	}

	@Test
	public void given_AnObject_when_CreatingImmutableVersion_then_CallingQueryReturnsOriginalRetval()
			throws Exception {
		A a = new A();
		a.setI(10);
		A b = Immutable.of(a);
		Assert.assertEquals(10, b.getI());
	}

	public static class Fluent {
		private int i;

		public int getI() {
			return i;
		}

		public SubFluent setI(int i) {
			SubFluent sf = new SubFluent();
			sf.setI(i);
			return sf;
		}
	}

	public static class SubFluent extends Fluent {
		private int i;

		public int getI() {
			return i;
		}

		public SubFluent setI(int i) {
			this.i = i;
			return this;
		}
	}

	@Test(expected = RuntimeException.class)
	public void given_AFluentObject_when_CreatingImmutableVersion_then_CallingMutatorThrowsException()
			throws Exception {
		Fluent a = new Fluent();
		a.setI(10);
		Fluent b = Immutable.of.fluent(a);
		b.setI(20);
	}

	@Test
	public void given_AFluentObject_when_CreatingImmutableVersion_then_CallingQueryReturnsOriginalRetval()
			throws Exception {
		Fluent a = new Fluent();
		a = a.setI(10);
		Fluent b = Immutable.of.fluent(a);
		Assert.assertEquals(10, b.getI());
	}

	@Test
	public void given_AFluentObject_when_CreatingImmutableVersion_then_CallingMutatorReturnsOriginalRetval()
			throws Exception {
		Fluent a = new Fluent();
		a = a.setI(10);
		Fluent b = Immutable.of(a);
		Fluent c = b.setI(20);
		Assert.assertTrue(c == a);
	}

	@Test
	public void given_TwoSimpleObjectsOfTheSameType_when_CreatingImmutableVersions_then_NoDuplicatedProxyClassIsCreated()
			throws Exception {
		A a1 = new A();
		A a2 = new A();
		A aa = Immutable.of(a1);
		A ab = Immutable.of(a2);
		Assert.assertEquals(aa.getClass(), ab.getClass());
	}

	@Test
	public void given_TwoFluentObjectsOfTheSameType_when_CreatingImmutableVersions_then_NoDuplicatedProxyClassIsCreated()
			throws Exception {
		Fluent a1 = new Fluent();
		Fluent a2 = new Fluent();
		Fluent aa = Immutable.of(a1);
		Fluent ab = Immutable.of(a2);
		Assert.assertEquals(aa.getClass(), ab.getClass());
	}

	public static class MutableClass {
		public void mutator() {
		}

		public void immutator() {
		}
	}

	interface ImmutableMethods {
		void immutator();
	};

	@Test(expected = RuntimeException.class)
	public void given_AClassAndImmutableMethodsInterface_when_CreatingImmutableVersion_then_CallingQueryRunsFine()
			throws Exception {
		MutableClass testObject = new MutableClass();
		MutableClass immutable = Immutable.of.using(ImmutableMethods.class).of(
				testObject);
		immutable.immutator();
	}

	@Test
	public void given_AClassAndImmutableMethodsInterface_when_CreatingImmutableVersion_then_CallingMutatorThrowsException()
			throws Exception {
		MutableClass testObject = new MutableClass();
		MutableClass immutable = Immutable.of.using(ImmutableMethods.class).of(
				testObject);
		immutable.mutator();
	}

	@Test
	public void given_TwoObjectOfTheSameClass_when_CreatingImmutableVersionBasedOnTheSameInterface_then_CreatingImmutableVersionCreatesOneClassOnly()
			throws Exception {
		MutableClass testObject1 = new MutableClass();
		MutableClass immutable1 = Immutable.of.using(ImmutableMethods.class)
				.of(testObject1);
		MutableClass testObject2 = new MutableClass();
		MutableClass immutable2 = Immutable.of.using(ImmutableMethods.class)
				.of(testObject2);
		Assert.assertEquals(immutable1.getClass(), immutable2.getClass());
	}

	public static class CloneableA extends A implements Cloneable {

		@Override
		public CloneableA clone() throws CloneNotSupportedException {
			return (CloneableA) super.clone();
		}
	}

	@Test
	public void given_AnObject_when_CreatingImmutableVersion_then_CloneableWorks()
			throws Exception {
		A a = new CloneableA();
		a.setI(10);
		A b = Immutable.of(a);
		Assert.assertEquals(10, b.getI());
	}
}
