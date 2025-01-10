### What's recomposition?

**Recomposition** in Jetpack Compose is the process by which the framework redraws parts of the UI to reflect updated data or state. Instead of redrawing the entire screen, Compose smartly "recomposes" (smart recomposition) only the parts of the UI that need to change, making it more efficient than traditional UI frameworks.

#### How Recomposition Works

1. **State-Driven UI**: Compose is a declarative UI framework where the UI is built based on the current state. When state changes, Compose triggers recomposition for the affected parts of the UI tree.

2. **Selective Redraw**: Only the composable functions that rely on the updated state will recompose. If a composable function doesn’t depend on the changed state, it will not recompose, making the UI update more efficient.

3. **Composable Functions**: Recomposition happens at the function level, where Compose calls the affected composable functions again with the new data. Compose reuses as much as possible from the previous composition to avoid unnecessary redraws.

For example, imagine there’s a text displaying a click count and a button that increments this count each time the user clicks it.

```kotlin
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Column {
        Text("Count: $count")
        Button(onClick = { count++ }) {
            Text("Increase")
        }
    }
}
```

The example above plays:

- Each time the button is clicked, `count` is updated, triggering recomposition of the `Counter` function.
- Compose redraws only the `Text` composable showing the count value, rather than the entire UI.

To summarize, the key points about recomposition are:

- **State-Driven**: Recomposition occurs when state changes. `remember` and `mutableStateOf` are commonly used to hold state that affects recomposition.
- **Optimized Performance**: Compose tries to recompose only what’s necessary, helping to improve performance.
- **Idempotency**: Composable functions should be designed to produce the same UI output for the same input, making recomposition reliable.

The Jetpack Compose Runtime library offers several functions that are closely related to state management, designed to either preserve data across recompositions or handle side effects efficiently.

- **`remember`**: Caches values across recompositions, so they aren’t reset each time.
- **`derivedStateOf`**: Optimizes recomposition by only triggering when the derived state changes.
- **`LaunchedEffect`, `SideEffect`, and `DisposableEffect`**: Manage side effects in composable functions across recompositions.

#### Summary

Recomposition is the process that updates and redraws UI elements based on new states, focusing only on parts of the UI that need to change. This approach, known as "smart recomposition," allows Jetpack Compose to efficiently update the UI, preserving responsiveness by keeping it synchronized with the current state.