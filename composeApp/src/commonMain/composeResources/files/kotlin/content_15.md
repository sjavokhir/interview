### What are structural equality and referential equality, and how do they differ?

The equality can be evaluated in two distinct ways: structural equality and referential equality. These concepts are essential for understanding how objects and their values are compared in the language.

**Structural Equality (`==`)**

Structural equality checks whether two objects have equivalent content. This comparison is performed using the `==` operator, which internally translates to a call to the `.equals()` method. If `.equals()` is overridden in a class, the custom implementation determines how structural equality is evaluated. For example:

```kotlin
data class Person(val name: String, val age: Int)

val person1 = Person("skydoves", 30)
val person2 = Person("skydoves", 30)

println(person1 == person2) // Output: true
```

In this example, the `==` operator returns `true` because the `Person` class, being a data class, automatically overrides `.equals()` to compare the properties of `person1` and `person2`.

**Referential Equality (`===`)**

Referential equality checks whether two references point to the exact same object in memory. This is performed using the `===` operator, which ensures that both variables refer to the same instance. For example:

```kotlin
val person1 = Person("skydoves", "man")
val person2 = Person("skydoves", "man")
val person3 = person1

println(person1 === person2) // Output: false (different instances)
println(person1 === person3) // Output: true (same instance)
```

Here, `person1` and `person2` have identical content, but they are different instances in memory, so `===` returns `false`. On the other hand, `person1` and `person3` refer to the same instance, so `===` returns `true`.

**Key Differences**

1. **Purpose**: Structural equality (`==`) compares the content of two objects, while referential equality (`===`) compares their memory references.

2. **Implementation**: Structural equality relies on the `.equals()` method, which can be overridden. Referential equality is a direct comparison of memory addresses and cannot be customized.

3. **Use Cases**: Use structural equality when the logical content of objects matters, such as comparing two strings or data class instances. Use referential equality when you need to ensure that two references point to the same object in memory.

#### Summary

Structural equality (`==`) compares the content of two objects, while referential equality (`===`) checks if two references point to the same memory location. Understanding and using these two types of equality appropriately ensures accurate and efficient comparisons, depending on the context of your application.