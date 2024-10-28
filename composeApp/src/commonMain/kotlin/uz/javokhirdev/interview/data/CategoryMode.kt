package uz.javokhirdev.interview.data

enum class CategoryMode(
    val title: String,
    val folder: String,
    val filename: String,
) {
    Kotlin(
        title = "Kotlin",
        folder = "files/kotlin",
        filename = "contents_kotlin.json"
    ),
    Android(
        title = "Android",
        folder = "files/android",
        filename = "contents_android.json"
    ),
    Compose(
        title = "Compose",
        folder = "files/compose",
        filename = "contents_compose.json"
    ),
    DesignPatterns(
        title = "Design Patterns",
        folder = "files/designPatterns",
        filename = "contents_design_patterns.json"
    ),
}