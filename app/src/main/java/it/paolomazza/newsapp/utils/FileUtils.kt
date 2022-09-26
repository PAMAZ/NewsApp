package it.paolomazza.newsapp.utils

import android.content.Context
import androidx.annotation.RawRes
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtils {


    @JvmStatic
    fun getMockResource(inputStream: InputStream,
                        file: String): String? {
        if (file.isEmpty()) {
            return null
        }
        val outputStream = ByteArrayOutputStream()

        val buffer = ByteArray(1024)
        var size = inputStream.read(buffer)
        try {
            while (size != -1) {
                outputStream.write(buffer, 0, size)
                size = inputStream.read(buffer)
            }
            outputStream.close()
            inputStream.close()
        } catch (e: IOException) {
        }

        return outputStream.toString()
    }

}