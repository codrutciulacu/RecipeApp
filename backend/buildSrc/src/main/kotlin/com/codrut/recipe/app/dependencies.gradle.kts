val ktor_version: String by project
val kotlin_version: String by project
val koin_version: String by project
val kotlinx_version: String by project
val exposed_version: String by project

val ktor by extra {
    fun (module: String): String = "io.ktor:ktor-server-$module:$ktor_version"
}

val koin by extra {
    fun (module: String): String = "io.insert-koin:koin-$module:$koin_version"
}

val kotlin by extra {
    fun (module: String): String = "org.jetbrains.kotlin:kotlin-$module:$kotlin_version"
}

val exposed by extra {
    fun (module: String): String = "org.jetbrains.exposed:exposed-$module:$exposed_version"
}

val kotlinx by extra {
    fun (module: String): String = "org.jetbrains.kotlinx:kotlinx-$module:$kotlinx_version"
}