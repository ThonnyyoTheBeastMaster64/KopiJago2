package com.example.kopijago;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Ambil data yang dikirim dari MainActivity
        Intent intent = getIntent();
        String nama     = intent.getStringExtra("NAMA");
        String email    = intent.getStringExtra("EMAIL");
        String menu     = intent.getStringExtra("MENU");
        String tipe     = intent.getStringExtra("TIPE");
        String tambahan = intent.getStringExtra("TAMBAHAN");

        // Hitung total harga berdasarkan menu
        int harga = 0;
        if (menu != null) {
            if (menu.contains("Espresso"))   harga = 25000;
            if (menu.contains("Latte"))      harga = 30000;
            if (menu.contains("Cappuccino")) harga = 32000;
        }
        // Tambahan harga
        if (tambahan != null) {
            if (tambahan.contains("Gula"))       harga += 3000;
            if (tambahan.contains("Extra Shot")) harga += 8000;
        }

        // Format teks struk
        String struk =
                "👤 Nama  : " + nama + "\n" +
                        "📧 Email : " + email + "\n\n" +
                        "☕ Menu  : " + menu + "\n" +
                        "🌡️ Tipe  : " + tipe + "\n\n" +
                        "➕ Tambahan:\n" + tambahan + "\n" +
                        "─────────────────────\n" +
                        "💰 Total  : Rp " + String.format("%,d", harga).replace(",", ".");

        // Tampilkan struk ke TextView
        TextView tvHasil = findViewById(R.id.tvHasil);
        tvHasil.setText(struk);

        // ── BONUS: Tampilkan Fragment ThankYou ──
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, new ThankYouFragment());
        ft.commit();

        // Tombol kembali ke MainActivity
        Button btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(v -> finish()); // finish() = kembali ke halaman sebelumnya
    }
}