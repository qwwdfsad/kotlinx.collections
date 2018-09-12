package kotlinx.commons.collections.bitset

/**
 * Class implementing a dynamic array of bits, indexed by a non-negative integer value with value represented as boolean.
 * [BitSet] has no constraint on the largest index of set bit, internal storage is expanded
 * automatically when required.
 */
expect class BitSet(sizeInBits: Int) {

    // Workaround for default argument
    public constructor()

    /**
     * Sets a bit at the [index] to true.
     * Throws [IndexOutOfBoundsException] is [index] is negative.
     */
    fun set(index: Int)

    /**
     * Sets a bit at the [index] to the provided [value].
     * Throws [IndexOutOfBoundsException] is [index] is negative.
     */
    operator fun set(index: Int, value: Boolean)

    /**
     * Clears a bit at the given [index].
     * Throws [IndexOutOfBoundsException] is [index] is negative.
     */
    fun clear(index: Int)

    /**
     * Returns `true` if bit at the given [index] is set or `false` otherwise.
     * Throws [IndexOutOfBoundsException] is [index] is negative.
     */
    operator fun get(index: Int): Boolean

    /**
     * Returns `true` if at least one bit is set to `true`
     */
    fun isEmpty(): Boolean

    /**
     * Clears all of the bits in the bitset whose corresponding
     * bit is set in the given [set].
     */
    fun andNot(set: BitSet)

    /**
     * Performs [and][Boolean.and] operation between current bitset and given [set].
     */
    fun and(set: BitSet)

    /**
     * Performs [or][Boolean.or] operation between current bitset and given [set].
     */
    fun or(set: BitSet)

    /**
     * Performs [xor][Boolean.xor] operation between current bitset and given [set].
     */
    fun xor(set: BitSet)

    /**
     * Computes the number of set bits
     */
    fun cardinality(): Int
}
