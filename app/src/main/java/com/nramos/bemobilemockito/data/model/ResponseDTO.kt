package com.nramos.bemobilemockito.data.model

data class ResponseDTO(
    val count: Int?,
    val next: String?,
    val previous: Any?,
    val results: List<CharacterDTO>?
)