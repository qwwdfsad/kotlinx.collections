package kotlinx.commons.collections.bitset.internal

// TODO intrinsify everything on JVM

internal fun bitCount(word: Long): Int {
    // Hacker's delight algo
    var i = word
    i -= (i.ushr(1) and 0x5555555555555555L)
    i = (i and 0x3333333333333333L) + (i.ushr(2) and 0x3333333333333333L)
    i = i + i.ushr(4) and 0x0f0f0f0f0f0f0f0fL
    i += i.ushr(8)
    i += i.ushr(16)
    i += i.ushr(32)
    return i.toInt() and 0x7f
}

internal fun arraycopy(source: LongArray, srcPos: Int, destination: LongArray,
                           destinationStart: Int, length: Int) {
    var destinationIndex = destinationStart
    for (sourceIndex in srcPos until srcPos + length) {
        destination[destinationIndex++] = source[sourceIndex]
    }
}
