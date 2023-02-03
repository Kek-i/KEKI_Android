package com.codepatissier.keki.src.main.consumer.home.model

data class HomeTagRes(
    val homePostRes: List<HomePostRe>,
    val tagIdx: Int,
    val tagName: String
)