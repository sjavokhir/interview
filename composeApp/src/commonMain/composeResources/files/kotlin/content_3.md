### What's Sealed Class in Kotlin?

In Kotlin, a **sealed class** is a special kind of class that restricts the hierarchy of its subclasses. Unlike a regular class, a sealed class defines a closed set of types, meaning that all possible subclasses of the sealed class are known at compile time. This makes sealed classes useful for representing restricted hierarchies, especially when modeling states or results where only specific options are allowed.

#### Key Characteristics of Sealed Classes:

- **Closed Hierarchy**: All subclasses of a sealed class must be defined in the same package as the sealed class. This ensures that no new subclasses can be created elsewhere, maintaining a fixed and controlled hierarchy.
- **Exhaustive when used in `when` expressions**: When a sealed class is used in a `when` expression, the compiler knows all possible subclasses, so it enforces that all cases are handled. If all subclasses are not handled, the code will not compile.
- **Flexibility of subclass types**: The subclasses can be regular classes, data classes, or even object declarations.

#### Syntax Example:

```kotlin
sealed class Result {
    data class Success(val data: String) : Result()
    data class Error(val errorMessage: String) : Result()
    data object Loading : Result()
}

fun handleResult(result: Result) {
    when (result) {
        is Result.Success -> println("Data: ${result.data}")
        is Result.Error -> println("Error: ${result.errorMessage}")
        Result.Loading -> println("Loading...")
    }
}
```

#### When to Use Sealed Classes:

- **Modeling Finite States**: Sealed classes are perfect for modeling finite states in applications, such as network/database states (loading, success, error), user inputs (click, scroll, request), or ui states (loading, fetched, error).
- **Data Wrapping**: You can use sealed classes to wrap different data types and handle them in a type-safe manner.

#### Difference from Regular Classes:

- In regular classes, new subclasses can be created anywhere. In sealed classes, all subclasses are defined in the same package, ensuring the class hierarchy is controlled and finite.