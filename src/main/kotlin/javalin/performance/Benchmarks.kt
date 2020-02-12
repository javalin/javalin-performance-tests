package javalin.performance

import io.javalin.Javalin;
import javalin.performance.*;

object Benchmarks{

    fun run(args: Array<String>){
        benchmark(args) {
            iterations = 5
            iterationTime = 10_000
            setup()
        }

        benchmark(args) {
            profile("gc")
            iterations = 20
            iterationTime = 500
            setup()
        }
    }

    private fun BenchmarkSettings.setup() {
        run<JavalinBenchmark>()
    }
}
