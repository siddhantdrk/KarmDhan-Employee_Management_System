package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

public class UpdateProjectDetailsActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        hideUnNecessaryAndUpdateUI();
    }

    private void hideUnNecessaryAndUpdateUI() {
        binding.projectNumberTil.setVisibility(View.GONE);
        binding.projectNumberEdt.setVisibility(View.GONE);
        binding.fillUpDetailsTv.setText("Update the project Details Below");
        binding.fillUpDetailsProjectLeaderTv.setText("Update the details below for Project Leader");
        binding.addProjectBtn.setText("Update");
    }
}