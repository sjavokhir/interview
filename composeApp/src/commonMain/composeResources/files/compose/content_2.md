### remember vs. rememberSaveable in Jetpack Compose

In Jetpack Compose, both `remember` and `rememberSaveable` are used to retain state across recompositions, but they have different behaviors when it comes to handling configuration changes (like screen rotations).

#### `remember`
- **Purpose**: Stores and remembers a state in memory during recompositions (e.g., when the UI is redrawn due to state changes), but **does not persist across configuration changes** such as screen rotations.
- **Use Case**: Useful for keeping data alive following the Composable lifesapan and it is still in memory.
- **Limitation**: State stored using `remember` will be lost during configuration changes because it only survives recompositions.

#### `rememberSaveable`
- **Purpose**: Works like `remember`, but additionally persists across configuration changes (like screen rotations) by saving the state in a **SaveableHolder**.
- **Use Case**: Ideal for persisting data that should survive configuration changes, such as form inputs or user selections.
- **Limitation**: By default, it can only save types that are natively supported by Android’s `Bundle`, but you can provide custom `Saver` implementations to support other types.

#### Key Differences:
| Feature                         | `remember`                           | `rememberSaveable`                     |
|----------------------------------|--------------------------------------|----------------------------------------|
| **Retained across recompositions** | Yes                                  | Yes                                    |
| **Retained across configuration changes** | No                                   | Yes                                    |
| **Supported types**              | Any type                             | Types supported by `Bundle`, or custom |

#### In summary

- **Use `remember`** for transient states where it is acceptable for the state to be lost on configuration changes.
- **Use `rememberSaveable`** It behaves similarly to remember, but the stored value will survive the activity or process recreation using the saved instance state mechanism.

So, `rememberSaveable` is more resilient for states that need to persist beyond just recomposition, particularly across configuration changes like screen rotations.