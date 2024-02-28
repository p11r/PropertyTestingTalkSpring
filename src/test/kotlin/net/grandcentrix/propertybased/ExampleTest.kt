package net.grandcentrix.propertybased

import assertk.assertThat
import assertk.assertions.containsAtLeast
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isGreaterThanOrEqualTo
import net.jqwik.api.Disabled
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.constraints.AlphaChars
import net.jqwik.api.constraints.Positive
import net.jqwik.api.constraints.Size

class ExampleTest {
    @Property
    @Disabled
    fun `reversing keeps all elements`(@ForAll list: List<Int>) {
        assertThat(list.reversed()).containsAtLeast(list)
    }

    @Property
    fun `reversing twice results in original list`(@ForAll list: List<Int>) {
        assertThat(list.reversed().reversed()).isEqualTo(list)
    }

    @Property
    fun `reversing swaps first and last`(@ForAll @Size(min=2) list: List<Int>) {
        val reversed = list.reversed()
        assertThat(reversed[0]).isEqualTo(list[list.size - 1])
        assertThat(reversed[list.size - 1]).isEqualTo(list[0])
    }
    @Property
    @Disabled
    fun `test square`(@ForAll @Positive a: Int) {
        val result = a * a
        assertThat(result).isGreaterThanOrEqualTo(a)
    }

    @Property
    @Disabled
    fun `string should be shrunk to AA`(@ForAll @AlphaChars aString: String?): Boolean {
        return aString!!.length > 5 || aString.length < 3
    }

    @Property
    @Disabled
    fun `abs value for all ints is positive`(@ForAll i: Int): Boolean {
        return Math.abs(i) >= 0;
    }

    @Property
    @Disabled
    fun `length of concatenated string is greater than length of each`(
        @ForAll string1: String, @ForAll string2: String
    ) {
        val conc = string1 + string2
        assertThat(conc.length).isGreaterThan(string1.length)
        assertThat(conc.length).isGreaterThan(string2.length)
    }
}