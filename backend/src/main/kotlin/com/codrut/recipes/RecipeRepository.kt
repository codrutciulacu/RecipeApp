package com.codrut.recipes

import com.codrut.users.User
import com.codrut.users.UserRepository
import java.time.Instant
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

data class Recipe(
    val id: Int,
    val title: String,
    val pictureUrl: String,
    val audit: Audit
)

data class Audit(
    val createdBy: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

object Recipes : Table() {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 128)
    val pictureUrl = varchar("picture_url", 1024)
    val authorId = integer("author_id").entityId()
    val createdBy = datetime("created_by").entityId()

    override val primaryKey = PrimaryKey(id)
}


class RecipeRepository {

}