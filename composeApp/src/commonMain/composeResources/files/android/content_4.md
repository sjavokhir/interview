### How to Implement Custom Views in Android Using XML

To implement custom views in Android via XML, you need to follow these steps:

#### 1. Create a Custom View Class:
- First, define a new class that extends a base view class (like `View`, `ImageView`, `TextView`, etc.).
- Override the necessary constructors and methods such as `onDraw()`, `onMeasure()`, or `onLayout()` depending on the custom behavior you're implementing.

Example:
```kotlin
class CustomCircleView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
): View(context, attrs, defStyle) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.FILL
        }
        // Draw a red circle at the center
        canvas.drawCircle(width / 2f, height / 2f, width / 4f, paint)
    }
}
```

#### 2. Use Custom View in XML Layout:
- After creating your custom view class, you can reference it directly in your XML layout file. Ensure that the full package name of your custom class is used as the XML tag.
- You can also pass custom attributes to your custom view, which can be defined in XML (see next step).

Example:
```xml
<com.example.CustomCircleView
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:layout_gravity="center"/>
```

#### 3. Add Custom Attributes (Optional):
- Define custom attributes in a new `attrs.xml` file in your `res/values` folder. This allows you to customize your view’s properties from the XML layout.

Example of `attrs.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="CustomCircleView">
        <attr name="circleColor" format="color"/>
        <attr name="circleRadius" format="dimension"/>
    </declare-styleable>
</resource>
```

- In your custom view class, retrieve the custom attributes inside the constructor using `context.obtainStyledAttributes()`.

Example:
```kotlin
class CustomCircleView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyle: Int = 0
): View(context, attrs, defStyle) {

    var circleColor: Int = Color.RED
    var circleRadius: Float = 50f

    init {
        when {
          attrs != null && defStyle != 0 -> getAttrs(attrs, defStyle)
          attrs != null -> getAttrs(attrs)
        }
    }

    private fun getAttrs(attrs: AttributeSet) {
      val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView)
      try {
        setTypeArray(typedArray)
      } finally {
        typedArray.recycle()
      }
    }

    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
      val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView, defStyle, 0)
      try {
        setTypeArray(typedArray)
      } finally {
        typedArray.recycle()
      }
    }       

    private fun setTypeArray(typedArray: TypedArray) {
        circleColor = typedArray.getColor(R.styleable.CustomCircleView_circleColor, Color.RED)
        circleRadius = typedArray.getDimension(R.styleable.CustomCircleView_circleRadius, 50f)
    }
}
```

- Use the custom attributes in your XML file:
```xml
<com.example.CustomCircleView
    android:layout_width="100dp"
    android:layout_height="100dp"
    app:circleColor="@color/blue"
    app:circleRadius="30dp"/>
```

#### 4. Handle Layout Measurement (Optional):
- Override the `onMeasure()` method if you want to control how your custom view measures its dimensions, especially if it behaves differently from standard views.

Example:
```kotlin
override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    val desiredSize = 200
    val width = resolveSize(desiredSize, widthMeasureSpec)
    val height = resolveSize(desiredSize, heightMeasureSpec)
    setMeasuredDimension(width, height)
}
```

#### Summary
Implementing custom views in Android via XML gives you flexibility in UI design. You can create various custom widgets by using the custom view systems and [Canvas](https://developer.android.com/reference/android/graphics/Canvas). For more detailes, refer to the [Android developer documentation on custom views](https://developer.android.com/guide/topics/ui/custom-components).