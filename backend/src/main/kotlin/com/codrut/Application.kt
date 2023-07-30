package com.codrut

import com.codrut.plugins.configureRouting
import com.codrut.recipes.RecipeRepository
import com.codrut.users.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module,
        watchPaths = listOf("classes")
    ).start(wait = true)
}

val appModules = module {
    singleOf(::RecipeRepository)
    singleOf(::UserRepository)
}

fun Application.module() {
    configureRouting()
    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }
}
