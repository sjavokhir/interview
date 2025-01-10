### What's LRU Cache?

An **LRU (Least Recently Used) Cache** is a data structure that uses a combination of memory management and caching algorithms to store a limited number of items. When the cache reaches its capacity and a new item needs to be added, the least recently used item is evicted to make space. This ensures the most recently accessed or frequently used items remain in the cache, optimizing performance for systems that deal with repeated access to a subset of data.

#### Key Features of LRU Cache

The primary purpose of an LRU cache is to manage limited storage efficiently while maintaining high-speed data retrieval. Its design ensures that items that have not been accessed for the longest time are removed first, mimicking real-world use cases like paging in operating systems or session management in applications.

Internally, an LRU cache is often implemented using a **hash map** for fast lookups and a **doubly-linked list** for tracking the usage order of elements. This allows the cache to achieve **O(1)** time complexity for both insertion and retrieval operations.

#### Use Cases of LRU Cache

LRU caches are widely used in scenarios where data access patterns exhibit temporal locality, meaning recently accessed data is likely to be accessed again. They are common in database systems for query results, and in memory management for virtual machines.

Imagine a mobile application where images are cached to avoid re-downloading them on every rendering. When the cache limit is reached, the least recently accessed bitmap data is removed to make space for new bitmap data. This approach is widely used in popular image loading libraries, such as Glide, [as demonstrated in the code](https://github.com/bumptech/glide/blob/ca394b1e637f6e33092089eb1bd9ebe4554e78fc/library/src/main/java/com/bumptech/glide/util/LruCache.java#L17).

#### Implementation in Kotlin

You can implement an LRU cache manually or use libraries like Android's `LruCache` class, which provides a built-in solution.

```kotlin
class LRUCache<K, V>(private val capacity: Int) {
    private val map = LinkedHashMap<K, V>(capacity, 0.75f, true)

    fun get(key: K): V? {
        return map[key]
    }

    fun put(key: K, value: V) {
        if (map.size >= capacity) {
            val leastRecentlyUsedKey = map.entries.iterator().next().key
            map.remove(leastRecentlyUsedKey)
        }
        map[key] = value
    }
}

fun main() {
    val cache = LRUCache<Int, String>(3)
    cache.put(1, "A")
    cache.put(2, "B")
    cache.put(3, "C")
    cache.get(1)  // Accessing item 1 makes it recently used
    cache.put(4, "D")  // Evicts item 2 (least recently used)
}
```

In this example, the `LinkedHashMap` is used to maintain the insertion order and track access patterns. When the capacity is exceeded, the least recently used item is evicted.

#### Summary

An LRU Cache is a highly efficient caching mechanism that optimizes memory usage by retaining the most recently used items and discarding the least recently used ones. Its implementation ensures a balance between speed and memory management, making it an ideal choice for scenarios like database caching, web browser history, and application session handling.