package com.dbms.project.karmdhan.Activity.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dbms.project.karmdhan.Activity.Dashboard.AdminDashboardActivity;
import com.dbms.project.karmdhan.Activity.Login.AdminLoginActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAdminRegisterBinding;

public class AdminRegisterActivity extends AppCompatActivity {
    private ActivityAdminRegisterBinding binding;
    private AdminOperations adminOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        binding.registerBtn.setOnClickListener(this::onClick);
        binding.loginTv.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        switch (view.getId()){
            case R.id.register_btn:
                registerAdmin();
                break;
            case R.id.login_tv:
                Intent intent = new Intent(AdminRegisterActivity.this,AdminLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private void registerAdmin() {
        String userId = binding.userIdEdt.getText().toString().trim();
        String password = binding.passwordEdt.getText().toString().trim();
        String cnfPassword = binding.cnfPasswordEdt.getText().toString().trim();

        if (!userId.isEmpty()){
            if (!password.isEmpty()){
                if(!cnfPassword.isEmpty()){
                    if (password.equals(cnfPassword)){
                        if(!adminOperations.checkAdminIDExist(Integer.parseInt(userId))){
                            Admin admin = new Admin(Integer.parseInt(userId),cnfPassword);
                            adminOperations.addAdmin(admin);
                            Intent intent = new Intent(AdminRegisterActivity.this, AdminDashboardActivity.class);
                            startActivity(intent);
                        }else{
                            binding.userIdTil.setError("Invalid User ID !!");
                            Toast.makeText(this, "User ID already taken", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(AdminRegisterActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    binding.cnfPasswordTil.setError("Required !!");
                    Toast.makeText(AdminRegisterActivity.this, "CnfPassword is empty", Toast.LENGTH_SHORT).show();
                }
            }else{
                binding.passwordTil.setError("Required !!");
                Toast.makeText(AdminRegisterActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            binding.userIdTil.setError("Required !!");
            Toast.makeText(AdminRegisterActivity.this, "UserId is empty", Toast.LENGTH_SHORT).show();
        }
    }

}