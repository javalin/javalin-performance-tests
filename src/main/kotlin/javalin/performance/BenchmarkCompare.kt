package javalin.performance

import de.siegmar.fastcsv.reader.CsvReader
import de.vandermeer.asciitable.AsciiTable
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment
import java.io.File
import java.nio.charset.StandardCharsets
import kotlin.math.round

fun main(args: Array<String>) {
    BenchmarkCompare.run(args)
}

object BenchmarkCompare {

    fun run(args: Array<String>) {
        val baseline = System.getProperty("baseline")
        val target = System.getProperty("javalinVersion")
        compare(baseline, target)
    }

    // compare baseline version with targer version
    fun compare(baseline: String, target: String) {
        val at = AsciiTable()
        val methodNameLabel = "Benchmark"
        val scoreLabel = "Score"
        val unitLabel = "Unit"
        val baselineCsv = File("$baseline.csv")
        val targetCsv = File("$target.csv")
        val csvReader = CsvReader()
        csvReader.setContainsHeader(true)

        val baselineRead = csvReader.read(baselineCsv, StandardCharsets.UTF_8)
        val targetRead = csvReader.read(targetCsv, StandardCharsets.UTF_8)

        at.addRule()
        at.addRow(methodNameLabel, "$baseline JMH Score", "$target JMH Score", unitLabel, "$target Percentage Increase")
        at.setTextAlignment(TextAlignment.CENTER)
        at.addRule()

        for (i in 0 until baselineRead.rows.size) {
            var baselineBenchmark = baselineRead.getRow(i)
            var targetBenchmark = targetRead.getRow(i)
            if (baselineBenchmark.getField(methodNameLabel)
                == targetBenchmark.getField(methodNameLabel)) {
                var baselineScore = round(baselineRead.getRow(i).getField(scoreLabel).toDouble()*100) / 100
                var targetScore = round(targetRead.getRow(i).getField(scoreLabel).toDouble()*100) / 100
                at.addRow(
                    baselineRead.getRow(i).getField(methodNameLabel), baselineScore, targetScore, baselineRead.getRow(i).getField(unitLabel), "%.3f".format(((targetScore / baselineScore) - 1)*100) + "%")
                at.setTextAlignment(TextAlignment.CENTER)
                at.addRule()
            }
        }
        println(at.render())
    }
}
