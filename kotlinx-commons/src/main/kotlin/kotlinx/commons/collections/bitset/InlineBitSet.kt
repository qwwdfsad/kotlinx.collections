package kotlinx.commons.collections.bitset

import kotlinx.commons.collections.bitset.internal.*


/**
 * Experimental inline bitset represented as single long without additional footprint.
 * Should be used when it's known that bitset set does not exceed 64 to have an allocation-free bitset.
 */
@Suppress("EXPERIMENTAL_FEATURE_WARNING")
inline class InlineBitSet(private val word: Long) {

    public constructor() : this(0L)

    /**
     * Returns a copy of the current bitset with a bit at the [index] to `true`.
     * Throws [IndexOutOfBoundsException] is [index] is negative.
     */
    fun set(index: Int): InlineBitSet {
        checkIndex(index)
        val mask = (1 shl index).toLong()
        return InlineBitSet(word or mask)
    }

    /**
     * Returns a copy of the current bitset with a bit at the [index] to given [value].
     * Throws [IndexOutOfBoundsException] is [index] is out of range `0..63`.
     */
    fun set(index: Int, value: Boolean): InlineBitSet {
        return if (value) set(index) else clear(index)
    }

    /**
     * Returns a copy of the current bitset with a bit at the [index] set to `false`.
     * Throws [IndexOutOfBoundsException] is [index] is out of range `0..63`.
     */
    fun clear(index: Int): InlineBitSet {
        checkIndex(index)
        val mask = (1 shl index).inv().toLong()
        return InlineBitSet(word and mask)
    }

    /**
     * Returns `true` if bit at the given [index] is set or `false` otherwise.
     * Throws [IndexOutOfBoundsException] is [index] is out of range `0..63`.
     */
    operator fun get(index: Int): Boolean {
        checkIndex(index)
        val mask = (1 shl index).toLong()
        return (word and mask) != 0L
    }

    fun andNot(set: InlineBitSet): InlineBitSet = InlineBitSet(word and set.word.inv())

    fun and(set: InlineBitSet): InlineBitSet = InlineBitSet(word and set.word)

    fun or(set: InlineBitSet): InlineBitSet = InlineBitSet(word or set.word)

    fun xor(set: InlineBitSet): InlineBitSet = InlineBitSet(word xor set.word)

    fun cardinality(): Int = bitCount(word)

    private fun checkIndex(index: Int) {
        require(index in 0..63) { "Index out of range: $index" }
    }
}

//@Suppress("NOTHING_TO_INLINE") TODO JsName
//inline fun InlineBitSet(): InlineBitSet = InlineBitSet(0)
