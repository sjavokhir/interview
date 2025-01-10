### What is a lambda expression?

A lambda expression is an anonymous function that you can treat as a value, allowing it to be passed as an argument, returned from a function, or stored in a variable. Lambda expressions are a cornerstone of functional programming in Kotlin, enabling concise and expressive code for tasks such as filtering, mapping, and event handling.

A lambda expression is defined within curly braces `{}` and typically includes a parameter list (if any) followed by the `->` symbol, separating the parameters from the function body. For example:

```kotlin
val greet = { name: String -> "Hello, $name" }
println(greet("skydoves")) // Output: Hello, skydoves
```

In this example, the lambda expression `{ name: String -> "Hello, $name" }` takes a `String` parameter named `name` and returns a greeting message.

#### Shorthand Syntax

If a lambda has a single parameter, Kotlin provides an implicit `it` keyword to represent the parameter, eliminating the need to declare it explicitly:

```kotlin
val square = { it: Int -> it * it }
println(square(4)) // Output: 16
```

Here, the lambda uses `it` to represent the single parameter, making the code more concise.

#### Usage with Higher-Order Functions

Lambda expressions are commonly used with higher-order functions, which take functions as arguments or return them. Kotlin's collection functions like `filter`, `map`, and `forEach` often leverage lambdas:

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
val doubledNumbers = numbers.map { it * 2 }
println(doubledNumbers) // Output: [2, 4, 6, 8, 10]
```

Here, the lambda `{ it * 2 }` is passed to the `map` function to double each number in the list.

#### Capturing Variables from the Scope

Lambda expressions can capture variables from their surrounding scope, allowing them to maintain state and interact with the enclosing context:

```kotlin
var counter = 0
val increment = { counter++ }
increment()
increment()
println(counter) // Output: 2
```

The lambda `{ counter++ }` captures the `counter` variable and modifies it directly.

#### Summary

Lambda expressions are versatile and powerful, enabling concise and functional programming. They allow you to define behavior inline, use shorthand syntax with `it` for single parameters, and interact with variables from their enclosing scope. Whether used for higher-order functions, collection transformations, or event handling, lambda expressions are a fundamental feature for writing clean and expressive Kotlin code.