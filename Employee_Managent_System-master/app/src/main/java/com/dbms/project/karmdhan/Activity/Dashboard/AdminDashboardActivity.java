package com.dbms.project.karmdhan.Activity.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.WelcomeActivity;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityAdminDashboardBinding;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.logoutBtn.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                Intent intent = new Intent(this, WelcomeActivity.class);
                SharedPreferenceManager.getInstance(this).clear();
                Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
        }
    }
}