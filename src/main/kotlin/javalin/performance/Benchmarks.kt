package javalin.performance

import io.javalin.Javalin;

fun main(args: Array<String>) {
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

open class JavalinBenchmark : HttpBenchmarkBase() {
    val app = Javalin.create();
    override fun startServer(port: Int) {
        app.start(port)
    }

    override fun stopServer() {
        app.stop()
    }
}
