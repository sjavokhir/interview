### How to optimize Compose performance by reducing recomposition?

#### 1. **Stabilize Your Classes with `@Stable` and `@Immutable` Annotations**

The `@Immutable` annotation ensures that all public properties of a class remain immutable after initialization, which signals to the Compose compiler that this class will never change, allowing it to skip unnecessary recompositions. On the other hand, the `@Stable` annotation also signals stability but is slightly more lenient, indicating that some fields may change but will not trigger recompositions unless specifically altered.

#### 2. **Use Immutable Collections**

To maintain stability within Composable functions, use immutable collections. The `kotlinx.collections.immutable` library, or alternatives like Guava's immutable collections, provides data structures like `ImmutableList` and `ImmutableSet`. These collections, unlike standard mutable ones, prevent any modifications after they are created, ensuring predictable behavior and reducing recompositions.

#### 3. **Optimize Lambdas (Non-Capturing Lambdas)**

Lambdas in Compose can be optimized depending on whether or not they capture values from the surrounding scope. If a lambda does not capture any external values, Compose treats it as a singleton, improving performance by avoiding unnecessary reallocation. For example:
```kotlin
modifier.clickable {
    Log.d("Log", "This Lambda doesn't capture any values")
}
```
However, if a lambda captures values from its surrounding scope, Compose uses memorization to handle it efficiently, as seen below:
```kotlin
var sum = 0
ints.filter { it > 0 }.forEach {
    sum += it
}
```
In such cases, the `remember` function is used to ensure that the lambda is invoked appropriately when captured values change.

#### 4. **Use Wrapper Classes for Unstable Data**

For classes outside your control (such as third-party libraries), create wrapper classes to impose stability. This ensures the data remains stable and does not trigger unnecessary recompositions. For example:

```kotlin
@Immutable
data class ImmutableUserList(
   val user: List<User>
)
```

This technique is especially useful when dealing with data that cannot be directly annotated as stable.

#### 5. **Use a Stability Configuration File**

Starting with Compose Compiler version 1.5.5, you can define a [stability configuration file](https://developer.android.com/develop/ui/compose/performance/stability/fix#configuration-file). This allows you to mark specific classes (including third-party or external classes) as stable, even when you don’t have direct control over them.

#### 6. **Enable Strong Skipping Mode**

Compose Compiler version 1.5.4 introduced [Strong Skipping Mode](https://developer.android.com/develop/ui/compose/performance/stability/strongskipping), allowing Composable functions to be skippable, even if they contain unstable parameters. This mode also ensures lambdas with unstable captures are efficiently memoized, improving performance by preventing redundant recompositions.

These techniques help you manage state and data in Compose more efficiently, ensuring better performance by minimizing unnecessary recompositions.