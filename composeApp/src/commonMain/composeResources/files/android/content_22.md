### How do you store and persist data locally?

Android provides several mechanisms for storing and persisting data locally, each designed for specific use cases such as lightweight key-value storage, structured database management, or file handling. Below are the primary options for local storage:

#### SharedPreferences

[SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) is a simple key-value storage mechanism best suited for lightweight data, such as app settings or user preferences. It allows you to save primitive data types like `Boolean`, `Int`, `String`, and `Float` and persists them across app restarts. SharedPreferences operates synchronously, but with the introduction of DataStore, it’s becoming less favored for modern applications.

```kotlin
val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
val editor = sharedPreferences.edit {
  putString("user_name", "skydoves")
}
```

#### DataStore

[Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) is a more modern, scalable, and efficient replacement for SharedPreferences. It provides two types: `PreferencesDataStore` for key-value storage and `ProtoDataStore` for structured data. Unlike SharedPreferences, DataStore is asynchronous, avoiding potential issues with blocking the main thread.

```kotlin
val dataStore: DataStore<Preferences> = context.createDataStore(name = "settings")

val userNameKey = stringPreferencesKey("user_name")
runBlocking {
    dataStore.edit { settings ->
        settings[userNameKey] = "John Doe"
    }
}
```

#### Room Database

[Room Database](https://developer.android.com/training/data-storage/room) is a high-level abstraction over SQLite, designed for handling structured and relational data. It simplifies database management with annotations, compile-time checks, and LiveData or Flow support for reactive programming. Room is ideal for apps requiring complex queries or large amounts of structured data.

```kotlin
@Entity
data class User(
    @PrimaryKey val id: Int,
    val name: String
)

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: Int): User
}

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

#### File Storage

For binary or custom data, Android allows you to store files in internal or external storage. Internal storage is private to your app, while external storage can be shared with other apps. File I/O operations can be used for tasks like storing images, videos, or custom serialized data.

```kotlin
val file = File(context.filesDir, "user_data.txt")
file.writeText("Sample user data")
```

#### Choosing the Right Solution

The choice of storage mechanism depends on the nature and complexity of the data:
- Use SharedPreferences or DataStore for lightweight, non-relational data such as settings or flags.
- Use Room for complex relational data with query requirements.
- Use File Storage for binary files or large custom datasets.

Each mechanism offers specific advantages tailored to different data management needs, ensuring efficient and reliable data storage on Android.