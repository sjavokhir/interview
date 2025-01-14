### `Modifier.drawWithContent` in Jetpack Compose

`Modifier.drawWithContent` allows you to perform custom drawing operations using `DrawScope` either before or after rendering the content of a composable. To ensure the composable's actual content is displayed, you must explicitly call `drawContent()` within your custom drawing logic.

This flexibility enables you to control the sequence of operations, deciding whether your custom drawing occurs before or after the composable's content is drawn. If you want to create a "flashlight keyhole" effect by overlaying a radial gradient on top of your content, you can implement it as shown below:

```kotlin
var pointerOffset by remember {
    mutableStateOf(Offset(0f, 0f))
}
Column(
    modifier = Modifier
        .fillMaxSize()
        .pointerInput("dragging") {
            detectDragGestures { change, dragAmount ->
                pointerOffset += dragAmount
            }
        }
        .onSizeChanged {
            pointerOffset = Offset(it.width / 2f, it.height / 2f)
        }
        .drawWithContent {
            drawContent()
            // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
            drawRect(
                Brush.radialGradient(
                    listOf(Color.Transparent, Color.Black),
                    center = pointerOffset,
                    radius = 100.dp.toPx(),
                )
            )
        }
) {
    // Your composables here
}
```