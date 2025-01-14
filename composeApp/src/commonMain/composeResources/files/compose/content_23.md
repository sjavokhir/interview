### Why should you always test Compose performance in release mode?

When testing Jetpack Compose performance, always prioritize running your app in release mode with [R8 enabled](https://developer.android.com/build/shrink-code#enable). Debug mode introduces additional overheads such as interpretation, Just-In-Time (JIT) compilation, and developer tools features like Live Literals, all of which impact performance and fail to reflect the end-user experience.

#### Impact of Debug Mode on Compose
Compose is shipped as a library ("unbundled"), meaning it is interpreted and compiled at runtime when running a debuggable app. Unlike the View system, which is bundled with the Android OS and pre-compiled, Compose code in debug mode incurs additional interpretation and JIT compilation overhead. This can create significant performance gaps compared to optimized release builds.

> According to the Android team, View libraries quickly end up back in the pre-compiled, release built and optimized, framework code. The Compose version on the other hand is running the whole UI stack as debuggable code, not just the small sliver of lazy list management.

#### Live Literals and Developer Tools
Debug builds enable developer features such as [Live Literals](https://developer.android.com/develop/ui/compose/tooling#live-edit-literals), which replace constants with getter functions to support runtime updates. This introduces additional computation and prevents optimizations, leading to slower recomposition and rendering in debug mode.

#### R8 Optimization in Release Mode
R8 significantly improves performance in release builds through optimizations like lambda grouping, omitting source information, constant folding, and converting interface calls to faster static invocations. These optimizations reduce startup times, lower memory usage, and streamline runtime execution.

> According to the Android team, Compose also benefits tremendously from R8 optimisation. As seen above, the addition of R8 in its default configuration is a 75% gain in startup performance and 60% gain in frame rendering performance. R8 does a lot of optimisation but below are the details of some that have the greatest effect on Compose code.

#### Why Baseline Profiles Matter
Compose relies on [Baseline Profiles](https://developer.android.com/topic/performance/baselineprofiles/overview) to further improve performance in release mode. These profiles precompile critical Compose methods, avoiding the need for runtime interpretation and JIT compilation during app startup. Debug builds do not use Baseline Profiles, making them less reflective of actual app performance. Further information, you can read [Improve Your Android App Performance With Baseline Profiles](https://medium.com/proandroiddev/improve-your-android-app-performance-with-baseline-profiles-297f388082e6).

#### Practical Testing Recommendations
To accurately assess performance, always test your Compose app in release mode with R8 and Baseline Profiles enabled. Utilize tools like [Macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview) to measure startup and runtime performance. This approach ensures you identify true performance bottlenecks and deliver a smooth experience to end-users.

#### Summary
Debug mode introduces significant overhead that distorts Jetpack Compose's true performance, making release mode testing essential. R8 optimizations and Baseline Profiles ensure Compose apps run efficiently, highlighting the importance of realistic performance benchmarking. For further insights, refer to the [Android Developer Blog: Why should you always test Compose performance in release?](https://medium.com/androiddevelopers/why-should-you-always-test-compose-performance-in-release-4168dd0f2c71).