package kotlinx.commons.collections.multimap

/**
 * A collection which maps keys to values, but in contrast with [Map], multiple
 * values can be associated with single key.
 * Uniqueness of the values is determined by the specific implementation, e.g.
 * [ListMultimap] and [HashMultimap].
 *
 * TODO views, Guava interopability/copyrights (?)
 *
 * This interface is designed to be directly mapped on Guava's Multimap on JVM to provide
 * smoother interoperability with Java applications.
 */
public expect interface Multimap<K, V>  {

    /**
     * Returns the number of key-value pairs (not a number of distinct keys!) in this multimap.
     */
    fun size(): Int

    /**
     * Returns `true` if this multimap contains no keys.
     */
    fun isEmpty(): Boolean

    /**
     * Returns `true` if this multimap contains at least one value associated with [key].
     */
    fun containsKey(key: K): Boolean

    /**
     * Returns `true` if this multimap contains given [value]
     */
    fun containsValue(value: V): Boolean

    /**
     * Returns `true` if this multimap contains at least one pair with [key] and [value]
     */
    fun containsEntry(key: K, value: V): Boolean

    /**
     * Returns a view collection of the values associated with [key] in this multimap, if any.*
     * Changes to the returned collection will update the underlying multimap, and vice versa.
     */
    operator fun get(key: K): Collection<V>

    /**
     * Returns a view collection of all *distinct* keys contained in this multimap. Note that the
     * key set contains a key if and only if this multimap maps that key to at least one value.
     *
     * Changes to the returned set will update the underlying multimap, and vice versa. However,
     * *adding* to the returned set is not possible.
     */
    fun keySet(): Set<K>

    /**
     * Associates the specified [value] with the [key] in the multimap.
     * Returns `true` if the [value] was successfully added and `false` if
     * multimap already contained given key-value pair and does not allow duplicates.
     */
    fun put(key: K, value: V): Boolean

    /**
     * Removes a single [value] associated with [key].
     * Returns `true` if the [value] was removed
     */
    fun remove(key: K, value: V): Boolean
    
    /**
     * *
     * Associates the specified [values] with the [key] in the multimap.
     * Returns `true` if any of the [values] was successfully added and `false` if
     * multimap already contained all given key-value pairs and does not allow duplicates.
     *
     * Semantically it is indistinguishable from
     * ```
     * values.forEach { map.put(key, it) }
     * ```
     */
    fun putAll(key: K, values: Iterable<V>): Boolean

    /**
     * Stores all key-value pairs of [multimap] in this multimap.
     *
     * Returns `true` if any values from [multimap] was successfully added.
     */
    fun putAll(multimap: Multimap<out K, out V>): Boolean

    /**
     * Removes all values associated with the [key].
     * Returns values that were removed (possibly empty).
     */
    fun removeAll(key: K): Collection<V>

    /**
     * Removes all key-value pairs from this multimap
     */
    fun clear()

    /**
     * Returns a view collection containing all values from each key-value pair contained in this multimap.
     *
     * Changes to the returned collection will update the underlying multimap, and vice versa.
     * However, *adding* to the returned collection is not possible.
     */
    fun values(): Collection<V>

    /**
     * Returns a view collection of all key-value pairs contained in this multimap.
     *
     * Changes to the returned collection or the entries it contains will update the underlying
     * multimap, and vice versa. However, *adding* to the returned collection is not possible.
     */
    fun entries(): Collection<Map.Entry<K, V>>

    /**
     * Returns a view of this multimap as a [Map] from each distinct key to the nonempty
     * collection of that key's associated values.
     */
    fun asMap(): Map<K, Collection<V>>
}
