package com.dbms.project.karmdhan.Activity.ResetPassword.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityChangeOrForgotPasswordAdminBinding;

public class ChangeOrForgotPasswordAdminActivity extends AppCompatActivity {
    private ActivityChangeOrForgotPasswordAdminBinding binding;
    private AdminOperations adminOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeOrForgotPasswordAdminBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        binding.forgotPassword.setOnClickListener(this::onClick);
        binding.changePassword.setOnClickListener(this::onClick);
        //setContentView(R.layout.activity_password_reset);
    }

    private void onClick(View view) {
        switch(view.getId()){
            case R.id.forgot_password:
                checkUserIdForForgotPassword();
                break;
            case R.id.change_password:
                checkUserIdForChangePassword();
                break;
        }
    }

    private void checkUserIdForChangePassword() {
        String userId = binding.userIdEdt.getText().toString().trim();
        if(!userId.isEmpty()){
            if (!adminOperations.checkAdminIDExist(Integer.parseInt(userId))) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ChangeOrForgotPasswordAdminActivity.this, ChangePasswordAdminActivity.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserIdForForgotPassword() {
        String userId = binding.userIdEdt.getText().toString().trim();
        if(!userId.isEmpty()){
            if (!adminOperations.checkAdminIDExist(Integer.parseInt(userId))) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ChangeOrForgotPasswordAdminActivity.this, ForgotPasswordAdminActivity.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }
}