package com.dicoding.myreadwritefile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.myreadwritefile.databinding.ActivityMainBinding

/**
 * Activity utama untuk aplikasi pengelola file sederhana
 * Implement View.OnClickListener untuk menangani klik tombol secara terpusat
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // lateinit karena binding baru diinisialisasi di onCreate(), bukan saat class dibuat
    // Ini lebih aman daripada nullable karena pasti akan diinisialisasi sebelum digunakan
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengaktifkan mode edge-to-edge agar UI bisa extend sampai edge layar
        enableEdgeToEdge()
        // View binding menggantikan findViewById, lebih type-safe dan efisien
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur padding untuk menghindari UI tertutup system bars (status bar, navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Set padding agar konten tidak tertutup system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Registrasi listener untuk setiap tombol, menggunakan this karena class ini implement OnClickListener
        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        // when expression untuk routing klik berdasarkan ID view yang diklik
        // Lebih clean daripada multiple if-else
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        // Mengosongkan kedua EditText untuk membuat file baru
        binding.editTitle.setText("")
        binding.editFile.setText("")
        // Feedback ke user bahwa field sudah dikosongkan
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        // fileList() adalah method Android yang mengembalikan array nama file di internal storage
        // Filter untuk menghilangkan file sistem "profileInstalled" agar tidak ditampilkan ke user
        // toTypedArray() karena AlertDialog.Builder.setItems() membutuhkan Array, bukan List
        val items = fileList().filter { fileName -> (fileName != "profileInstalled") }.toTypedArray()

        // AlertDialog untuk menampilkan list file yang tersimpan
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pilih file yang diinginkan")
        // setItems menampilkan list item yang bisa diklik
        // Lambda menerima position (item) dan meload file yang dipilih
        builder.setItems(items) { _, item -> loadData(items[item].toString()) }
        val alert = builder.create()
        // Tampilkan dialog ke user
        alert.show()
    }

    private fun loadData(title: String) {
        // Membaca data file dari internal storage menggunakan FileHelper
        val fileModel = FileHelper.readFromFile(this, title)
        // Populate UI dengan data yang dibaca dari file
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        // Feedback ke user bahwa file berhasil dimuat
        Toast.makeText(this, "Loading " + fileModel.filename + " data", Toast.LENGTH_SHORT).show()
    }

    private fun saveFile() {
        // Validasi input menggunakan when expression
        // Memastikan title dan konten tidak kosong sebelum menyimpan
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Title harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()

            binding.editFile.text.toString().isEmpty() -> Toast.makeText(
                this,
                "Konten harus diisi terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                // Ambil input dari EditText
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()

                // Buat FileModel baru dengan data dari input user
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text

                // Simpan ke internal storage menggunakan FileHelper
                FileHelper.writeToFile(fileModel, this)
                // Feedback ke user bahwa file berhasil disimpan
                Toast.makeText(this, "Saving " + fileModel.filename + " file", Toast.LENGTH_SHORT).show()
            }
        }
    }

}