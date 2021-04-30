package com.dbms.project.karmdhan.Activity.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAdminRegisterBinding;

public class AdminRegisterActivity extends AppCompatActivity {
    private ActivityAdminRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }
}