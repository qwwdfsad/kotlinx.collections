package kotlinx.commons.collections.multimap

import kotlinx.commons.collections.multimap.internal.Multimap

@Suppress("ACTUAL_WITHOUT_EXPECT")
actual typealias Multimap<K, V> = Multimap<K, V>

public actual fun <K, V> ListMultimap(initialKeysCapacity: Int, initialValuesCapacity: Int): Multimap<K, V> = kotlinx.commons.collections.multimap.internal.ListMultimap<K, V>()

public actual fun <K, V> HashMultimap(initialKeysCapacity: Int, initialValuesCapacity: Int): Multimap<K, V> = kotlinx.commons.collections.multimap.internal.HashMultimap<K, V>()