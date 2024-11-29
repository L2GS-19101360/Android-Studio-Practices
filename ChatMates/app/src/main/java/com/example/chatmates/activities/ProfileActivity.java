package com.example.chatmates.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

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
        String userName = getIntent().getStringExtra(Constants.KEY_NAME);
        String userEmail = getIntent().getStringExtra(Constants.KEY_EMAIL);
        String userPassword = getIntent().getStringExtra(Constants.KEY_PASSWORD);

        byte[] bytes = Base64.decode(getIntent().getStringExtra(Constants.KEY_IMAGE), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        binding.imageProfile.setImageBitmap(bitmap);

        binding.inputName.setText(userName);
        binding.inputEmail.setText(userEmail);
        binding.inputPassword.setText(userPassword);
        binding.inputConfirmPassword.setText(userPassword);
    }
}