### What is functional (SAM) interfaces?

[Functional interfaces](https://kotlinlang.org/docs/fun-interfaces.html), also known as Single Abstract Method (SAM) interfaces, are interfaces that have exactly one abstract method. These interfaces are designed to represent a single operation or function, making them an essential feature for enabling functional programming constructs and simplifying the use of lambda expressions.

A functional interface can be annotated with `@FunctionalInterface` for clarity, although this annotation is optional. The key characteristic of a functional interface is its single abstract method, which allows you to use lambda expressions or method references instead of creating verbose implementations.

```kotlin
fun interface Greeter {
    fun greet(name: String): String
}

val greeter = Greeter { name -> "Hello, $name from skydoves!" }
println(greeter.greet("Kotlin")) // Output: Hello, Kotlin from skydoves!
```

In this example, the `Greeter` functional interface defines a single method `greet`. A lambda expression is used to provide the implementation directly when creating an instance of `Greeter`.

#### SAM Conversion

SAM conversion is the mechanism that allows Kotlin to treat a lambda expression as an implementation of a functional interface. This feature eliminates boilerplate code and seamlessly integrates with Java functional interfaces, such as those in the `java.util.function` package.

```kotlin
val runnable = Runnable { println("Running with SAM conversion in skydoves!") }
runnable.run() // Output: Running with SAM conversion in skydoves!
```

Here, the `Runnable` interface from Java is a functional interface, and Kotlin’s SAM conversion allows you to provide a lambda expression instead of creating an explicit implementation.

#### Key Features of Functional Interfaces

1. **Lambda-Friendly**: Functional interfaces simplify code by enabling the use of lambdas instead of verbose anonymous classes.
2. **Seamless Interoperability**: Kotlin’s SAM conversion works with both Kotlin-defined and Java-defined functional interfaces, making it easy to integrate functional programming constructs across platforms.
3. **Reduced Boilerplate**: By using lambda expressions with functional interfaces, you can create concise, readable, and maintainable code.

#### Summary

Functional (SAM) interfaces represent interfaces with a single abstract method, allowing them to be implemented using lambda expressions or method references. They enhance interoperability with Java functional interfaces through SAM conversion and reduce boilerplate code, making them an essential tool for adopting functional programming paradigms in Kotlin. Whether defining custom functional interfaces or leveraging existing ones, they contribute to cleaner and more expressive code.