package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.databinding.ActivityAddEmployeesToProjectBinding;

public class AddEmployeesToProjectActivity extends AppCompatActivity {
    private ActivityAddEmployeesToProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeesToProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }
}