### What are side effects in Jetpack Compose, and how can you use side-effect APIs?

In **Jetpack Compose**, a **side effect** refers to any operation that affects state outside the scope of the composable function or persists beyond its recomposition. Since composables are designed to be pure functions that simply render UI based on the current state, side effects are used when you need to perform actions outside the composable function's lifecycle, like updating shared state, triggering one-time events, or interacting with external resources.

Jetpack Compose provides several **side-effect APIs** to handle these scenarios safely and predictably, such as `LaunchedEffect`, `SideEffect`, and `DisposableEffect`.

### 1. **LaunchedEffect**: Used for launching coroutines in a composable
- `LaunchedEffect` allows you to start a coroutine in response to certain key state changes. It runs within the `Composition` and will cancel and restart if the specified key changes, making it useful for one-time or reactive tasks, such as fetching data or handling animations.
- **Example**:
  ```kotlin
  @Composable
  fun MyScreen(userId: String) {
      LaunchedEffect(userId) {
          // Runs when `userId` changes, or when entering the composition
          fetchDataForUser(userId)
      }
  }
  ```

### 2. **SideEffect**: Used to perform non-restartable side effects
- `SideEffect` is invoked every time a composable successfully recomposes. It’s used for performing lightweight, non-restartable actions like updating a mutable shared object or logging.
- **Example**:
  ```kotlin
  @Composable
  fun MyComposable(screenName: String) {
      SideEffect {
          // Runs after each recomposition, ideal for analytics or logging
          logScreenView(screenName)
      }
  }
  ```

### 3. **DisposableEffect**: Used for effects that need cleanup
- `DisposableEffect` is used for actions that require both setup and cleanup, such as registering a listener or resource that should be released when the composition leaves the screen or is recomposed. This API lets you define `onDispose` block, which will be invoked when the Composable function's lifecycle is ended up.
- **Example**:
  ```kotlin
  @Composable
  fun MyComposableWithListener(listener: SomeListener) {
      DisposableEffect(listener) {
          listener.register()  // Called when entering the composition
          onDispose {
              listener.unregister()  // Called when leaving the composition
          }
      }
  }
  ```

### Summary
- **LaunchedEffect**: Triggers a coroutine based on state changes; ideal for async actions like data loading.
- **SideEffect**: Executes non-restartable code after each recomposition; useful for logging or relevant actions.
- **DisposableEffect**: Manages effects with setup and cleanup/disposing requirements, such as resource listeners.

Using these side-effect APIs properly allows you to manage external resources, events, and state changes efficiently within the composable lifecycle, maintaining a clean, predictable UI. For more information, check out the official Android docs [Side-effects in Compose](https://developer.android.com/develop/ui/compose/side-effects).