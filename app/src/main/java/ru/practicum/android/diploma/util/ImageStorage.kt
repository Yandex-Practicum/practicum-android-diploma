package ru.practicum.android.diploma.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class ImageStorage {

    /**
     * Сохраняет картинку во внешнее хранилище
     * @return путь для дальнейшего извлечения сохраненной картирнки
     */
    private fun saveImageToPrivateStorage(context: Context, uri: Uri): String {
        val filePath = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "icons"
        )
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, "${uri.lastPathSegment}.jpg")
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, outputStream)

        return file.absolutePath
    }

    /**
     * @return Uri ранее сохраненной картинки по пути ее хранения
     * @see saveImageToPrivateStorage
     */
    private fun getStoredImageUri(imagePath: String, context: Context): Uri {
        val file = File(imagePath)

        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }

    companion object {
        private const val IMAGE_QUALITY = 30
    }
}
