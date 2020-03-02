package javalin.performance

import de.vandermeer.asciitable.AsciiTable 
import de.siegmar.fastcsv.reader.CsvReader
import java.io.File
import java.nio.charset.StandardCharsets

object Benchmarks {

    fun run(args: Array<String>) {
        val mode = System.getProperty("mode")
        when (mode) {
            "benchmark" -> {
                val version = System.getProperty("version")
                runBenchmark(args)
            }
            "compareReport" -> {
                val baseline = System.getProperty("baseline")
                val target = System.getProperty("target")
                compare(baseline, target)
            }
        }
    }

    // run benchmark with version in property
    fun runBenchmark(args: Array<String>) {
        val version = System.getProperty("version")
        benchmark(args) {
            iterations = 20
            iterationTime = 10_000
            resultName = version
            setup()
        }

        benchmark(args) {
            profile("gc")
            iterations = 20
            iterationTime = 10_000
            resultName = "$version-gc"
            setup()
        }
    }

    // compare baseline version with targer version
    fun compare(baseline: String, target: String) {
        val at = AsciiTable()
        at.addRule()
        val methodNameLabel = "Benchmark"
        val scoreLabel = "Score"
        val baselineCsv = File("$baseline.csv")
        val targetCsv = File("$target.csv")
        val csvReader = CsvReader()
        csvReader.setContainsHeader(true)
        val baselineRead = csvReader.read(baselineCsv, StandardCharsets.UTF_8)
        val targetRead = csvReader.read(targetCsv, StandardCharsets.UTF_8)
        at.addRow("methodNameLabel", "increment score");
        at.addRule()

        for (i in 0 until baselineRead.rows.size) {
            var baselineBenchmark = baselineRead.getRow(i)
            var targetBenchmark = targetRead.getRow(i)
            if (baselineBenchmark.getField(methodNameLabel)
                == targetBenchmark.getField(methodNameLabel)) {
                var baselineScore = baselineRead.getRow(i).getField(scoreLabel).toDouble()
                var targetScore = targetRead.getRow(i).getField(scoreLabel).toDouble()
                at.addRow(baselineRead.getRow(i).getField(methodNameLabel),"%.3f".format(((targetScore / baselineScore) - 1)*100) + "%");
                at.addRule()
            }
        }
        println(at.render())
    }

    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
