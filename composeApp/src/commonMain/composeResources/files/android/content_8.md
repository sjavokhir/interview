### What's ANR on Android? How to prevent it

**ANR (Application Not Responding)** is an error on Android that occurs when the main thread (UI thread) of an app is blocked for too long, usually for **5 seconds** or more. When an ANR occurs, Android prompts the user to either close the app or wait for it to respond. ANRs degrade user experience and can be caused by various factors, such as:

- Heavy computations on the main thread longer than 5 seconds
- Long-running network or database operations
- Blocking UI operations (e.g., synchronous operations on the UI thread)

#### How to Prevent ANRs

To prevent ANRs, it’s essential to keep the main thread responsive by offloading heavy or time-consuming tasks. Here are some best practices:

1. **Move Intensive Tasks Off the Main Thread**
    - Use **background threads** (e.g., using `AsyncTask`, `Executors`, or `Thread`) to handle tasks like file I/O, network requests, or database operations.
    - For a modern, safer approach, use **Kotlin Coroutines** with `Dispatchers.IO` to handle background tasks efficiently.

2. **Use [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for Persistent Tasks**: For tasks that need to run even when the app is in the background (e.g., syncing data), use `WorkManager`. This API handles task scheduling and ensures tasks are done outside the main thread.

3. **Optimize Data Fetching**: Use **Paging** for handling large data sets from a database or network, as it fetches data in small, manageable chunks without overloading the UI.

4. **Minimize UI Operations on Configuration Changes**: Use `ViewModel` to retain UI-related data and avoid reloading the entire UI on configuration changes (like screen rotation).

5. **Monitor and Profile with Android Studio**: Use **Android Studio Profiler** tools to monitor CPU, memory, and network usage. These tools help identify performance bottlenecks that might lead to ANRs.

6. **Avoid Blocking Calls**: Avoid blocking operations like long loops, sleep calls, or synchronous network requests on the main thread.

7. **Use `Handler` for Small Delays**: If you need a small delay without blocking the main thread, use `Handler.postDelayed()` instead of a `Thread.sleep()` on the main thread.

#### Summary

**ANR (Application Not Responding)** is an Android error that occurs when an app's main thread (UI thread) is blocked, usually for **5 seconds** or more, and it decreases the user experiences and loses all the current user states. To prevent ANRs, keep the main thread light by moving intensive work to background threads, such as requesting data from the network, querying the database, and doing heavy computational work. Also, you can optimize data operations and profile your app using the [Android Studio Profiler](https://developer.android.com/studio/profile). For more information, refer to the [official Android docs about ANRs](https://developer.android.com/topic/performance/vitals/anr).