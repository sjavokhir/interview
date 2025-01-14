### What happens if you run null + null operation?

Adding `null + null` does not result in a compiler error. Instead, it returns the string `"nullnull"`. This happens because Kotlin implicitly converts `null` to its `String` representation when the `+` operator is used. In this context, the `+` operator concatenates the string `"null"` with another `"null"`, producing `"nullnull"`.

```kotlin
val result = null + null
println(result) // Output: nullnull
```

This behavior is possible because the `+` operator for `String` types is overloaded to perform string concatenation, and `null`, when used in a string context, is converted to the string `"null"`.

#### Why Does This Happen?

The Kotlin compiler interprets the `+` operator as a call to the `String.plus()` function if you were not overridden the operator. When either operand is `null`, Kotlin converts it into the string `"null"`. Here’s what effectively happens under the hood:

```kotlin
val result = "null".plus("null")
println(result) // Output: nullnull
```

#### Summary

In Kotlin, `null + null` results in `"nullnull"` due to implicit conversion of `null` to its string representation during string concatenation. This highlights the importance of understanding how Kotlin handles nullable values in different contexts to avoid unexpected results in your code. For more information, you can refer to [What Happens if You Add `null + null` in Kotlin?](https://youtu.be/wwplVknTza4?feature=shared).