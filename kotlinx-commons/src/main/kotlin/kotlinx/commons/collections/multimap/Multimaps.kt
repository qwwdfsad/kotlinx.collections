package kotlinx.commons.collections.multimap


public expect fun <K, V> ListMultimap(initialKeysCapacity: Int = 16, initialValuesCapacity: Int = 4): Multimap<K, V>

public expect fun <K, V> HashMultimap(initialKeysCapacity: Int = 16, initialValuesCapacity: Int = 4): Multimap<K, V>