### The `coroutineScope` extension

Creates a `CoroutineScope` and calls the specified suspend block with this scope. The provided scope inherits its `coroutineContext` from the outer scope, using the `Job` from that context as the parent for a new `Job`. This function is designed for concurrent decomposition of work. When any child coroutine in this scope fails, this scope fails, cancelling all the other children (for a different behavior, see `supervisorScope`). This function returns as soon as the given block and all its child coroutines are completed. A usage of a scope looks like this:

```kotlin
 suspend fun showSomeData() = coroutineScope {
    val data = async(Dispatchers.IO) { // <- extension on current scope
      ... load some UI data for the Main thread ...
     }

     withContext(Dispatchers.Main) {
         doSomeWork()
         val result = data.await()
         display(result)
     }
 }
```

The scope in this example has the following semantics:
1. `showSomeData` returns as soon as the data is loaded and displayed in the UI.
2. If `doSomeWork` throws an exception, then the `async` task is cancelled and `showSomeData` rethrows that exception.
3. If the outer scope of `showSomeData` is cancelled, both started `async` and `withContext` blocks are cancelled.
4. If the `async` block fails, `withContext` will be cancelled.
   The method may throw a `CancellationException` if the current job was cancelled externally, rethrow the exception thrown by `block`, or throw an unhandled Throwable if there is one (for example, from a crashed coroutine that was started with launch in this scope).