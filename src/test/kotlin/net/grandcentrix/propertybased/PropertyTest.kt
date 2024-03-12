package net.grandcentrix.propertybased;

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.ObjectMapper
import net.jqwik.api.ForAll
import net.jqwik.api.Property
import org.junit.jupiter.api.Test;

class PropertyTest {

    @Property
    fun `symmetry`(@ForAll list: List<Int>) {
        val serialized = ObjectMapper().writeValueAsString(list)
        val deserialized = ObjectMapper().readValue(serialized, List::class.java)

        //
        assertThat(deserialized).isEqualTo(list)
    }

    @Property
    fun `commutativity`(@ForAll list: List<Int>) {
        val isEven = { it: Int -> it % 2 == 0 }

        // sorting then filtering should have the same effect as filtering then sorting
        val sortedFiltered = list.sorted().filter(isEven)
        val filteredSorted = list.filter(isEven).sorted()

        assertThat(sortedFiltered).isEqualTo(filteredSorted)
    }

    @Property
    fun `invariants`(@ForAll list: List<Int>) {
        val mapped = list.map { it * 2 }
        val sorted = list.sorted()

        // sorting and mapping should never change the size of a collection
        assertThat(mapped.size).isEqualTo(list.size)
        assertThat(sorted.size).isEqualTo(list.size)
    }

    @Property
    fun `idempotence`(@ForAll list: List<Int>){
        val sorted = list.sorted()
        val sortedTwice = sorted.sorted()

        // sorting a sorted list should return the same list
        assertThat(sorted)
            .isEqualTo(sortedTwice)
    }

    @Test
    fun `induction`() {
        // […]
    }

    @Test
    fun `blackbox testing`() {
        // hard to prove, easy to verify
    }

    @Test
    fun `implementation equivalence`() {
        // […]
    }

}
