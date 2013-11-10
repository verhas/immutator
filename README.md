immutator
=========

Immutator is a Java library to create immutable version of an object run time.
An immutable version of an object is a proxy object that works on the original object, transparently
passing the call to the original object but throws exception whenever the code calls a method that may
modify the state of the original object

A method call that does not modify the state of the original object gets through:

```
caller -- queryMethod() --> proxyObject -- queryMethod() --> originalObject
```

A method call that may modify the state of the object raises exception

```
                                          throw exception
                                              ^
caller -- mutatorMethod() --> proxyObject() --+
```

The creation of the immutator proxy object is done during run time using the jscglib library.

To create an immutable version of an object you should simply call

```
MyClass object = new MyClass();
MyClass query = Immutable.of(object);
```

The object `query` will throw `RuntimeException` for any call to a method of class `MyClass` that is `void`. This simplest use assumes that the methods in `MyClass` are developed following the single responsible principle, that is: each method does one thing. If a method returns a value, it does just that and does not do anything else, specifically does not alter the state of the object.

Some design patterns, however, allow having non `void` methods to alter the state of the object although the single responsibility is still met. This is when your classes are designed to provide fluent interface. In that case the setters/mutators return an object of the same type as the method was called (or a sub class) allowing to write consecutive calls in the form:

```
fluent.methodCall(1).methodCall(2).methodCall(3)
```

To create immutable version of those objects you can

```
MyClass query = Immutable.of.fluent(object)
```

This will create an immutable object that will throw `RuntimeException` when a method is called that is `void` or has a return value compatible with `MyClass`, and thus can be part of the fluent api and as such is assumed to be a mutator.

When you have a class that does not follow neither of the above design patterns you should use the general call:

```
interface Query {
 ... all methods that are not mutators ...
}
MyClass query = Immutable.using(Query.class).of(object);
```

The interface `Query` is used to define the names of the methods that are mutators. The call to create the immutable version will treat all methods that are listed in the interface `Query` as immutators and for any other method `RuntimeException` will be thrown.

Notes
-----

The methods defined in the `java.lang.Object` are never intercepted.

The interface defining the query methods can be a class. Implementation details are not checked by the library. Also the return value and the argument types and the number of the arguments in this interface or class are irrelevant, only the names of the inherited and declared methods are checked. The class need not implement the interface. The interface is simply used like a String array to get the set of names of the methods.

The factories that are creating the proxy classes in the library try to reuse the already created classes, thus they will not pollute the permgen. They will, however generate a new proxy class for each (class, interface) pairs.


