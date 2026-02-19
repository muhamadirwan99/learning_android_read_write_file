package com.dicoding.myreadwritefile

/**
 * Model data untuk merepresentasikan file yang akan disimpan/dibaca
 * Menggunakan data class untuk otomatis mendapatkan equals(), hashCode(), toString(), dan copy()
 */
data class FileModel(
    // Menyimpan nama file, nullable karena bisa jadi belum diisi saat pertama kali dibuat
    var filename: String? = null,
    // Menyimpan isi/konten dari file, nullable untuk menangani kondisi file kosong atau belum ada data
    var data: String? = null
)
