package com.example.kopijago;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Deklarasi semua komponen UI
    EditText etNama, etEmail;
    Spinner spinnerMenu;
    RadioGroup rgTipe;
    CheckBox cbSugar, cbExtraShot;
    Button btnPesan, btnLokasi;

    // Nama key untuk SharedPreferences
    static final String PREF_NAME = "KopiJagoPref";
    static final String KEY_NAMA  = "nama_terakhir";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hubungkan variabel dengan komponen di XML
        etNama       = findViewById(R.id.etNama);
        etEmail      = findViewById(R.id.etEmail);
        spinnerMenu  = findViewById(R.id.spinnerMenu);
        rgTipe       = findViewById(R.id.rgTipe);
        cbSugar      = findViewById(R.id.cbSugar);
        cbExtraShot  = findViewById(R.id.cbExtraShot);
        btnPesan     = findViewById(R.id.btnPesan);
        btnLokasi    = findViewById(R.id.btnLokasi);

        // ── BONUS: Ambil nama terakhir dari SharedPreferences ──
        SharedPreferences pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String namaTerakhir = pref.getString(KEY_NAMA, "");
        if (!namaTerakhir.isEmpty()) {
            etNama.setText(namaTerakhir); // isi otomatis nama terakhir
        }

        // ── TOMBOL PESAN (Explicit Intent) ──
        btnPesan.setOnClickListener(v -> {

            // Ambil nilai dari form
            String nama  = etNama.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String menu  = spinnerMenu.getSelectedItem().toString();

            // Cek tipe minuman yang dipilih
            int selectedId = rgTipe.getCheckedRadioButtonId();
            RadioButton rbTerpilih = findViewById(selectedId);
            String tipe = rbTerpilih.getText().toString();

            // Cek checkbox tambahan
            String tambahan = "";
            if (cbSugar.isChecked())     tambahan += "✓ Tambah Gula\n";
            if (cbExtraShot.isChecked()) tambahan += "✓ Extra Shot\n";
            if (tambahan.isEmpty())      tambahan  = "Tidak ada";

            // ── VALIDASI FORM ──
            if (nama.isEmpty()) {
                etNama.setError("Nama tidak boleh kosong!");
                etNama.requestFocus();
                return; // stop, jangan lanjut
            }
            if (email.isEmpty() || !email.contains("@")) {
                etEmail.setError("Email harus mengandung karakter '@'!");
                etEmail.requestFocus();
                return; // stop, jangan lanjut
            }

            // ── BONUS: Simpan nama ke SharedPreferences ──
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(KEY_NAMA, nama);
            editor.apply();

            // ── Kirim data ke ResultActivity via Explicit Intent ──
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("NAMA",      nama);
            intent.putExtra("EMAIL",     email);
            intent.putExtra("MENU",      menu);
            intent.putExtra("TIPE",      tipe);
            intent.putExtra("TAMBAHAN",  tambahan);
            startActivity(intent);
        });

        // ── TOMBOL LOKASI (Implicit Intent) ──
        btnLokasi.setOnClickListener(v -> {
            // Buka Google Maps ke lokasi Kopi Jago (koordinat contoh Jakarta)
            Uri lokasi = Uri.parse("geo:-6.2088,106.8456?q=Kopi+Jago+Cafe");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, lokasi);

            // Cek apakah ada aplikasi maps yang bisa handle
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // Jika tidak ada Maps, buka browser
                Uri browser = Uri.parse("https://maps.google.com/?q=Kopi+Jago+Cafe");
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, browser);
                startActivity(browserIntent);
            }
        });
    }
}