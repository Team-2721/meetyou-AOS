package com.team2127.domain.usecase.image

import com.team2127.domain.repository.ImageRepository
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(path: String? = null): Result<List<String>> =
        repository.getImages(path)
}
