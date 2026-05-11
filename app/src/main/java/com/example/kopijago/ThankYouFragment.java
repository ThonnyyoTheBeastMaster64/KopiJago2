package com.example.kopijago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

// Fragment sederhana untuk tampilkan pesan Terima Kasih
public class ThankYouFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Hubungkan dengan layout fragment_thank_you.xml
        return inflater.inflate(R.layout.fragment_thank_you, container, false);
    }
}