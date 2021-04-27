package com.dbms.project.karmdhan.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Dashboard.AdminDashboardActivity;
import com.dbms.project.karmdhan.Activity.Dashboard.EmployeeDashboardActivity;
import com.dbms.project.karmdhan.Activity.Login.AdminLoginActivity;
import com.dbms.project.karmdhan.Activity.Login.EmployeeLoginActivity;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this));
        checkIfUserExists();
        binding.proceedEmployeeBtn.setOnClickListener(this::onClick);
        binding.proceedAdminBtn.setOnClickListener(this::onClick);
    }

    private void checkIfUserExists() {
        if (SharedPreferenceManager.getInstance(this).getToken() != null) {
            if (SharedPreferenceManager.getInstance(this).isAdmin())
                startActivity(new Intent(WelcomeActivity.this, AdminDashboardActivity.class));
            else
                startActivity(new Intent(WelcomeActivity.this, EmployeeDashboardActivity.class));
            finish();
        } else {
            setContentView(binding.getRoot());
        }
    }

    @SuppressLint("NonConstantResourceId")
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceed__employee_btn:
                SharedPreferenceManager.getInstance(this).setAdmin(false);
                Intent intentEmpLogin = new Intent(this, EmployeeLoginActivity.class);
                startActivity(intentEmpLogin);
                break;
            case R.id.proceed_admin_btn:
                SharedPreferenceManager.getInstance(this).setAdmin(true);
                Intent intentAdminLogin = new Intent(this, AdminLoginActivity.class);
                startActivity(intentAdminLogin);
                break;
        }
    }
}