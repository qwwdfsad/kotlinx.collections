package kotlinx.commons.collections.multimap

import com.google.common.collect.*

public actual fun <K, V> ListMultimap(initialKeysCapacity: Int, initialValuesCapacity: Int): Multimap<K, V> = ArrayListMultimap.create(initialKeysCapacity, initialValuesCapacity)

public actual fun <K, V> HashMultimap(initialKeysCapacity: Int, initialValuesCapacity: Int): Multimap<K, V> =  HashMultimap.create(initialKeysCapacity, initialValuesCapacity)