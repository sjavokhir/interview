### The Default Android Composition Locals in Jetpack Compose

Have you ever wondered how default `CompositionLocals`, such as `LocalContext`, `LocalConfiguration`, and `LocalLifecycleOwner`, are automatically available even without explicit injection? This behavior is tied to the `setContent` method. Here's a breakdown of the process when `setContent` is called:

#### 1. When you call `ComponentActivity.setContent`, it either restores or creates a `ComposeView`, and then invokes `ComposeView.setContent(content: @Composable () -> Unit)`.

#### 2. In `ComposeView.setContent(content: @Composable () -> Unit)`, the function creates a composition by invoking the `createComposition` method.

```kotlin
fun setContent(content: @Composable () -> Unit) {
    shouldCreateCompositionOnAttachedToWindow = true
    this.content.value = content
    if (isAttachedToWindow) {
        createComposition()
    }
}
```

#### 3. The `createComposition` method calls `ensureCompositionCreated()`, which in turn creates the composition by calling `AbstractComposeView.setContent(CompositionContext): Composition`.

```kotlin
if (composition == null) {
    try {
        creatingComposition = true
        composition = setContent(resolveParentCompositionContext()) {
            Content()
        }
    } finally {
        creatingComposition = false
    }
}
```

#### 4. Setting Up the Default CompositionLocals

During the creation of the wrapped composition in `AbstractComposeView.setContent(CompositionContext): Composition`, the system invokes `ProvideAndroidCompositionLocals`, which provides platform-specific (Android) `CompositionLocals`, including `LocalContext`, `LocalConfiguration`, and `LocalLifecycleOwner`:

```kotlin
@Composable
@OptIn(ExperimentalComposeUiApi::class)
internal fun ProvideAndroidCompositionLocals(
    owner: AndroidComposeView,
    content: @Composable () -> Unit
) {
    val view = owner
    val context = view.context
    // Make a deep copy to compare to later, since the same configuration object will be mutated
    // as part of configuration changes
    var configuration by remember {
        mutableStateOf(Configuration(context.resources.configuration))
    }

    owner.configurationChangeObserver = { configuration = Configuration(it) }

    val uriHandler = remember { AndroidUriHandler(context) }
    val viewTreeOwners = owner.viewTreeOwners ?: throw IllegalStateException(
        "Called when the ViewTreeOwnersAvailability is not yet in Available state"
    )

    val saveableStateRegistry = remember {
        DisposableSaveableStateRegistry(view, viewTreeOwners.savedStateRegistryOwner)
    }
    DisposableEffect(Unit) {
        onDispose {
            saveableStateRegistry.dispose()
        }
    }

    val imageVectorCache = obtainImageVectorCache(context, configuration)
    val resourceIdCache = obtainResourceIdCache(context)
    val scrollCaptureInProgress =
        LocalScrollCaptureInProgress.current or owner.scrollCaptureInProgress
    CompositionLocalProvider(
        LocalConfiguration provides configuration,
        LocalContext provides context,
        LocalLifecycleOwner provides viewTreeOwners.lifecycleOwner,
        LocalSavedStateRegistryOwner provides viewTreeOwners.savedStateRegistryOwner,
        LocalSaveableStateRegistry provides saveableStateRegistry,
        LocalView provides owner.view,
        LocalImageVectorCache provides imageVectorCache,
        LocalResourceIdCache provides resourceIdCache,
        LocalProvidableScrollCaptureInProgress provides scrollCaptureInProgress,
    ) {
        ProvideCommonCompositionLocals(
            owner = owner,
            uriHandler = uriHandler,
            content = content
        )
    }
}
```

As a result, you can easily access and use the following `CompositionLocals` without any manual injection on your part: `LocalConfiguration`, `LocalContext`, `LocalLifecycleOwner`, `LocalSavedStateRegistryOwner`, `LocalSaveableStateRegistry`, `LocalView`, `LocalImageVectorCache`, `LocalResourceIdCache`, and `LocalProvidableScrollCaptureInProgress`. This is made possible simply by calling the `ComponentActivity.setContent()` method, enabling you to seamlessly work with Compose UI throughout your project.