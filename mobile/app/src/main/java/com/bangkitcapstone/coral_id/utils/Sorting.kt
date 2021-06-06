package com.bangkitcapstone.coral_id.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object Sorting {
    const val VOTE_BEST = "Best"
    const val NAME = "Name"
    const val RANDOM = "Random"
    const val MOVIE_ENTITIES = "movies_entities"

    fun getSortedQuery(filter: String, table_name: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table_name ")
        when (filter) {
            VOTE_BEST -> simpleQuery.append("ORDER BY rating DESC")
            NAME -> simpleQuery.append("ORDER BY title")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}