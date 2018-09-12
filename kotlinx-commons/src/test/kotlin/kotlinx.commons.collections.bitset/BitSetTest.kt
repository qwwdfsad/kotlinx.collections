package kotlinx.commons.collections.bitset

import kotlinx.commons.collections.bitset.internal.*
import kotlin.random.*
import kotlin.test.*

class BitSetTest {

    @Test
    fun testOr() {
        val bs = CommonBitSet()
        val bs2 = CommonBitSet()
        bs.xor(bs2)
        assertTrue(bs.isEmpty())
        assertTrue(bs2.isEmpty())
        repeat(10) {
            bs.set(it)
            if (it % 2 == 0) {
                bs2.set(it)
            } else {
                bs[it] = true
            }
        }

        bs.or(bs2)

        repeat(10) {
            assertTrue(bs[it])
        }
    }

    @Test
    fun testAnd() {
        val bs = CommonBitSet()
        val bs2 = CommonBitSet()
        bs.xor(bs2)
        assertTrue(bs.isEmpty())
        assertTrue(bs2.isEmpty())
        repeat(10) {
            bs.set(it)
            if (it % 2 == 0) {
                bs2.set(it)
            }
        }

        bs.and(bs2)

        repeat(10) {
            assertEquals(it % 2 == 0, bs[it])
        }

        bs.and(bs2)
        repeat(10) {
            assertEquals(it % 2 == 0, bs[it])
        }
    }

    @Test
    fun testAndNot() {
        val bs = CommonBitSet()
        val bs2 = CommonBitSet()
        bs.xor(bs2)
        assertTrue(bs.isEmpty())
        assertTrue(bs2.isEmpty())
        repeat(10) {
            bs.set(it)
            if (it % 2 == 0) {
                bs2.set(it)
            }
        }

        bs.andNot(bs2)
        repeat(10) {
            assertEquals(it % 2 != 0, bs[it])
        }
    }

    @Test
    fun testXor() {
        val bs = CommonBitSet()
        val bs2 = CommonBitSet()
        bs.xor(bs2)
        assertTrue(bs.isEmpty())
        assertTrue(bs2.isEmpty())
        repeat(10) {
            bs.set(it)
            if (it % 2 == 0) {
                bs2.set(it)
            }
        }

        val copy = bs.copy()
        bs.xor(bs2)
        repeat(10) {
            assertEquals(it % 2 != 0, bs[it])
        }

        assertNotEquals(bs, copy)
        bs.xor(bs2)
        assertEquals(bs, copy)
    }

    @Test
    fun testIsEmpty() {
        val bs = CommonBitSet()
        assertTrue(bs.isEmpty())

        bs.set(0)
        assertFalse(bs.isEmpty())

        bs.clear(0)
        assertTrue(bs.isEmpty())
    }

    @Test
    fun testClear() {
        val bs = CommonBitSet()
        assertTrue(bs.isEmpty())

        bs.clear()
        assertTrue(bs.isEmpty())

        bs.set(1)
        bs.set(3)
        bs.set(239)
        assertFalse(bs.isEmpty())

        bs.clear()
        assertTrue(bs.isEmpty())
    }

    @Test
    fun testClearIndex() {
        val bs = CommonBitSet()
        assertTrue(bs.isEmpty())
        bs.clear(Int.MAX_VALUE)
        bs.clear(Int.MAX_VALUE - 1)
        assertTrue(bs.isEmpty())

        bs.set(1)
        bs.set(2)
        bs.set(3)
        assertFalse(bs.isEmpty())

        bs.clear(1)
        assertFalse(bs.isEmpty())

        bs.clear(2)
        assertFalse(bs.isEmpty())

        bs.clear(3)
        assertTrue(bs.isEmpty())
    }

    @Test
    fun testClearIndexException() {
        assertFailsWith<IndexOutOfBoundsException> {
            CommonBitSet().clear(-1)
        }
    }

    @Test
    fun testCopy() = repeat(100) {
        val bs = CommonBitSet()
        repeat(Random.nextInt(0..100)) {
            bs.set(Random.nextInt(1000))
        }

        assertEquals(bs, bs.copy())
        assertNotSame(bs, bs.copy())
    }

    @Test
    fun testToString() {
        val bs = CommonBitSet()
        assertEquals("{}", bs.toString())

        bs.set(0)
        assertEquals("{0}", bs.toString())

        bs.clear(0)
        assertEquals("{}", bs.toString())

        bs.set(255)
        bs.set(239)
        assertEquals("{239, 255}", bs.toString())

    }

    @Test
    fun testEquals() {
        val first = CommonBitSet()
        val second = CommonBitSet()
        assertEquals(first, second)

        first.set(239)
        assertNotEquals(first, second)
        first.clear(239)
        assertEquals(first, second)

        first.set(34)
        second.set(34)
        assertEquals(first, second)
        assertEquals(first, first)
    }

    @Test
    fun testEqHcToStrRandom() = repeat(100) {
        val bs1 = CommonBitSet()
        val bs2 = CommonBitSet()
        repeat(Random.nextInt(0..100)) {
            val toss = Random.nextInt(1000)
            bs1.set(toss)
            bs2.set(toss)
        }

        assertEquals(bs1, bs2)
        assertEquals(bs1, bs1)
        assertEquals(bs1.hashCode(), bs2.hashCode())
        assertEquals(bs1.toString(), bs2.toString())
    }
}
