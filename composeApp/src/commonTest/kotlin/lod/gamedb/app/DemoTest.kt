package lod.gamedb.app

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals

/**
 * A demonstration test class showing different testing approaches.
 */
class DemoTest {
    
    @Test
    fun testBasicAssertion() {
        // Simple boolean assertion
        val condition = true
        assertTrue(condition, "Boolean assertion example")
    }
    
    @Test
    fun testValueComparison() {
        // Value comparison
        val expected = "GameDB"
        val actual = "Game" + "DB"
        assertEquals(expected, actual, "String concatenation should work correctly")
    }
    
    @Test
    fun testCollectionOperations() {
        // Testing collection operations
        val gamesList = listOf("RPG", "Strategy", "Simulation", "Action")
        
        // Test collection size
        assertEquals(4, gamesList.size, "List should contain 4 items")
        
        // Test collection contains specific item
        assertTrue(gamesList.contains("RPG"), "List should contain RPG genre")
        
        // Test collection transformation
        val uppercaseList = gamesList.map { it.uppercase() }
        assertEquals("RPG", uppercaseList[0], "First item should be RPG")
    }
}