package com.dbms.project.karmdhan.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.MainActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityAdminLoginBinding;

public class AdminLoginActivity extends AppCompatActivity {
    private ActivityAdminLoginBinding binding;
    private AdminOperations adminOperations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        adminOperations.addAdmin(new Admin("12345678", "12345678"));
        binding.loginBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId()==R.id.login_btn) {
                login();
        }
    }

    private void login() {
        String userId = binding.userIdEdt.getText().toString().trim();
        String password = binding.passwordEdt.getText().toString().trim();

        if (!userId.isEmpty()) {
            if (!adminOperations.checkAdminIDExist(userId)) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            }
            if (!password.isEmpty()) {
                Admin admin = new Admin(userId, password);
                if (adminOperations.checkLoginCredentials(admin)) {
                    Toast.makeText(this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                    SharedPreferenceManager.getInstance(this).saveToken(userId);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Wrong credentials !! Please fill the correct details", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(AdminLoginActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AdminLoginActivity.this, "userName is empty", Toast.LENGTH_SHORT).show();
        }
    }

}