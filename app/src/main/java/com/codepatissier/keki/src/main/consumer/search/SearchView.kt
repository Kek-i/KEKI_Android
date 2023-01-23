package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.PopularSearchesResponse

interface SearchView {
    fun onGetPopularSearchesSuccess(response: PopularSearchesResponse)

    fun onGetPopularSearchesFailure(message: String)
}