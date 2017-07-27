package pouring

import org.junit.Test
import kotlin.test.assertEquals

class StateTest {

    // step 06
//    @Test
//    fun `a State should contains list of glass`() {
//        val state = State(listOf(Glass(4)))
//        assertEquals(listOf(Glass(4)), state.glasses)
//    }

    // step 06
//    @Test
//    fun `a State should have valid equals() method`() {
//        val actual = State(listOf(Glass(4, 2)))
//        val expected = State(listOf(Glass(4, 2)))
//        assertEquals(expected, actual)
//    }

    // step 07
//    @Test
//    fun `a State could be build with a variable argument`() {
//        val expected = State(listOf(Glass(4, 2), Glass(2, 1), Glass(1, 1)))
//
//        val actual = State(Glass(4, 2), Glass(2, 1), Glass(1, 1))
//
//        assertEquals(expected, actual)
//    }


    // step 17
//    @Test
//    fun `a State should process Empty`() {
//        val actual = State(listOf(Glass(4, 2), Glass(2, 1)))
//
//        val result = actual.process(Empty(1))
//
//        val expected = State(listOf(Glass(4, 2), Glass(2, 0)))
//        assertEquals(expected, result)
//    }

    // step 17
//    @Test
//    fun `a State should process Fill`() {
//        val actual = State(listOf(Glass(4, 2), Glass(2, 1)))
//
//        val result = actual.process(Fill(1))
//
//        val expected = State(listOf(Glass(4, 2), Glass(2, 2)))
//        assertEquals(expected, result)
//    }

    // step 17
//    @Test
//    fun `a State should process Pour`() {
//        val actual = State(listOf(Glass(4, 2), Glass(2, 1)))
//
//        val result = actual.process(Pour(from = 0, to = 1))
//
//        val expected = State(listOf(Glass(4, 1), Glass(2, 2)))
//        assertEquals(expected, result)
//    }

    // step 19
//    @Test
//    fun `a State should define availableMove Empty`() {
//        val actual = State(listOf(Glass(4, 2), Glass(2, 1), Glass(1, 0)))
//
//        val result = actual.availableMoves().filterIsInstance<Empty>().toSet()
//
//        val expected = setOf(Empty(0), Empty(1))
//        assertEquals(expected, result)
//    }

    // step 19
//    @Test
//    fun `a State should define availableMove Fill`() {
//        val actual = State(listOf(Glass(4, 2), Glass(2, 1), Glass(1, 1)))
//
//        val result = actual.availableMoves().filterIsInstance<Fill>().toSet()
//
//        val expected = setOf(Fill(0), Fill(1))
//        assertEquals(expected, result)
//    }

    // step 19
//    @Test
//    fun `a State should define availableMove Pour`() {
//        val actual = State(listOf(
//                Glass(4, 2),
//                Glass(2, 1),
//                Glass(1, 1),
//                Glass(1, 0)))
//
//        val result = actual.availableMoves().filterIsInstance<Pour>().toSet()
//
//        val expected = setOf(
//                Pour(0, 1), Pour(0, 3),
//                Pour(1, 0), Pour(1, 3),
//                Pour(2, 0), Pour(2, 1), Pour(2, 3))
//        assertEquals(expected, result)
//    }
}