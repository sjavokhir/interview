### What are the differences between launch and async?

`launch` and `async` are coroutine builders used to execute tasks concurrently. While they may seem similar, their intended use cases, return types, and behavior differ significantly, making each suitable for specific scenarios.

##### launch Overview

`launch` creates a coroutine that runs independently and doesn’t return a result. It is primarily used for **fire-and-forget** tasks such as updating the UI, logging, or running background tasks where you don’t need a return value.

**Key Characteristics of launch:**
- **Return Type:** `Job` (non-blocking handle for cancellation).
- **Execution:** Runs asynchronously and returns immediately.
- **Use Case:** Tasks with no expected result, such as UI updates or background tasks.

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch {
        delay(1000L)
        println("Task completed")
    }
    println("Main thread running")
    job.join()  // Waits for the task to complete
}
```

##### async Overview

`async` creates a coroutine that **returns a result** using a `Deferred<T>` object, similar to a Future or Promise in other languages. The result can be accessed using `await()`, which suspends the coroutine until the task is complete.

**Key Characteristics of async:**
- **Return Type:** `Deferred<T>` (future-like result holder).
- **Execution:** Runs asynchronously but requires `await()` to retrieve the result.
- **Use Case:** Tasks that return a computed value or result.

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val deferred = async {
        delay(1000L)
        "Data fetched"
    }
    println("Fetching result...")
    val result = deferred.await()
    println("Result: $result")
}
```

##### Key Differences

#### Return Type
- `launch`: `Job`
- `async`: `Deferred<T>`

#### Result Access
- `launch`: No result
- `async`: Use `await()` for result

#### Execution
- `launch`: Fire-and-forget
- `async`: Computes and returns value
- 
- #### Error Handling
- `launch`: Immediate cancellation
- `async`: Exception thrown on `await`

##### Summary

Use `launch` for tasks that don’t return a result, such as background operations or UI updates. Use `async` for concurrent tasks that produce a result and require retrieval or more computational tasks using `await()`. Choosing between the two depends on whether you need a return value, enabling more precise coroutine-based task management in Kotlin applications.