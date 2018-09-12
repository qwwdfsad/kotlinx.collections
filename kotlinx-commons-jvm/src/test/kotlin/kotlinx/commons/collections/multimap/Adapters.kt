@file:Suppress("UNCHECKED_CAST")

package kotlinx.commons.collections.multimap

import com.google.common.collect.*
import kotlinx.commons.collections.multimap.internal.Multimap as InternalMultimap

fun <K, V> InternalMultimap<K, V>.wrap(): SetMultimap<K, V> {
    val mm = this
    return object : SetMultimap<K, V> {
        override fun clear() = mm.clear()

        override fun put(key: K?, value: V?): Boolean = mm.put(key!!, value!!)

        override fun isEmpty(): Boolean = mm.isEmpty()

        override fun containsValue(value: Any?): Boolean = mm.containsValue(value as V)

        override fun remove(key: Any?, value: Any?): Boolean = mm.remove(key as K, value as V)

        override fun asMap(): MutableMap<K, MutableCollection<V>> = mm.asMap() as MutableMap<K, MutableCollection<V>>

        override fun containsEntry(key: Any?, value: Any?): Boolean = mm.containsEntry(key as K, value as V)

        override fun putAll(key: K?, values: MutableIterable<V>): Boolean = putAll(key!!, values)

        override fun putAll(multimap: com.google.common.collect.Multimap<out K, out V>): Boolean {
            var result = false
            multimap.entries().forEach {
                result = put(it.key, it.value) or result
            }

            return result
        }

        override fun values(): MutableCollection<V> = mm.values() as MutableCollection<V>

        override fun removeAll(key: Any?): MutableSet<V> = mm.removeAll(key as K) as MutableSet<V>

        override fun size(): Int = mm.size()

        override fun keySet(): MutableSet<K> = mm.keySet() as MutableSet<K>

        override fun containsKey(key: Any?): Boolean = mm.containsKey(key as K)

        override fun get(key: K?): MutableSet<V> = mm[key as K] as MutableSet<V>

        override fun replaceValues(key: K?, values: MutableIterable<V>): MutableSet<V> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun entries(): MutableSet<MutableMap.MutableEntry<K, V>> = mm.entries() as MutableSet<MutableMap.MutableEntry<K, V>>

        override fun keys(): Multiset<K> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
