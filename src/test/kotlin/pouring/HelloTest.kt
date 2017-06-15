package pouring

import org.junit.Test
import kotlin.test.assertEquals

class HelloTest {

    @Test
    fun `a plopper should plop`() {
        fun plopper() = "plop"
        assertEquals("plop", plopper())
    }

}