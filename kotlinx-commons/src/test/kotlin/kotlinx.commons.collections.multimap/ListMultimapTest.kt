package kotlinx.commons.collections.multimap

import kotlin.test.*

class ListMultimapTest {

    @Test
    fun testSameValues() {
        val mm = HashMultimap<Int, Int>()
        mm.put(1, 11)
        mm.put(1, 12)

        println(mm[1])
    }

}