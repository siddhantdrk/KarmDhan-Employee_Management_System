package com.dbms.project.karmdhan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAdminLoginBinding;
import com.dbms.project.karmdhan.databinding.ActivityPasswordResetBinding;

public class PasswordResetActivity extends AppCompatActivity {
    private ActivityPasswordResetBinding binding;
    private AdminOperations adminOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPasswordResetBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        binding.forgotPassword.setOnClickListener(this::onClick);
        //setContentView(R.layout.activity_password_reset);
    }

    private void onClick(View view) {
        switch(view.getId()){
            case R.id.forgot_password:
                check();
                break;
            case R.id.renew_password:
                check1();
        }
    }

    private void check1() {
        String userId = binding.userIdEdt.getText().toString().trim();
        if(!userId.isEmpty()){
            if(!adminOperations.checkAdminIDExist(userId)){
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent =new Intent(PasswordResetActivity.this,RenewPasswordActivity.class);
                intent.putExtra("USERID",userId);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void check() {
        String userId = binding.userIdEdt.getText().toString().trim();
        if(!userId.isEmpty()){
            if(!adminOperations.checkAdminIDExist(userId)){
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent =new Intent(PasswordResetActivity.this,ForgotPasswordActivity.class);
                intent.putExtra("USERID",userId);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }
}