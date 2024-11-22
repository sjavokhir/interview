### Stable vs. Immutable in Jetpack Compose

Many people ask about the difference between `@Stable` and `@Immutable` annotations for class stability in Jetpack Compose. The main difference lies in their semantics; the compiler generates the same code for both annotations.

You should use `@Immutable` most of the time to mark a class whose instances are guaranteed not to change.

```kotlin
data class User(
  val id: Int,
  val name: String,
  val images: List<Image>,
)
```

However, if the class has mutable properties that notify the composition about changes (such as those managed by `mutableStateOf`), then `@Stable` is more appropriate to indicate that changes in those properties are safely tracked by the Compose framework.

```kotlin
@Stable
interface State<out T> {
    val value: T
}

@Stable
interface MutableState<T> : State<T> {
    override var value: T
    operator fun component1(): T
    operator fun component2(): (T) -> Unit
}
```