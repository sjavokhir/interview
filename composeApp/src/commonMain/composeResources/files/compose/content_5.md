### How `rememberCoroutineScope` works in Jetpack Compose internally

If you look into the details of the `rememberCoroutineScope` function, you'll see the code below:

```kotlin
@Composable
inline fun rememberCoroutineScope(
    crossinline getContext: @DisallowComposableCalls () -> CoroutineContext = { EmptyCoroutineContext }
): CoroutineScope {
    val composer = currentComposer
    val wrapper = remember {
        CompositionScopedCoroutineScopeCanceller(
            createCompositionCoroutineScope(getContext(), composer)
        )
    }
    return wrapper.coroutineScope
}
```

The key concepts here are:

1. The coroutine scope is created using the `createCompositionCoroutineScope` method.
2. This newly created coroutine scope is then registered with the `CompositionScopedCoroutineScopeCanceller`.
3. The `CompositionScopedCoroutineScopeCanceller` is stored using the `remember` composable function, which eventually returns the coroutine scope.

So let's see one by one. If you look into the `createCompositionCoroutineScope` method, it creates the coroutine scope with the given coroutine context.

```kotlin
internal fun createCompositionCoroutineScope(
    coroutineContext: CoroutineContext,
    composer: Composer
) = if (coroutineContext[Job] != null) {
    CoroutineScope(
        Job().apply {
            completeExceptionally(
                IllegalArgumentException(
                    "CoroutineContext supplied to " +
                        "rememberCoroutineScope may not include a parent job"
                )
            )
        }
    )
} else {
    val applyContext = composer.applyCoroutineContext
    CoroutineScope(applyContext + Job(applyContext[Job]) + coroutineContext)
}
```

The created coroutine scope is then passed to the `CompositionScopedCoroutineScopeCanceller` class, which extends `RememberObserver`. This class is responsible for canceling the coroutine scope in alignment with the `RememberObserver` lifecycle, ensuring that the scope is properly managed and terminated when it is no longer needed. Then, you'll get the coroutine scope that is held by the `CompositionScopedCoroutineScopeCanceller` class.