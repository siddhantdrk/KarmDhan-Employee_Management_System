package com.dbms.project.karmdhan.Activity.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Dashboard.AdminDashboardActivity;
import com.dbms.project.karmdhan.Activity.ResetPassword.ChangeOrForgotPasswordActivity;
import com.dbms.project.karmdhan.Authentication.PasswordAuthentication;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityAdminLoginBinding;

public class AdminLoginActivity extends AppCompatActivity {
    private ActivityAdminLoginBinding binding;
    private AdminOperations adminOperations;
    private PasswordAuthentication passwordAuthentication;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        passwordAuthentication = new PasswordAuthentication();
        String password = "12345678";
        if (adminOperations.addAdmin(new Admin(12345678, passwordAuthentication.hash(password.toCharArray())))) {
            Toast.makeText(this, "Admin added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User ID already exists", Toast.LENGTH_SHORT).show();
        }
        adminOperations.addAdmin(new Admin(98765432, passwordAuthentication.hash(password.toCharArray())));
        binding.loginBtn.setOnClickListener(this::onClick);
        binding.forgotPasswordTv.setOnClickListener(this::onClick);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.forgot_password_tv:
                Intent intent = new Intent(AdminLoginActivity.this, ChangeOrForgotPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void login() {
        String userId = binding.userIdEdt.getText().toString().trim();
        String password = binding.passwordEdt.getText().toString().trim();

        if (!userId.isEmpty()) {
            if (!adminOperations.checkAdminIDExist(Integer.parseInt(userId))) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            } else if (!password.isEmpty()) {
                Admin admin = new Admin(Integer.parseInt(userId), password);
                if (adminOperations.checkLoginCredentials(admin)) {
                    Toast.makeText(this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferenceManager.getInstance(this).saveToken(userId);
                    Intent intent = new Intent(this, AdminDashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Wrong credentials !! Please fill the correct details", Toast.LENGTH_SHORT).show();
                }
            }else{
                binding.passwordTil.setError("password field should not kept empty");
                Toast.makeText(AdminLoginActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            binding.userIdTil.setError("UserId field should not kept empty");
            Toast.makeText(AdminLoginActivity.this, "UserId is empty", Toast.LENGTH_SHORT).show();
        }
    }

}