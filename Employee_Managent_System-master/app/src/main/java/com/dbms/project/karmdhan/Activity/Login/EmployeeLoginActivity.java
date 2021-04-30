package com.dbms.project.karmdhan.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Dashboard.EmployeeDashboardActivity;
import com.dbms.project.karmdhan.Activity.ResetPassword.Employee.ChangeOrForgotPasswordEmployeeActivity;
import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityEmployeeLoginBinding;

public class EmployeeLoginActivity extends AppCompatActivity {
    private EmployeeOperations employeeOperations;
    private ActivityEmployeeLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.dbms.project.karmdhan.databinding.ActivityEmployeeLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.loginBtn.setOnClickListener(this::onClick);
        binding.forgotPasswordTv.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if (view.getId()==R.id.login_btn) {
            login();
        }else if(view.getId()==R.id.forgot_password_tv){
            Intent intent = new Intent(EmployeeLoginActivity.this, ChangeOrForgotPasswordEmployeeActivity.class);
            startActivity(intent);
        }
    }

    private void login() {
        String employeeNumber = binding.userIdEdt.getText().toString().trim();
        String password = binding.passwordEdt.getText().toString().trim();

        if (!employeeNumber.isEmpty()) {
            employeeOperations = new EmployeeOperations(this);
            if (employeeOperations.checkIfEmployeeNumberExist(Integer.parseInt(employeeNumber))) {
                if (!password.isEmpty()) {
                    if (employeeOperations.checkEmployeeLoginCredentials(new Employee(Integer.parseInt(employeeNumber), password))) {
                        Toast.makeText(this, "Logged In successfully!", Toast.LENGTH_SHORT).show();
                        SharedPreferenceManager.getInstance(this).saveToken(employeeNumber);
                        Intent intent = new Intent(this, EmployeeDashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "Sorry, you haven't been Registered yet. Please contact to your Admin.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.passwordTil.setError("Required!!");
                    Toast.makeText(EmployeeLoginActivity.this, "password is empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.userIdTil.setError("Invalid User ID !!");
                Toast.makeText(this, "User ID doesn't Exist !", Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.passwordTil.setError("Required!!");
            Toast.makeText(EmployeeLoginActivity.this, "userID is empty", Toast.LENGTH_SHORT).show();
        }
    }
}