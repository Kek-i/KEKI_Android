package com.codepatissier.keki.src.main.consumer.search.model

data class Result(
    val recentSearches: List<RecentSearch>,
    val popularSearches: List<PopularSearch>,
    val recentPostSearches: List<RecentPostSearch>
)