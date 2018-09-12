package kotlinx.commons.collections.multimap.internal

internal class ListMultimap<K, V> : CollectionMultimap<K, V>() {
    override fun createValuesCollection(): MutableCollection<V> = ArrayList()
}

internal class HashMultimap<K, V> : CollectionMultimap<K, V>() {
    override fun createValuesCollection(): MutableCollection<V> = HashSet()
}
