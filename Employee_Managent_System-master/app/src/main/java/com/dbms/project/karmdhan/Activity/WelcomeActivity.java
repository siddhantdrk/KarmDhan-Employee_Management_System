package com.dbms.project.karmdhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Login.AdminLoginActivity;
import com.dbms.project.karmdhan.Activity.Login.EmployeeLoginActivity;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.proceedEmployeeBtn.setOnClickListener(this::onClick);
        binding.proceedAdminBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.proceed__employee_btn:
                Intent intentEmpLogin = new Intent(this, EmployeeLoginActivity.class);
                startActivity(intentEmpLogin);
                break;
            case R.id.proceed_admin_btn:
                Intent intentAdminLogin = new Intent(this, AdminLoginActivity.class);
                startActivity(intentAdminLogin);
                break;
        }
    }
}