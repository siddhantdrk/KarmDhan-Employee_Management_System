package com.dbms.project.karmdhan.Activity.Login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAdminLoginBinding;
import com.dbms.project.karmdhan.databinding.ActivityEmployeeLoginBinding;
import com.google.android.material.textfield.TextInputEditText;

public class AdminLoginActivity extends AppCompatActivity {
    private ActivityAdminLoginBinding binding ;
    TextInputEditText userNameEdt,passwordEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.loginBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId()==R.id.login_btn) {
                login();
        }
    }

    private void login() {
        String name = binding.userIdEdt.getText().toString().trim();
        String password = binding.passwordEdt.getText().toString().trim();

        if(!name.isEmpty()){
            if(!password.isEmpty()){

            }else{
                Toast.makeText(AdminLoginActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(AdminLoginActivity.this, "userName is empty", Toast.LENGTH_SHORT).show();
        }
    }

}