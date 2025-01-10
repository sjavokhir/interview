### What's the difference between cold flow and hot flow?

In Kotlin's [coroutines library](https://kotlinlang.org/docs/coroutines-overview.html), Flows are a powerful tool for handling asynchronous data streams. A key distinction when working with Flows is whether they are **cold** or **hot**, which describes when data is emitted and how subscribers interact with the stream.

##### Cold Flow

A cold flow produces or emits data only when there is at least one active subscriber. Unlike hot flows, cold flows react independently to each subscription, starting fresh for every new collector.

Cold flows are lazy by design, meaning the computation or data emission begins only when the flow is collected. This makes cold flows ideal for tasks that need to be tied to specific lifecycles, such as fetching data from a database or making network requests. Each collector triggers its own independent execution, ensuring that subscribers get a unique stream of data.

For instance, consider a simple flow that emits three integers. When collected, the flow starts producing data, but each collection results in a new emission sequence.

```kotlin
val coldFlow = flow {
    println("Flow started")
    emit(1)
    emit(2)
    emit(3)
}

coldFlow.collect { println("Collector 1: $it") }
coldFlow.collect { println("Collector 2: $it") }
```

In this example, both collectors independently trigger the flow, resulting in the same sequence of emissions for each. This behavior is particularly useful when fresh data is needed for every new collector.

##### Hot Flow

In contrast, a hot flow continues to emit data regardless of whether there are active subscribers. Subscribers observing a hot flow will only receive data emitted after they start collecting, and they might miss earlier emissions. This behavior makes hot flows suitable for scenarios involving shared data or live updates.

Hot flows maintain an active state and emit data to multiple subscribers simultaneously. Common examples of hot flows in Kotlin include `SharedFlow` and `StateFlow`. These flows are designed to share data efficiently among subscribers. `SharedFlow` is typically used for broadcasting updates, while `StateFlow` holds the latest value and emits it to any new collector.

Consider a `SharedFlow` emitting values in real time. Collectors observe the emissions currently available but do not receive past data unless explicitly configured.

```kotlin
val hotFlow = MutableSharedFlow<Int>(replay = 1)

GlobalScope.launch {
    hotFlow.emit(1)
    hotFlow.emit(2)
    hotFlow.emit(3)
}

hotFlow.collect { println("Collector 1: $it") }
hotFlow.collect { println("Collector 2: $it") }
```

In this scenario, both collectors share the same stream and observe only the latest emissions or replayed values. Hot flows excel in use cases like real-time notifications, shared state updates, or UI events.

##### Key Differences Between Cold Flow and Hot Flow

The primary difference lies in how they handle data emissions and subscriptions. Cold flows begin emitting data only when collected, making them stateless and providing independent streams to each subscriber. Hot flows, on the other hand, continuously emit data and share the same stream among all subscribers, making them suitable for live updates and shared data scenarios.

#### Summary

Cold flows are perfect for operations that need fresh data for each subscription, such as network calls or database queries. Hot flows are ideal for scenarios where data needs to be shared across multiple subscribers, such as managing UI state or broadcasting events in real time.