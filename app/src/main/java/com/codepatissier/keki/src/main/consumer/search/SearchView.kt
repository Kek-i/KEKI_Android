package com.codepatissier.keki.src.main.consumer.search

import com.codepatissier.keki.src.main.consumer.search.model.MainSearchesResponse

interface SearchView {
    fun onGetMainSearchesSuccess(response: MainSearchesResponse)
    fun onGetMainSearchesFailure(message: String)
}