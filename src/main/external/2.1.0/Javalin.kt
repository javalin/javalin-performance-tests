package javalin.performance

import io.javalin.Javalin;

fun main(args: Array<String>) {
    Benchmarks.run(args)
}

open class JavalinBenchmark : HttpBenchmarkBase() {
    val app = Javalin.create();
    override fun startServer(port: Int) {
        app.start(port)
        app.attachEndpoints()
    }

    override fun stopServer() {
        app.stop()
    }
}
