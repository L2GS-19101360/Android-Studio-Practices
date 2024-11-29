package com.example.chatmates.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chatmates.R;
import com.example.chatmates.databinding.ActivityProfileBinding;
import com.example.chatmates.models.User;
import com.example.chatmates.utilities.Constants;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadProfileDetail();
        setListeners();
    }

    void setListeners() {
        binding.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void updateProfile() {

    }

    private void loadProfileDetail() {


    }
}