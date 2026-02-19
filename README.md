# MyReadWriteFile - Aplikasi Android File Manager Sederhana

## 📝 Deskripsi

MyReadWriteFile adalah aplikasi Android sederhana yang mendemonstrasikan cara membaca dan menulis file di **Internal Storage** Android. Aplikasi ini memungkinkan pengguna untuk membuat, menyimpan, dan membuka file teks secara lokal di perangkat Android.

## ✨ Fitur Utama

- **New File**: Membersihkan form untuk membuat file baru
- **Save File**: Menyimpan file teks ke internal storage dengan nama custom
- **Open File**: Menampilkan daftar file yang tersimpan dan membuka file yang dipilih

## 🏗️ Arsitektur Aplikasi

### Struktur Kode

```
com.dicoding.myreadwritefile/
├── MainActivity.kt          # Activity utama dengan UI logic
├── FileHelper.kt           # Helper class untuk operasi file I/O
└── FileModel.kt           # Data model untuk representasi file
```

### Penjelasan Komponen

#### 1. **FileModel.kt**
Data class yang merepresentasikan file dengan dua properti:
- `filename`: Nama file (nullable)
- `data`: Konten/isi file (nullable)

**Konsep Penting:**
- Menggunakan `data class` untuk mendapatkan method bawaan seperti `equals()`, `hashCode()`, `toString()`
- Properti nullable (`String?`) untuk fleksibilitas handling data kosong

#### 2. **FileHelper.kt**
Object singleton yang menyediakan dua fungsi utama:

##### `writeToFile()`
- Menulis data ke internal storage menggunakan `openFileOutput()`
- Menggunakan `MODE_PRIVATE` agar file hanya bisa diakses oleh aplikasi ini
- `use{}` untuk auto-close stream dan mencegah memory leak

##### `readFromFile()`
- Membaca file dari internal storage menggunakan `openFileInput()`
- `bufferedReader()` untuk efisiensi pembacaan file
- `useLines()` memproses file baris per baris (hemat memory)
- `fold()` untuk menggabungkan semua baris menjadi satu String

**Konsep Penting:**
- `internal object` = Singleton pattern, tidak perlu buat instance
- File disimpan di `/data/data/com.dicoding.myreadwritefile/files/`
- File bersifat private dan otomatis terhapus saat uninstall

#### 3. **MainActivity.kt**
Activity utama yang mengatur UI dan interaksi user:

##### Fungsi Utama:
- `onCreate()`: Inisialisasi view binding dan setup listeners
- `onClick()`: Router untuk menangani klik tombol
- `newFile()`: Clear form untuk file baru
- `showList()`: Menampilkan dialog dengan list file tersimpan
- `loadData()`: Load file yang dipilih ke form
- `saveFile()`: Validasi dan simpan file ke storage

**Konsep Penting:**
- Menggunakan **View Binding** untuk akses view yang type-safe
- Implement `View.OnClickListener` untuk centralized click handling
- **Edge-to-Edge** mode dengan proper window insets handling
- Validasi input menggunakan `when` expression
- `fileList()` adalah method Android bawaan untuk list file di internal storage

## 🛠️ Teknologi yang Digunakan

- **Bahasa**: Kotlin
- **SDK Minimum**: API Level sesuai build.gradle
- **View Binding**: Untuk akses view tanpa findViewById
- **Material Components**: Untuk UI modern
- **Internal Storage API**: Context.openFileOutput() & Context.openFileInput()

## 📱 Cara Penggunaan

1. **Membuat File Baru:**
   - Klik tombol "New" untuk membersihkan form
   - Isi "Title" (nama file)
   - Isi "Content" (isi file)
   - Klik "Save"

2. **Menyimpan File:**
   - Pastikan Title dan Content tidak kosong
   - Klik "Save"
   - File akan tersimpan di internal storage

3. **Membuka File:**
   - Klik tombol "Open"
   - Pilih file dari dialog yang muncul
   - File akan dimuat ke form dan bisa diedit

## 🔐 Keamanan & Privacy

- File disimpan di **Internal Storage** yang bersifat **private**
- Hanya aplikasi ini yang bisa akses file tersebut
- File otomatis terhapus saat aplikasi di-uninstall
- Tidak memerlukan permission khusus (WRITE_EXTERNAL_STORAGE tidak diperlukan)

## 📚 Konsep Android yang Dipelajari

### 1. Internal Storage vs External Storage
- **Internal Storage**: Private, terhapus saat uninstall, tidak perlu permission
- **External Storage**: Shared, persistent, perlu permission

### 2. Context Methods
- `openFileOutput()`: Membuka/membuat file untuk ditulis
- `openFileInput()`: Membuka file untuk dibaca
- `fileList()`: Mendapatkan list file di internal storage

### 3. Kotlin Best Practices
- **Object singleton** untuk helper class
- **Data class** untuk model
- **Safe call operator** (`?.`) untuk null safety
- **Extension functions** (`use`, `useLines`, `fold`)
- **Lambda expressions** untuk callback
- **When expression** untuk conditional logic

### 4. Android UI Patterns
- View Binding untuk akses view
- AlertDialog untuk menampilkan pilihan
- Toast untuk feedback ke user
- Edge-to-edge dengan window insets

## 🐛 Troubleshooting

### File tidak tersimpan?
- Pastikan Title dan Content tidak kosong
- Check Logcat untuk error message

### File tidak muncul di list?
- Pastikan sudah save file terlebih dahulu
- File sistem "profileInstalled" sudah difilter otomatis

### Memory leak?
- Kode sudah menggunakan `use{}` untuk auto-close stream

## 📖 Referensi

- [Android Developer - Data and File Storage](https://developer.android.com/training/data-storage)
- [Kotlin - Scope Functions](https://kotlinlang.org/docs/scope-functions.html)
- [View Binding](https://developer.android.com/topic/libraries/view-binding)

## 👨‍💻 Pengembangan Lebih Lanjut

Beberapa ide untuk pengembangan:
- [ ] Tambahkan fitur delete file
- [ ] Tambahkan fitur rename file
- [ ] Tambahkan konfirmasi sebelum overwrite file
- [ ] Tambahkan timestamp pada file
- [ ] Implementasi encryption untuk file sensitif
- [ ] Export/import file ke external storage
- [ ] Tambahkan search functionality

## 📄 Lisensi

Aplikasi ini dibuat untuk tujuan pembelajaran.

---

**Dibuat dengan ❤️ untuk belajar Android Development**

