package kotlinx.commons.collections.bitset

import kotlin.math.*
import kotlin.random.*
import kotlin.test.*

class InlineBitSetTest {
    private val generator = Random(42)

    @Test
    fun testClear() {
        repeat(1000) {
            var target = InlineBitSet(0L)
            for (x in 0 until generator.nextInt(64)) {
                target = target.set(generator.nextInt(64))
            }

            for (i in 0..63) {
                target = target.clear(i)
            }

            assertEquals(InlineBitSet(0L), target)
            assertEquals(0, target.cardinality())
        }
    }

    @Test
    fun testSet() {
        var target = InlineBitSet(0L)
        for (i in 0..63) {
            target = target.set(i)
        }

        assertEquals(InlineBitSet(0.inv()), target)
        assertEquals(64, target.cardinality())

        for (i in 0..30) {
            assertEquals(InlineBitSet(2.0.pow(i.toDouble()).toLong()), InlineBitSet(0L).set(i))
            assertNotEquals(InlineBitSet(2.0.pow(i - 1).toLong()), InlineBitSet(0L).set(i))
            assertNotEquals(InlineBitSet(2.0.pow(i + 1).toLong()), InlineBitSet(0L).set(i))
        }
    }

    @Test
    fun testOr() = testBinaryOp({ a, b -> a.or(b) }, Boolean::or)

    @Test
    fun testXor() = testBinaryOp({ a, b -> a.xor(b) }, Boolean::xor)

    @Test
    fun testAnd() = testBinaryOp({ a, b -> a.and(b) }, Boolean::and)

    @Test
    fun testAndNot() = testBinaryOp({ a, b -> a.andNot(b) }, { a, b -> a && !b })

    private fun testBinaryOp(
        setOp: (InlineBitSet, InlineBitSet) -> InlineBitSet,
        binaryOp: (Boolean, Boolean) -> Boolean
    ) {
        repeat(1000) {
            var first = InlineBitSet(0L)
            var second = InlineBitSet(0L)
            for (i in 0 until generator.nextInt(31)) {
                val bit = generator.nextInt(32)
                first = first.set(bit)
                second = second.set(63 - bit)
            }

            var reference = InlineBitSet(0L)
            for (i in 0..63) {
                reference = reference.set(i, binaryOp(first[i], second[i]))
            }

            val result = setOp(first, second)
            assertEquals(reference, result)
            assertEquals(reference.cardinality(), result.cardinality())

        }
    }
}
