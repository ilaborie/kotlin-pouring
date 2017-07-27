package pouring

import org.junit.Test
import kotlin.test.assertEquals

class MoveTest {

    // step 09
    @Test
    fun `a Move could be Empty(index)`() {
        val move = Empty(0)
        assertEquals(0, move.index)
    }

    // step 10
    @Test
    fun `a Move could be Fill(index)`() {
        val move = Fill(0)
        assertEquals(0, move.index)
    }

    // step 11
    @Test
    fun `a Move could be Pour(from, to)`() {
        val move = Pour(0, 1)
        assertEquals(0, move.from, "from")
        assertEquals(1, move.to, "to")
    }


}