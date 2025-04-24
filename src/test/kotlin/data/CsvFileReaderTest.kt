// src/test/kotlin/data/CsvFileReaderTest.kt
package data

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.io.IOException

class CsvFileReaderTest {

    @TempDir
    lateinit var tempDir: File

    @Test
    fun `readLinesFromFile drops header and returns only data rows`() {
        // given: a CSV with one header + two data rows
        val csv = File(tempDir, "test.csv").apply {
            writeText(
                """
                colA,colB,colC
                a1,b1,c1
                a2,b2,c2
                """.trimIndent()
            )
        }

        // when
        val rows = CsvFileReader(csv).readLinesFromFile()

        // then
        assertThat(rows).hasSize(2)
        assertThat(rows[0]).isEqualTo(arrayOf("a1", "b1", "c1"))
        assertThat(rows[1]).isEqualTo(arrayOf("a2", "b2", "c2"))
    }

    @Test
    fun `readLinesFromFile returns empty list when only header present`() {
        // given: CSV with header but no data
        val csv = File(tempDir, "only_header.csv").apply {
            writeText("h1,h2,h3")
        }

        // when
        val rows = CsvFileReader(csv).readLinesFromFile()

        // then
        assertThat(rows).isEmpty()
    }

    @Test
    fun `readLinesFromFile handles quoted values containing commas`() {
        // given: CSV where a field is quoted and contains commas
        val csv = File(tempDir, "quoted.csv").apply {
            writeText(
                """
                col
                "foo,bar,baz"
                simple
                """.trimIndent()
            )
        }

        // when
        val rows = CsvFileReader(csv).readLinesFromFile()

        // then
        assertThat(rows).hasSize(2)
        assertThat(rows[0]).isEqualTo(arrayOf("foo,bar,baz"))
        assertThat(rows[1]).isEqualTo(arrayOf("simple"))
    }

    @Test
    fun `readLinesFromFile throws when file does not exist`() {
        // given
        val missing = File(tempDir, "does_not_exist.csv")
        assertThat(missing.exists()).isFalse()

        // when & then
        val exception = assertThrows<IOException> {
            CsvFileReader(missing).readLinesFromFile()
        }

        assertThat(exception)
            .hasMessageThat()
            .contains("does_not_exist.csv")
    }
}
