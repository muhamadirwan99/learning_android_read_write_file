package com.dicoding.myreadwritefile

import android.content.Context

/**
 * Helper class untuk operasi baca/tulis file di internal storage
 * Menggunakan object karena tidak perlu membuat instance, langsung diakses seperti static method
 * Internal berarti hanya bisa diakses dalam module yang sama, meningkatkan enkapsulasi
 */
internal object FileHelper {
    fun writeToFile(fileModel: FileModel, context: Context) {
        // openFileOutput membuka/membuat file di internal storage app (private, tidak bisa diakses app lain)
        // MODE_PRIVATE memastikan file hanya bisa diakses oleh app ini
        // use{} otomatis menutup stream setelah selesai, mencegah memory leak
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            // Operator ?. (safe call) mencegah crash jika data null
            // toByteArray() mengkonversi String ke bytes karena file I/O bekerja dengan byte
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.filename = filename
        // openFileInput membuka file dari internal storage untuk dibaca
        // bufferedReader() membaca file secara efisien dengan buffering
        // useLines{} memproses file baris per baris, hemat memory untuk file besar
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            // fold() adalah fungsi akumulasi, menggabungkan semua baris menjadi satu String
            // some = akumulator (hasil gabungan sejauh ini), text = baris saat ini
            // "$some$text\n" menggabungkan baris dengan menambahkan newline di akhir
            lines.fold("") { some, text ->
                "$some$text\n"
            }
        }
        return fileModel
    }
}