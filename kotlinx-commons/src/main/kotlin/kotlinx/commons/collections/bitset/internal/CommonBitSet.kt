package kotlinx.commons.collections.bitset.internal

import kotlin.math.*

/**
 * TODO
 */
open class CommonBitSet {
    protected var words: LongArray

    // Index of last used word used to speedup operators over bitset
    protected var lastUsedWord = 0

    public constructor(sizeInBits: Int) {
        require(sizeInBits > 0) { "Size cannot be negative, have $sizeInBits" }
        words = LongArray(wordIndex(sizeInBits - 1) + 1)
    }

    public constructor() : this(64)

    public constructor(words: LongArray) {
        this.words = words
        this.lastUsedWord = words.size
        updateUsedWords()
    }

    private constructor(words: LongArray, wordsInUse: Int) {
        this.words = words
        this.lastUsedWord = wordsInUse
    }

    private fun updateUsedWords() {
        var i = lastUsedWord - 1
        while (i >= 0) {
            if (words[i] != 0L)
                break
            --i
        }

        lastUsedWord = i + 1
    }

    operator fun get(index: Int): Boolean {
        checkIndex(index)
        val wordIndex = wordIndex(index)
        return ((wordIndex < lastUsedWord) && ((words[wordIndex] and (1L shl index)) != 0L))
    }

    fun set(index: Int) {
        checkIndex(index)
        val wordIndex = wordIndex(index)
        expand(wordIndex)
        words[wordIndex] = words[wordIndex] or (1L shl index) // Restores invariants
    }

    operator fun set(index: Int, value: Boolean) {
        if (value) set(index)
        else clear(index)
    }

    fun clear(index: Int) {
        checkIndex(index)
        val wordIndex = wordIndex(index)
        if (wordIndex >= lastUsedWord) {
            return
        }
        words[wordIndex] = words[wordIndex] and (1L shl index).inv()
        updateUsedWords()
    }

    fun clear() {
        while (lastUsedWord > 0) {
            words[--lastUsedWord] = 0
        }
    }

    fun isEmpty(): Boolean = lastUsedWord == 0

    fun cardinality(): Int {
        var sum = 0
        for (i in 0 until lastUsedWord) {
            sum += bitCount(words[i])
        }

        return sum
    }

    fun and(set: CommonBitSet) {
        if (this === set)
            return

        while (lastUsedWord > set.lastUsedWord) {
            words[--lastUsedWord] = 0
        }

        // Perform logical AND on words in common
        for (i in 0 until lastUsedWord)
            words[i] = words[i] and set.words[i]

        updateUsedWords()
    }

    fun or(set: CommonBitSet) {
        if (this === set)
            return

        val wordsInCommon = min(lastUsedWord, set.lastUsedWord)

        if (lastUsedWord < set.lastUsedWord) {
            ensureCapacity(set.lastUsedWord)
            lastUsedWord = set.lastUsedWord
        }

        // Perform logical OR on words in common
        for (i in 0 until wordsInCommon)
            words[i] = words[i] or set.words[i]

        // Copy any remaining words
        if (wordsInCommon < set.lastUsedWord) {
            arraycopy(
                set.words, wordsInCommon,
                words, wordsInCommon,
                lastUsedWord - wordsInCommon
            )
        }
    }

    fun xor(set: CommonBitSet) {
        val wordsInCommon = min(lastUsedWord, set.lastUsedWord)

        if (lastUsedWord < set.lastUsedWord) {
            ensureCapacity(set.lastUsedWord)
            lastUsedWord = set.lastUsedWord
        }

        // Perform logical XOR on words in common
        for (i in 0 until wordsInCommon)
            words[i] = words[i] xor set.words[i]

        // Copy any remaining words
        if (wordsInCommon < set.lastUsedWord) {
            arraycopy(
                set.words, wordsInCommon,
                words, wordsInCommon,
                set.lastUsedWord - wordsInCommon
            )
        }

        updateUsedWords()
    }

    fun andNot(set: CommonBitSet) {
        // Perform logical (a & !b) on words in common
        for (i in min(lastUsedWord, set.lastUsedWord) - 1 downTo 0) {
            words[i] = words[i] and set.words[i].inv()
        }

        updateUsedWords()
    }

    fun copy(): CommonBitSet {
        val newWords = words.copyOf()
        val wordsInUse = lastUsedWord
        return CommonBitSet(newWords, wordsInUse)
    }

    override fun hashCode(): Int {
        // java.util.Bitset-compatible hashcode
        var h: Long = 1234
        for (i in (lastUsedWord - 1) downTo 0) {
            h = h xor words[i] * (i + 1)
        }

        return ((h shr 32) xor h).toInt()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is CommonBitSet)
            return false
        if (this === other)
            return true

        if (lastUsedWord != other.lastUsedWord) {
            return false
        }

        for (i in 0 until lastUsedWord) {
            if (words[i] != other.words[i]) {
                return false
            }
        }

        return true
    }

    override fun toString(): String = buildString {
        append('{')
        for (i in 0 until lastUsedWord) {
            val word = words[i]
            if (word == 0L) continue
            for (index in 0..63) {
                if (word and (1L shl index) != 0L) {
                    if (length != 1) append(", ")
                    append(64 * i + index)
                }
            }
        }

        append('}')
    }

    private fun checkIndex(index: Int) {
        if (index < 0) throw IndexOutOfBoundsException("index cannot be negative: $index")
    }

    private fun wordIndex(index: Int): Int {
        return index shr 6 // 2^6 == 64
    }

    private fun ensureCapacity(wordsRequired: Int) {
        if (words.size < wordsRequired) {
            val newSize = max(2 * words.size, wordsRequired)
            words = words.copyOf(newSize)
        }
    }

    private fun expand(wordIndex: Int) {
        val wordsRequired = wordIndex + 1
        if (lastUsedWord < wordsRequired) {
            ensureCapacity(wordsRequired)
            lastUsedWord = wordsRequired
        }
    }
}
