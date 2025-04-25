package data

import kotlinx.datetime.LocalDate
import org.berlin.model.Meal
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MealsCsvParserTest {

    private val parser = MealsCsvParser()

    private fun sampleLine(
        name: String = " N ",
        id: String = "1",
        minutes: String = "2",
        contributor: String = "3",
        date: String = "2025-01-01",
        tags: String = "['t']",
        nutrition: String = "[1.0,2.0,3.0,4.0,5.0,6.0,7.0]",
        nSteps: String = "1",
        steps: String = "['s']",
        description: String = " D ",
        ingredients: String = "['i']",
        nIngredients: String = "1"
    ): Array<String> = arrayOf(
        name, id, minutes, contributor, date,
        tags, nutrition, nSteps, steps, description,
        ingredients, nIngredients
    )

    // === NAME ===

    @Test
    fun `given name with extra spaces when parsed then spaces are collapsed`() {
        // given
        val line = sampleLine(name = "  A   B  ")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals("A B", result.name)
    }

    // === ID ===

    @Test
    fun `given valid id when parsed then id is integer`() {
        // given
        val line = sampleLine(id = "42")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(42, result.id)
    }

    @Test
    fun `given non-numeric id when parsed then id is -1`() {
        // given
        val line = sampleLine(id = "foo")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(-1, result.id)
    }

    // === MINUTES ===

    @Test
    fun `given valid minutes when parsed then minutes is integer`() {
        // given
        val line = sampleLine(minutes = "15")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(15, result.minutes)
    }

    @Test
    fun `given non-numeric minutes when parsed then minutes is -1`() {
        // given
        val line = sampleLine(minutes = "bar")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(-1, result.minutes)
    }

    // === CONTRIBUTOR ID ===

    @Test
    fun `given valid contributorId when parsed then contributorId is integer`() {
        // given
        val line = sampleLine(contributor = "7")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(7, result.contributorId)
    }

    @Test
    fun `given non-numeric contributorId when parsed then contributorId is -1`() {
        // given
        val line = sampleLine(contributor = "baz")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(-1, result.contributorId)
    }

    // === SUBMISSION DATE ===

    @Test
    fun `given valid date when parsed then submissionDate correct`() {
        // given
        val line = sampleLine(date = "2025-12-31")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(LocalDate.parse("2025-12-31"), result.submissionDate)
    }

    @Test
    fun `given date with whitespace when parsed then whitespace trimmed`() {
        // given
        val line = sampleLine(date = " 2025-12-01 ")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(LocalDate.parse("2025-12-01"), result.submissionDate)
    }

    // === TAGS ===

    @Test
    fun `given empty tags when parsed then tags list empty`() {
        // given
        val line = sampleLine(tags = "[]")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertTrue(result.tags.isEmpty())
    }

    @Test
    fun `given single tag when parsed then tags list has element`() {
        // given
        val line = sampleLine(tags = "['solo']")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(listOf("solo"), result.tags)
    }

    @Test
    fun `given tags with blanks when parsed then blanks are dropped`() {
        // given
        val raw = "['keep','',\"   \",'also']"
        val line = sampleLine(tags = raw)
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(listOf("keep", "also"), result.tags)
    }

    // === N_STEPS ===

    @Test
    fun `given valid nSteps when parsed then numberOfSteps correct`() {
        // given
        val line = sampleLine(nSteps = "3")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(3, result.numberOfSteps)
    }

    @Test
    fun `given non-numeric nSteps when parsed then numberOfSteps is 0`() {
        // given
        val line = sampleLine(nSteps = "bad")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(0, result.numberOfSteps)
    }

    // === STEPS ===

    @Test
    fun `given empty steps when parsed then steps list empty`() {
        // given
        val line = sampleLine(steps = "[]")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertTrue(result.steps.isEmpty())
    }

    @Test
    fun `given single step when parsed then steps list has element`() {
        // given
        val line = sampleLine(steps = "['walk']")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(listOf("walk"), result.steps)
    }


    // === DESCRIPTION ===

    @Test
    fun `given blank description when parsed then description null`() {
        // given
        val line = sampleLine(description = "    ")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertNull(result.description)
    }

    @Test
    fun `given whitespace-only description when parsed then description null`() {
        // given
        val line = sampleLine(description = "\t  ")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertNull(result.description)
    }

    @Test
    fun `given normal description when parsed then trimmed`() {
        // given
        val line = sampleLine(description = " Hello   World ")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals("Hello World", result.description)
    }

    @Test
    fun `given null at DESCRIPTION column when parsed then description null`() {
        // given
        @Suppress("UNCHECKED_CAST")
        val row = arrayOfNulls<String>(12) as Array<String>
        row[ColumnIndex.NAME] = "n"
        row[ColumnIndex.ID] = "1"
        row[ColumnIndex.MINUTES] = "1"
        row[ColumnIndex.CONTRIBUTOR_ID] = "1"
        row[ColumnIndex.SUBMISSION_DATE] = "2025-01-01"
        row[ColumnIndex.TAGS] = "[]"
        row[ColumnIndex.NUTRITION] = "[0,0,0,0,0,0,0]"
        row[ColumnIndex.N_STEPS] = "0"
        row[ColumnIndex.STEPS] = "[]"
        row[ColumnIndex.DESCRIPTION] = ""
        row[ColumnIndex.INGREDIENTS] = "[]"
        row[ColumnIndex.N_INGREDIENTS] = "0"

        // when
        val result: Meal = parser.parseColumnsToMeal(row)
        // then
        assertNull(result.description)
    }

    // === INGREDIENTS ===

    @Test
    fun `given empty ingredients when parsed then list empty`() {
        // given
        val line = sampleLine(ingredients = "[]")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertTrue(result.ingredients.isEmpty())
    }

    @Test
    fun `given single ingredient when parsed then list has element`() {
        // given
        val line = sampleLine(ingredients = "['item']")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(listOf("item"), result.ingredients)
    }

    // === N_INGREDIENTS ===

    @Test
    fun `given valid nIngredients when parsed then numberOfIngredients correct`() {
        // given
        val line = sampleLine(nIngredients = "4")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(4, result.numberOfIngredients)
    }

    @Test
    fun `given non-numeric nIngredients when parsed then numberOfIngredients is 0`() {
        // given
        val line = sampleLine(nIngredients = "nope")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(0, result.numberOfIngredients)
    }

    // === NUTRITION ===

    @Test
    fun `given nutrition containing valid numbers when parsed then calories correct`() {
        // given
        val line = sampleLine(nutrition = "[9.9,0,0,0,0,0,0]")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(9.9, result.nutrition.calories)
    }

    @Test
    fun `given nutrition with non-numeric when parsed then calories zero`() {
        // given
        val line = sampleLine(nutrition = "[a,0,0,0,0,0,0]")
        // when
        val result: Meal = parser.parseColumnsToMeal(line)
        // then
        assertEquals(0.0, result.nutrition.calories)
    }

    // === MALFORMED ROW ===

    @Test
    fun `given too few columns when parsing then exception thrown`() {
        // given
        val bad = arrayOf("only","five","cols","here","0")
        // when / then
        assertThrows<IllegalArgumentException> {
            parser.parseColumnsToMeal(bad)
        }
    }
}
