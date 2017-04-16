package io.funatwork.core.cache

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.io.IOException

class FileManager {

    /**
     * Writes a file to Disk.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform this operation using another thread.

     * @param file The file to write to Disk.
     */
    fun writeToFile(file: File, fileContent: String) {
        if (!file.exists()) {
            try {
                val writer = FileWriter(file)
                writer.write(fileContent)
                writer.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * Reads a content from a file.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.

     * @param file The file to read from.
     * *
     * @return A string with the content of the file.
     */
    fun readFileContent(file: File): String = file.readText()

    /**
     * Returns a boolean indicating whether this file can be found on the underlying file system.

     * @param file The file to check existence.
     * *
     * @return true if this file exists, false otherwise.
     */
    fun exists(file: File): Boolean = file.exists()

    /**
     * Warning: Deletes the content of a directory.
     * This is an I/O operation and this method executes in the main thread, so it is recommended to
     * perform the operation using another thread.

     * @param directory The directory which its content will be deleted.
     */
    fun clearDirectory(directory: File): Boolean = directory.deleteRecursively()

    /**
     * Write a value to a user preferences file.

     * @param context [android.content.Context] to retrieve android user preferences.
     * *
     * @param preferenceFileName A file name reprensenting where data will be written to.
     * *
     * @param key A string for the key that will be used to retrieve the value in the future.
     * *
     * @param value A long representing the value to be inserted.
     */
    fun writeToPreferences(context: Context, preferenceFileName: String, key: String,
                           value: Long) {
        val sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    /**
     * Get a value from a user preferences file.

     * @param context [android.content.Context] to retrieve android user preferences.
     * *
     * @param preferenceFileName A file name representing where data will be get from.
     * *
     * @param key A key that will be used to retrieve the value from the preference file.
     * *
     * @return A long representing the value retrieved from the preferences file.
     */
    fun getFromPreferences(context: Context, preferenceFileName: String, key: String): Long {
        val sharedPreferences = context.getSharedPreferences(preferenceFileName,
                Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0)
    }
}