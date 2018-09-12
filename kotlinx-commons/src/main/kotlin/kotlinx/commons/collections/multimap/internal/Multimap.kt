package kotlinx.commons.collections.multimap.internal

interface Multimap<K, V> {
    fun size(): Int
    fun isEmpty(): Boolean
    fun containsKey(key: K): Boolean
    fun containsValue(value: V): Boolean
    fun containsEntry(key: K, value: V): Boolean
    operator fun get(key: K): Collection<V>
    fun keySet(): Set<K>
    fun put(key: K, value: V): Boolean
    fun remove(key: K, value: V): Boolean
    fun putAll(key: K, values: Iterable<V>): Boolean
    fun putAll(multimap: Multimap<out K, out V>): Boolean
    fun removeAll(key: K): Collection<V>
    fun clear()
    fun values(): Collection<V>
    fun entries(): Collection<Map.Entry<K, V>>
    fun asMap(): Map<K, Collection<V>>
}