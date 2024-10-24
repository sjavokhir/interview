### What's the delegated property?

In Kotlin, [delegated properties](https://kotlinlang.org/docs/delegated-properties.html) allow the delegation of property access to another object, which can handle the getter and setter logic. This is done using the `by` keyword, which connects the property to a delegate that defines how the property will be stored and retrieved.

### How Delegated Properties Work

A delegated property delegates its getter and setter to another object, known as the **delegate**. The delegate manages the property's value and provides custom logic for accessing or modifying it.

### Common Use Cases for Delegated Properties

1. **Lazy Initialization (`lazy`)**:
    - The `lazy` delegate allows for initializing a property only when it's first accessed. It avoids the need to initialize it at object creation.
    - Example:
      ```kotlin
      val lazyValue: String by lazy {
          println("Computed!")
          "Hello, Kotlin!"
      }
      ```
    - In this example, `"Computed!"` will be printed, and the value `"Hello, Kotlin!"` will be assigned only when `lazyValue` is accessed for the first time.

2. **Observable Properties (`Delegates.observable`)**:
    - Allows monitoring property changes by triggering a callback when the value changes.
    - Example:
      ```kotlin
      import kotlin.properties.Delegates
 
      var observableValue: String by Delegates.observable("Initial value") { _, old, new ->
          println("Value changed from $old to $new")
      }
      observableValue = "New value"
      ```
    - This prints `"Value changed from Initial value to New value"` whenever `observableValue` changes.

3. **Vetoable Properties (`Delegates.vetoable`)**:
    - Similar to `observable`, but allows vetoing changes based on a condition.
    - Example:
      ```kotlin
      var vetoableValue: Int by Delegates.vetoable(0) { _, old, new ->
          new > old  // Accept the new value only if it's greater than the old value
      }
      vetoableValue = 5  // Allowed
      vetoableValue = 2  // Not allowed, vetoed
      ```

4. **Storing Properties in a Map (`Map Delegate`)**:
    - Useful for dynamic properties where property names and values are stored in a map.
    - Example:
      ```kotlin
      class User(val map: Map<String, Any?>) {
          val name: String by map
          val age: Int by map
      }
 
      val user = User(mapOf("name" to "John Doe", "age" to 30))
      println(user.name)  // Output: John Doe
      println(user.age)   // Output: 30
      ```

### Summary

Delegated properties in Kotlin provide a way to delegate property access to other objects for specific behavior, such as lazy initialization, change observation, vetoing changes, or mapping property values. This makes the code more concise and modular, allowing developers to separate property management logic from other code.