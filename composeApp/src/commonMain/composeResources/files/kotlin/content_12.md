### What's race condition?

A **race condition** is a type of software bug that occurs when multiple threads or processes access shared resources, such as variables, files, or databases, and the program's behavior depends on the order or timing of these accesses. It often leads to unpredictable and undesirable outcomes, as the sequence of operations isn't guaranteed to occur in the intended order.

#### How a Race Condition Occurs

A race condition arises when two or more threads or processes try to read or write shared data simultaneously without proper synchronization. Because thread execution timing can vary, the sequence of access to the shared resource becomes non-deterministic, potentially causing errors or inconsistent states.

Consider a banking system where two threads attempt to update a user’s account balance. If both threads read the same initial balance before either updates it, the final balance will reflect only one of the changes instead of both.

```kotlin
var balance = 100

fun withdraw(amount: Int) {
    if (balance >= amount) {
        balance -= amount  // Race condition here
    }
}
```

When two threads execute `withdraw(50)` concurrently, both might read the balance as 100, leading to a final balance of 50 instead of 0.

#### Real-World Impact

Race conditions are a common issue in multithreaded or asynchronous environments, such as mobile apps, server-side systems, or embedded systems. They can lead to data corruption, unexpected crashes, or subtle bugs that are difficult to reproduce and debug.

#### Preventing Race Conditions

To avoid race conditions, proper synchronization mechanisms are required to control access to shared resources. Techniques like **mutual exclusion** (using locks), **atomic operations**, or **thread-safe data structures** can help ensure that only one thread accesses the shared resource at a time.

Using a `synchronized` block in Kotlin can safeguard critical sections:

```kotlin
val lock = Any()

fun withdraw(amount: Int) {
    synchronized(lock) {
        if (balance >= amount) {
            balance -= amount
        }
    }
}
```

Alternatively, modern approaches like **coroutines** in Kotlin provide structured concurrency that minimizes the risk of race conditions by confining state modifications to a single thread or coroutine scope.

#### Summary

A race condition occurs when the correctness of a program depends on the unpredictable timing of multiple threads accessing shared resources. It can lead to inconsistent results or system failures. Proper synchronization techniques, such as locks or thread-safe constructs, are essential for mitigating race conditions in concurrent programming environments.