### What's are the Compose phases?

Jetpack Compose follows a well-defined rendering pipeline divided into three key phases: **Composition**, **Layout**, and **Drawing**. These phases work sequentially to build, arrange, and render the UI on the screen efficiently.

#### Composition

The Composition phase is responsible for creating descriptions for your composable functions by executing `@Composable` functions and build the UI tree. During this phase, Compose builds the initial UI structure and records the relationships between composables in a data structure called the **Slot Table**. When state changes occur, the composition phase recalculates affected parts of the UI and triggers recomposition where necessary.

**Key Tasks in Composition**:
- Executing `@Composable` functions
- Creating and updating the UI tree
- Tracking changes for recomposition

#### Layout

The Layout phase comes after the Composition phase. It determines the size and position of each UI element based on provided constraints. Each composable measures its children, decides its dimensions, and defines its position relative to its parent.

**Key Tasks in Layout**:
- Measuring UI components
- Defining width, height, and positions
- Arranging children within parent containers

#### Drawing

The Drawing phase is where the composed and laid-out UI elements are rendered onto the screen. Compose uses the Skia graphics engine for this process, ensuring smooth and hardware-accelerated rendering. Custom drawing logic can be implemented using the `Canvas` API in Compose.

**Key Tasks in Drawing**:
- Rendering visual elements
- Drawing UI components onto the screen
- Applying custom drawing operations

#### Summary

Jetpack Compose’s three-phase rendering model ensures a clean, efficient, and scalable UI-building process. The Composition phase builds the UI tree, the Layout phase arranges components, and the Drawing phase renders everything visually. For a deeper understanding, refer to the official Android documentation on [Jetpack Compose Phases](https://developer.android.com/develop/ui/compose/phases), which provides an in-depth explanation of the key phases involved in Compose's UI rendering process.