### The `runBlocking` internals

`runBlocking` creates a new coroutine that blocks the current thread until all tasks within it complete. It is mainly used in main functions or unit tests to keep the application alive while executing coroutines. If this blocked thread is interrupted, then the coroutine job is cancelled and this `runBlocking` invocation throws `InterruptedException`.

```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Starting runBlocking")
    launch {
        delay(1000L)
        println("Inside coroutine")
    }
    println("Ending runBlocking")
}
```

You might used to see `runBlocking` has been used frequently in the offical docs or unit testing. However, using `runBlocking` in Android UI code is **discouraged** because it blocks the main thread, causing the app to freeze. Since Android prioritizes responsive UI interactions, suspending functions should be launched using `lifecycleScope.launch` or `viewModelScope.launch` to avoid blocking the main thread.

If you examine the internal implementation of `runBlocking`, you'll find that it launches a new coroutine on the current thread (such as the Android main thread) while leveraging the global scope to derive the coroutine context.

```kotlin
public actual fun <T> runBlocking(context: CoroutineContext, block: suspend CoroutineScope.() -> T): T {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    val currentThread = Thread.currentThread()
    val contextInterceptor = context[ContinuationInterceptor]
    val eventLoop: EventLoop?
    val newContext: CoroutineContext
    if (contextInterceptor == null) {
        // create or use private event loop if no dispatcher is specified
        eventLoop = ThreadLocalEventLoop.eventLoop
        newContext = GlobalScope.newCoroutineContext(context + eventLoop)
    } else {
        // See if context's interceptor is an event loop that we shall use (to support TestContext)
        // or take an existing thread-local event loop if present to avoid blocking it (but don't create one)
        eventLoop = (contextInterceptor as? EventLoop)?.takeIf { it.shouldBeProcessedFromContext() }
            ?: ThreadLocalEventLoop.currentOrNull()
        newContext = GlobalScope.newCoroutineContext(context)
    }
    val coroutine = BlockingCoroutine<T>(newContext, currentThread, eventLoop)
    coroutine.start(CoroutineStart.DEFAULT, coroutine, block)
    return coroutine.joinBlocking()
}
```

It initializes a new instance of `BlockingCoroutine`, and upon examining the internal implementation of `BlockingCoroutine`, particularly the `joinBlocking` method, it becomes evident that this method blocks and occupies the main thread entirely until all tasks are completed.

```kotlin
private class BlockingCoroutine<T>(
    parentContext: CoroutineContext,
    private val blockedThread: Thread,
    private val eventLoop: EventLoop?
) : AbstractCoroutine<T>(parentContext, true, true) {

    override val isScopedCoroutine: Boolean get() = true

    override fun afterCompletion(state: Any?) {
        // wake up blocked thread
        if (Thread.currentThread() != blockedThread)
            unpark(blockedThread)
    }

    @Suppress("UNCHECKED_CAST")
    fun joinBlocking(): T {
        registerTimeLoopThread()
        try {
            eventLoop?.incrementUseCount()
            try {
                while (true) {
                    @Suppress("DEPRECATION")
                    if (Thread.interrupted()) throw InterruptedException().also { cancelCoroutine(it) }
                    val parkNanos = eventLoop?.processNextEvent() ?: Long.MAX_VALUE
                    // note: process next even may loose unpark flag, so check if completed before parking
                    if (isCompleted) break
                    parkNanos(this, parkNanos)
                }
            } finally { // paranoia
                eventLoop?.decrementUseCount()
            }
        } finally { // paranoia
            unregisterTimeLoopThread()
        }
        // now return result
        val state = this.state.unboxState()
        (state as? CompletedExceptionally)?.let { throw it.cause }
        return state as T
    }
}
```

For this reason, it is crucial to use `runBlocking` with caution to prevent it from occupying the Android main thread, which could lead to ANR (Application Not Responding) issues in your application.