package net.grandcentrix.propertybased

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThanOrEqualTo
import net.jqwik.api.Assume
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import net.jqwik.api.Report
import net.jqwik.api.Reporting
import net.jqwik.api.constraints.AlphaChars
import net.jqwik.api.constraints.Positive
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class CalculatorTest {

    @Test
    fun `test addition example based`() {
        val calculator = Calculator()
        calculator.add(2)
        assertThat(calculator.result).isEqualTo(2)
    }

    @ParameterizedTest
    @ValueSource(ints = [1, 2, 3, 4, Int.MAX_VALUE, Int.MIN_VALUE])
    fun `test addition parameterized example based`(x: Int) {
        val calculator = Calculator()
        calculator.add(x)
        assertThat(calculator.result).isEqualTo(x)
    }

    @Property(tries = 100000)
    fun `test addition property based`(@ForAll x: Int) {
        val calculator = Calculator()
        calculator.add(x)
        assertThat(calculator.result).isEqualTo(x)
    }

    @Test
    fun `test division example based`() {
        val calculator = Calculator()
        calculator.add(10)
        calculator.div(10)
        assertThat(calculator.result).isEqualTo(1)
    }

    @Property(tries = 50)
    @Report(Reporting.GENERATED)
    fun `test division property based`(@ForAll x: Int) {
        Assume.that(x != 0)

        val calculator = Calculator()
        calculator.add(x)
        calculator.div(x)
        assertThat(calculator.result).isEqualTo(1)
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
    fun `length of concatenated string is greater than length of each`(
        @ForAll string1: String, @ForAll string2: String
    ) {
        val conc = string1 + string2
        assertThat(conc.length).isGreaterThanOrEqualTo(string1.length)
        assertThat(conc.length).isGreaterThanOrEqualTo(string2.length)
    }
}