### What is data class in Kotlin? How does a data class in Kotlin differ from a regular class?

In Kotlin, a **data class** is a special type of class specifically designed to hold data. Kotlin generates several useful methods automatically for data classes, which makes them ideal for representing simple data-holding objects.

#### Key Features of Data Classes:

When you declare a data class, Kotlin automatically generates the following:
1. **`equals()`**: Compares two instances of the class for equality based on their properties.
2. **`hashCode()`**: Generates a hash code based on the properties.
3. **`toString()`**: Provides a string representation of the object with its property values.
4. **`copy()`**: Allows for creating a new object with some properties copied from the existing one, with the option to modify specific values.
5. **Component functions**: For destructuring declarations (e.g., `component1()`, `component2()`), allowing you to extract properties easily.

#### Example:
```kotlin
data class User(val name: String, val age: Int)
```

In the example above, Kotlin automatically provides `equals()`, `hashCode()`, `toString()`, and `copy()` for the `User` class.

#### Differences Between Data Class and Normal Class:
1. **Boilerplate Reduction**: In a normal class, you would need to manually override `equals()`, `hashCode()`, `toString()`, and other utility methods. With a data class, Kotlin generates these for you automatically.

2. **Primary Constructor Requirement**: A data class requires at least one property to be declared in the primary constructor, whereas a normal class does not.

3. **Use Case**: Data classes are primarily used for holding immutable data (though you can use mutable properties), whereas normal classes can be used for any kind of behavior or logic.

#### Example of Normal Class for Comparison:

```kotlin
class Person(val name: String, val age: Int)
```
In a normal class like this, you'd need to manually implement `equals()`, `hashCode()`, and `toString()` if you wanted similar functionality to a data class.

#### In Summary:

- **Data classes** are used for objects that only contain data, and Kotlin automatically generates utility methods like `equals()`, `hashCode()`, `toString()`, and `copy()`.
- A **normal class** is more flexible but doesn't provide those methods by default, making it more suited for objects with behavior and complex logic.