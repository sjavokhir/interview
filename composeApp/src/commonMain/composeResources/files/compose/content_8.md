### Reveal `setContent` in Jetpack Compose

If you examine `setContent`, the entry point for creating the first Compose layout node and composition, you'll find that it actually relies on the traditional view system. Specifically, it attempts to retrieve a `ComposeView` from the main window's `decorView`. If no such object is found, it creates a new `ComposeView` using the provided `CompositionContext`.

```kotlin
public fun ComponentActivity.setContent(
    parent: CompositionContext? = null,
    content: @Composable () -> Unit
) {
    val existingComposeView = window.decorView
        .findViewById<ViewGroup>(android.R.id.content)
        .getChildAt(0) as? ComposeView

    if (existingComposeView != null) with(existingComposeView) {
        setParentCompositionContext(parent)
        setContent(content)
    } else ComposeView(this).apply {
        // Set content and parent **before** setContentView
        // to have ComposeView create the composition on attach
        setParentCompositionContext(parent)
        setContent(content)
        // Set the view tree owners before setting the content view so that the inflation process
        // and attach listeners will see them already present
        setOwners()
        setContentView(this, DefaultActivityContentLayoutParams)
    }
}
```