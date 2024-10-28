### What is Dependency Injection (DI)?

**Dependency Injection** is a design pattern in which an object's dependencies (i.e., the objects it needs to perform its tasks) are supplied to it externally, rather than the object creating the dependencies itself. This helps in achieving **loose coupling** between objects, making the code more modular, testable, and maintainable.

In DI, an object (often called the **client**) receives other objects (called **dependencies**) that it requires, rather than creating them directly. These dependencies are usually injected via the constructor, a setter method, or through an interface.

#### Key Concepts:
- **Client**: The object that depends on other objects (dependencies) to function.
- **Dependencies**: The objects that are required by the client to perform certain operations.
- **Injector**: The component responsible for providing dependencies to the client.

#### Difference Between Dependency Injection and the Factory Pattern

- **Dependency Injection**: The responsibility of providing dependencies lies with an external component (like a framework or an injector). The client does not know where the dependencies come from; they are simply provided.
    - *Example*: A DI framework like Dagger injects dependencies into an Android Activity.

- **Factory Pattern**: In the factory pattern, the client is responsible for requesting the dependency from a factory, which constructs and returns the required object. The control of object creation is still within the system, but it is delegated to a factory class that centralizes the object creation logic.
    - *Example*: A class uses a factory to request objects instead of creating them directly.

#### Key Differences:

##### Control of object creation
- **Dependency Injection**: External component (injector)
- **Factory Pattern**: Factory (requested by the client)

##### Flexibility
- **Dependency Injection**: Higher (decouples creation from use)
- **Factory Pattern**: Higher Lower (client must know about the factory)

##### Client knowledge of dependencies
- **Dependency Injection**: The client is unaware of how dependencies are created
- **Factory Pattern**: The client must know how to request the dependency from the factory

#### Difference Between Dependency Injection and Service Locator

- **Dependency Injection**: Dependencies are provided externally, usually by a DI framework, without the client requesting them.

- **Service Locator**: The client asks a central registry (the service locator) for the required dependencies. The service locator pattern allows clients to request objects without knowing where they come from, but it introduces some coupling because the client must interact with the service locator.

#### Key Differences:

##### Dependency retrieval
- **Dependency Injection**: Automatic (injected into the client)
- **Service Locator**: Manual (client requests from the service locator)

##### Coupling
- **Dependency Injection**: Low (client does not know the provider)
- **Service Locator**: Higher (client needs to know about the service locator)

##### Testability
- **Dependency Injection**: Higher
- **Service Locator**: Lower (client is aware of the service locator)

#### Achieving Dependency Injection Manually

Achieving Dependency Injection manually means you don't use a framework like Dagger or Hilt, but instead, manage the creation and injection of dependencies yourself. This can be done in a few ways:

#### 1. Constructor Injection
The most straightforward way to manually inject dependencies is through constructor injection, where dependencies are passed directly into the object's constructor.

Example:

```kotlin
// Dependency
class UserRepository(private val apiService: ApiService) {
    // ...
}

// Service
class ApiService {
    // Service logic here
}

// Class that depends on UserRepository
class UserViewModel(private val userRepository: UserRepository) {
    fun getUserData() {
        userRepository.getData()
    }
}

// Manual Dependency Injection
fun main() {
    // Manually create the dependencies
    val apiService = ApiService()
    val userRepository = UserRepository(apiService)
    val userViewModel = UserViewModel(userRepository)

    // Use the ViewModel
    userViewModel.getUserData()
}
```

In this example, the `UserViewModel` requires `UserRepository`, which in turn depends on `ApiService`. The dependencies are created and passed manually when the object is instantiated.

#### 2. Setter Injection

Another method is to use setter injection, where dependencies are passed through setter methods after object creation.

Example:

```kotlin
// Dependency
class UserRepository(private val apiService: ApiService) {
    // ...
}

// Class that depends on UserRepository
class UserViewModel {
    private lateinit var userRepository: UserRepository

    // Setter injection
    fun setUserRepository(userRepository: UserRepository) {
        this.userRepository = userRepository
    }

    fun getUserData() {
        userRepository.getData()
    }
}

// Manual Dependency Injection
fun main() {
    val apiService = ApiService()
    val userRepository = UserRepository(apiService)
    
    val userViewModel = UserViewModel()
    userViewModel.setUserRepository(userRepository)
    userViewModel.getUserData()
}
```

Here, the `setUserRepository` method is used to inject the dependency into `UserViewModel`.

#### Achieving Dependency Injection Using Dagger or Hilt in Android

**Dagger** and **Hilt** are popular dependency injection frameworks for Android.

1. **Dagger**:
    - Dagger is a fully static, compile-time DI framework. It uses annotations to generate code that can provide dependencies at runtime. Dagger requires explicit component and module setup, which allows for more flexibility and control.
    - **Usage**:
        - Define a **Module** with `@Module` and provide methods annotated with `@Provides` to specify how to create dependencies.
        - Define a **Component** with `@Component` that acts as the injector and binds the modules to the classes requiring dependencies.
        - Inject dependencies with `@Inject` annotations.

   ```kotlin
   @Module
   class NetworkModule {
       @Provides
       fun provideRetrofit(): Retrofit {
           return Retrofit.Builder().baseUrl("https://example.com").build()
       }
   }

   @Component(modules = [NetworkModule::class])
   interface AppComponent {
       fun inject(activity: MainActivity)
   }
   ```

2. **Hilt**:
    - Hilt is built on top of Dagger and simplifies the setup of DI in Android applications. It abstracts much of the boilerplate code by providing default components like `ApplicationComponent` and `ActivityComponent` and integrates seamlessly with the Android lifecycle.
    - **Usage**:
        - Annotate your `Application` class with `@HiltAndroidApp`.
        - Annotate Android components (e.g., Activities, Fragments) with `@AndroidEntryPoint` to indicate they are injection targets.
        - Use `@Inject` to inject dependencies into fields, constructors, or methods.
        - Hilt modules are similar to Dagger’s but use `@InstallIn` to specify the component scope.

   ```kotlin
   @HiltAndroidApp
   class MyApplication : Application()

   @AndroidEntryPoint
   class MainActivity : AppCompatActivity() {
       @Inject lateinit var retrofit: Retrofit
   }

   @Module
   @InstallIn(SingletonComponent::class)
   object NetworkModule {
       @Provides
       fun provideRetrofit(): Retrofit {
           return Retrofit.Builder().baseUrl("https://example.com").build()
       }
   }
   ```

#### Summary:
- **Dependency Injection** injects dependencies into a client, decoupling the creation of objects from their usage.
- **Factory Pattern** centralizes object creation in a factory, but the client still requests the objects.
- **Service Locator** requires the client to request dependencies from a central registry, introducing some coupling.
- **Dagger** provides DI in Android via modules and components, while **Hilt** simplifies DI setup by leveraging Dagger under the hood with built-in Android lifecycle components.

#### More Information
- [Dependency injection in Android](https://developer.android.com/training/dependency-injection)
- [Manual dependency injection](https://developer.android.com/training/dependency-injection/manual)
- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt in multi-module apps](https://developer.android.com/training/dependency-injection/hilt-multi-module)