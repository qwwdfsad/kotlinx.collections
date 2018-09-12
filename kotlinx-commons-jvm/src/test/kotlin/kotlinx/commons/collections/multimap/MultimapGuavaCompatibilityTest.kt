package kotlinx.commons.collections.multimap

import junit.framework.*
import com.google.common.collect.testing.features.CollectionSize
import com.google.common.collect.testing.features.MapFeature
import com.google.common.collect.SetMultimap
import com.google.common.collect.testing.google.TestStringSetMultimapGenerator
import com.google.common.collect.testing.google.SetMultimapTestSuiteBuilder
import junit.framework.TestSuite


class MultimapGuavaCompatibilityTest : TestCase() {

    companion object {
        @JvmStatic
        fun suite(): Test {
            val suite = TestSuite()
            val ts = SetMultimapTestSuiteBuilder.using(
                object : TestStringSetMultimapGenerator() {
                    override fun create(entries: Array<Map.Entry<String, String>>): SetMultimap<String, String> {
                        val multimap = kotlinx.commons.collections.multimap.internal.HashMultimap<String, String>().wrap()
                        for (entry in entries) {
                            multimap.put(entry.key, entry.value)
                        }

                        return multimap
                    }
                })
                .named("HashMultimap")
                .withFeatures(
                    MapFeature.GENERAL_PURPOSE,
                    MapFeature.FAILS_FAST_ON_CONCURRENT_MODIFICATION,
                    CollectionSize.ANY
                )
                .createTestSuite()
            suite.addTest(ts)
            suite.addTestSuite(MultimapGuavaCompatibilityTest::class.java)
            return suite
        }
    }
}
