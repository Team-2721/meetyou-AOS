package com.team2127.data.repository

import android.Manifest
import android.os.Build
import com.gun0912.tedpermission.TedPermissionResult
import com.gun0912.tedpermission.coroutine.TedPermission
import com.team2127.data.R
import com.team2127.data.source.ImageDataSource
import com.team2127.domain.exception.RequestDeniedException
import com.team2127.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: ImageDataSource
): ImageRepository {
    override suspend fun getImages(path: String?): Result<List<String>> {
        val result = getReadPermissionResult()
        return if (result.isGranted){
            Result.success(dataSource.getImage(path))
        } else {
            Result.failure(RequestDeniedException(result.deniedPermissions.first()))
        }
    }

    private suspend fun getReadPermissionResult(): TedPermissionResult {
        val permission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }
        return TedPermission.create()
            .setDeniedMessage(R.string.read_permission_denied)
            .setPermissions(permission)
            .check()
    }
}
