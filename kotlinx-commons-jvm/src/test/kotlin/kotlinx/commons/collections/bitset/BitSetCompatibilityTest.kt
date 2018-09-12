package kotlinx.commons.collections.bitset

import kotlinx.commons.collections.bitset.internal.*
import org.junit.Test
import java.util.BitSet
import java.util.concurrent.*
import kotlin.random.Random
import kotlin.test.*

class BitSetCompatibilityTest {

    @Test
    fun testOr() = testBinaryOp(CommonBitSet::or, BitSet::or)

    @Test
    fun testXor() = testBinaryOp(CommonBitSet::xor, BitSet::xor)

    @Test
    fun testAnd() = testBinaryOp(CommonBitSet::and, BitSet::and)

    @Test
    fun testAndNot() = testBinaryOp(CommonBitSet::andNot, BitSet::andNot)

    private fun testBinaryOp(
        setOp: (CommonBitSet, CommonBitSet) -> Unit,
        juSetOp: (BitSet, BitSet) -> Unit
    ) = repeat(10_000) {
        val bs1 = CommonBitSet()
        val bs2 = CommonBitSet()
        val jbs1 = BitSet()
        val jbs2 = BitSet()

        repeat(Random.nextInt(0..200)) {
            val index1 = ThreadLocalRandom.current().nextInt(200)
            val index2 = ThreadLocalRandom.current().nextInt(200)

            bs1.set(index1)
            jbs1.set(index1)

            bs2.set(index2)
            jbs2.set(index2)
        }

        setOp(bs1, bs2)
        juSetOp(jbs1, jbs2)
        assertEquals(jbs1.toString(), bs1.toString())
        assertEquals(jbs2.toString(), bs2.toString())
    }

    @Test
    fun testHashCode() = repeat(10_000) {
        val bs = CommonBitSet()
        val jbs = BitSet()

        repeat(Random.nextInt(0..200)) {
            val index = ThreadLocalRandom.current().nextInt(200)
            bs.set(index)
            jbs.set(index)
        }

        assertEquals(jbs.hashCode(), bs.hashCode())
        assertEquals(jbs.cardinality(), bs.cardinality())

    }

    @Test
    fun testToString() = repeat(10_000) {
        val bs = CommonBitSet()
        val jbs = BitSet()

        repeat(Random.nextInt(0..200)) {
            val index = ThreadLocalRandom.current().nextInt(200)
            bs.set(index)
            jbs.set(index)
        }

        assertEquals(jbs.toString(), bs.toString())
    }
}
