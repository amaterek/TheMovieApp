package amaterek.movie.base.navigation

import org.junit.Test
import kotlin.test.assertEquals

class DestinationTest {

    @Test
    fun `WHEN instance created without argument THEN exposes baseRoute as route`() {
        val baseRoute = "baseRoute"
        val subject = Destination(baseRoute = baseRoute)

        assertEquals(baseRoute, subject.route)
    }

    @Test
    fun `WHEN instance created with argument THEN exposes parametrised route for NavController`() {
        val baseRoute = "baseRoute"
        val argumentName = "parameter"
        val subject = Destination(baseRoute = baseRoute, argumentName = argumentName)

        assertEquals("${baseRoute}/{$argumentName}", subject.route)
    }

    @Test
    fun `WHEN routeFor is called with argument THEN returns parametrised route`() {
        val baseRoute = "baseRoute"
        val argument = "argument"
        val subject = Destination(baseRoute = baseRoute)

        assertEquals("${baseRoute}/$argument", subject.routeFor(argument))
    }
}

