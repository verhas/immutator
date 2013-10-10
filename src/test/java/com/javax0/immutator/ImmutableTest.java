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

	public static class Chained {
		private int i;

		public int getI() {
			return i;
		}

		public Chained setI(int i) {
			this.i = i;
			return this;
		}
	}

	@Test(expected = RuntimeException.class)
	public void given_AChainedObject_when_CreatingImmutableVersion_then_CallingMutatorThrowsException()
			throws Exception {
		Chained a = new Chained();
		a.setI(10);
		Chained b = Immutable.of.chain(a);
		b.setI(20);
	}

	@Test
	public void given_AChainedObject_when_CreatingImmutableVersion_then_CallingQueryReturnsOriginalRetval()
			throws Exception {
		Chained a = new Chained();
		a.setI(10);
		Chained b = Immutable.of.chain(a);
		Assert.assertEquals(10, b.getI());
	}

	@Test
	public void given_AChainedObject_when_CreatingImmutableVersion_then_CallingChainedMutatorReturnsOriginalRetval()
			throws Exception {
		Chained a = new Chained();
		a.setI(10);
		Chained b = Immutable.of(a);
		Chained c = b.setI(20);
		Assert.assertTrue(c == a);
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
		a.setI(10);
		Fluent b = Immutable.of.fluent(a);
		Assert.assertEquals(10, b.getI());
	}

	@Test
	public void given_AFluentObject_when_CreatingImmutableVersion_then_CallingChainedMutatorReturnsOriginalRetval()
			throws Exception {
		Fluent a = new Fluent();
		a.setI(10);
		Fluent b = Immutable.of(a);
		Fluent c = b.setI(20);
		Assert.assertTrue(c == a);
		Fluent d = Immutable.of.chain(a);
		Fluent e = d.setI(20);
		Assert.assertTrue(e == a);
	}
}
