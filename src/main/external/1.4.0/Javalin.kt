package javalin.performance

import io.javalin.Javalin;

fun main(args: Array<String>) {
    Benchmarks.run(args)
}

open class JavalinBenchmark : HttpBenchmarkBase() {
    var app :Javalin? = null
    override fun startServer(port: Int) {
       app =  Javalin.start(port)
        app!!.get(helloPath) { ctx -> ctx.result(helloResult) }
    }

    override fun stopServer() {
        app!!.stop()
    }
}
