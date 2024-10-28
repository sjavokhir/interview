### What is state hoisting in Jetpack Compose?

State hoisting in Jetpack Compose refers to a design pattern where you "hoist" the state up to the caller or parent composable, allowing the parent to control the state while the child composable only focuses on displaying the UI. This concept is inspired by React's state management approach. The main goal of state hoisting is to separate concerns, keeping UI components stateless and promoting reusability and easier testing.

In state hoisting:
- **State** is managed in the parent composable.
- **Events** or **triggers** (like onClick, onValueChange) are passed from the child back to the parent, which updates the state.
- The updated state is then passed back down as parameters to the child, creating a unidirectional data flow.

#### Example:
```kotlin
@Composable
fun Parent() {
    var sliderValue by remember { mutableStateOf(0f) }

    SliderComponent(
        value = sliderValue,
        onValueChange = { sliderValue = it }
    )
}

@Composable
fun SliderComponent(value: Float, onValueChange: (Float) -> Unit) {
    Slider(value = value, onValueChange = onValueChange)
}
```

In this example, the `Parent` composable manages the state (`sliderValue`), while the `SliderComponent` is stateless and receives both the value and the event handler from the parent. This approach promotes better structure and maintainability in Compose applications.

State hoisting in Jetpack Compose offers several benefits below:

1. **Single Source of Truth**: State hoisting ensures that the state is managed in a single place (usually the parent composable), preventing conflicting states between child and parent composables. This improves data consistency across the app.

2. **Reusability**: Since child composables don't manage their own state, they can be reused in different parts of the app. You can pass different states and event handlers, making components more versatile and reusable.

3. **Separation of Concerns**: By hoisting state to the parent, you can keep your child composables stateless, focusing purely on rendering the UI. This makes components simpler, easier to read, and maintain.

4. **Improved Testability**: Stateless composables are easier to test because they don't have to manage state internally. You can pass in different states and event handlers to simulate various scenarios.

5. **Unidirectional Data Flow**: State hoisting enforces a unidirectional data flow, where the state is passed down from the parent, and events are sent back up, making the flow of data more predictable and easier to debug.

6. **Better Control Over Lifecycle**: When the state is managed in the parent, you have better control over its lifecycle. The parent can decide when and how the state should change, which can improve performance and efficiency in managing resources like memory.

These benefits collectively improve the overall structure, maintainability, and scalability of your Jetpack Compose codebase.

#### Summary

You should hoist UI state to the lowest common ancestor of all the composables that need to read or modify that state. Keeping the state as close as possible to where it is consumed helps maintain a clean separation of concerns and ensures efficient data flow. From the state owner, expose an immutable state to consumers along with events or callbacks that allow them to modify the state as needed. For more details, refer to [Where to hoist state](https://developer.android.com/develop/ui/compose/state-hoisting).