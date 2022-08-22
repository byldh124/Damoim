package com.moondroid.project01_meetingapp.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.moondroid.project01_meetingapp.R
import org.jetbrains.annotations.NotNull
import java.security.MessageDigest


internal object DMUtils {
    fun getImgUrl(subUrl: String): String {
        return if (subUrl.startsWith("http")) {
            subUrl
        } else {
            "http://moondroid.dothome.co.kr/damoim/$subUrl"
        }
    }

    fun hashingPw(password: String, salt: String): String {
        val md: MessageDigest = MessageDigest.getInstance("SHA-256") // SHA-256 해시함수를 사용
        var output: ByteArray = password.toByteArray()

        // key-stretching
        for (i in 0..999) {
            val temp: String = byteToString(output) + salt // 패스워드와 Salt 를 합쳐 새로운 문자열 생성
            md.update(temp.toByteArray(Charsets.UTF_16)) // temp 의 문자열을 해싱하여 md 에 저장해둔다
            output = md.digest() // md 객체의 다이제스트를 얻어 password 를 갱신한다
        }

        return byteToString(output)
    }

    fun byteToString(byteArray: ByteArray): String {
        val sb = StringBuilder()
        for (bt in byteArray) {
            sb.append(String.format("%02x", bt))
        }

        return sb.toString()
    }

    fun getStringId(context: Context, name: String): Int {
        return try {
            context.resources.getIdentifier(name, "string", context.packageName)
        } catch (e: Exception) {
            0
        }
    }

    fun getDrawableId(context: Context, name: String): Int {
        return try {
            context.resources.getIdentifier(name, "drawable", context.packageName)
        } catch (e: Exception) {
            0
        }
    }


    fun getInterestNum(context: Context, interest: String): Int {
        for (i: Int in 1..19) {
            val query = context.getString(getStringId(context, String.format("interest_%02d", i)))

            if (query == interest) { return i }
        }
        return 0
    }

    fun getPathFromUri(context: Context, @NotNull uri: Uri): String? {
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
                )
                return getDataColumn(context, contentUri, null, null).toString()

            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                val contentUri: Uri = when (type) {
                    "image" -> {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    else -> {
                        return null
                    }
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(
                context,
                uri,
                null,
                null
            )
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path.toString()
        }
        return null
    }

    private fun getDataColumn(
        context: Context, uri: Uri, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }
}