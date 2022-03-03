package com.nramos.bemobilemockito.data.model

import com.nramos.bemobilemockito.domain.model.Character

fun ResponseDTO.toDomain() = this.results?.map {
    it.toDomain()
}.orEmpty()

fun CharacterDTO.toDomain() = Character(
    name = this.name,
    gender = this.gender,
    birthYear = this.birth_year
)