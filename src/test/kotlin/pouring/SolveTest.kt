package pouring

import org.junit.Test
import kotlin.test.assertEquals

class SolveTest {


    // step 27
    @Test
    fun `should solve initial == final`() {
        val initial = State(listOf(Glass(4)))

        val moves = solve(initial, initial)

        assertEquals(emptyList(), moves)
    }

    // step 27
    @Test
    fun `should solve 0|8, 0|5, 0|3 to  4|8, 0|5, 0|3 in 8 moves`() {
        val initialState = State(listOf(Glass(8), Glass(5), Glass(3)))
        val expectedState = State(listOf(Glass(capacity = 8, current = 4), Glass(5), Glass(3)))

        val moves = solve(from = initialState, to = expectedState)

        assertEquals(8, moves.size)
    }
}