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

The creation of the immutator proxy object is done during run time using the cglib library.


