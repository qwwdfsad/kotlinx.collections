package kotlinx.commons.collections.multimap.internal

// TODO wrap all collections into proxies to properly reflect changes
abstract class CollectionMultimap<K, V> : Multimap<K, V> {

    private val container: MutableMap<K, MutableCollection<V>> = HashMap()

    protected abstract fun createValuesCollection(): MutableCollection<V>

    override fun size(): Int = entries().size

    override fun isEmpty(): Boolean = size() == 0

    override fun containsKey(key: K): Boolean = container.containsKey(key)

    override fun containsValue(value: V): Boolean = entries().any { it.value == value }

    override fun containsEntry(key: K, value: V): Boolean = get(key).contains(value)

    override fun get(key: K): Collection<V> = container[key] ?: createValuesCollection()

    override fun keySet(): Set<K> = container.keys

    override fun put(key: K, value: V): Boolean {
        val values = container.getOrPut(key) { createValuesCollection() }
        return values.add(value)
    }

    override fun remove(key: K, value: V): Boolean = container[key]?.remove(value) ?: false

    override fun putAll(key: K, values: Iterable<V>): Boolean {
        var result = false
        values.forEach { result = put(key, it) or result }
        return result
    }

    override fun putAll(multimap: Multimap<out K, out V>): Boolean {
        var result = false
        multimap.entries().forEach {
            result = put(it.key, it.value)
        }
        return result
    }

    override fun removeAll(key: K): Collection<V> = container.remove(key) ?: createValuesCollection()

    override fun clear() = container.clear()

    override fun values(): Collection<V> = entries().map { it.value }

    override fun entries(): Collection<Map.Entry<K, V>> =
        container.entries.flatMap { e -> e.value.map { Entry(e.key, it) } }

    override fun asMap(): Map<K, Collection<V>> = container

    private class Entry<K, V>(override val key: K, override val value: V) : Map.Entry<K, V>
}
