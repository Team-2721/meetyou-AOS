package com.team2127.domain.repository

interface ImageRepository {
    suspend fun getImages(path: String?): Result<List<String>>
}
