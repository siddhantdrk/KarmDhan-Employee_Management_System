package com.dbms.project.karmdhan.Activity.ResetPassword.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityChangeOrForgotPasswordEmployeeBinding;

public class ChangeOrForgotPasswordEmployeeActivity extends AppCompatActivity {
    private ActivityChangeOrForgotPasswordEmployeeBinding binding;
    private EmployeeOperations employeeOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeOrForgotPasswordEmployeeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        employeeOperations = new EmployeeOperations(this);
        binding.changePassword.setOnClickListener(this::onClick);
        binding.forgotPassword.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        switch (view.getId()){
            case R.id.change_password:
                checkUserIdChange();
                break;
            case R.id.forgot_password:
                checkUserIdForForgot();
                break;
        }
    }

    private void checkUserIdForForgot() {
        String employeeNumber = binding.userIdEdt.getText().toString().trim();
        if(!employeeNumber.isEmpty()){
            if (!employeeOperations.checkIfEmployeeNumberExist(Integer.parseInt(employeeNumber))) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ChangeOrForgotPasswordEmployeeActivity.this, ForgotPasswordEmployeeActivity.class);
                intent.putExtra("EMPLOYEE_NUMBER", employeeNumber);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserIdChange() {
        String employeeNumber = binding.userIdEdt.getText().toString().trim();
        if(!employeeNumber.isEmpty()){
            if (!employeeOperations.checkIfEmployeeNumberExist(Integer.parseInt(employeeNumber))) {
                Toast.makeText(this, "User ID doesn't exist", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ChangeOrForgotPasswordEmployeeActivity.this, ChangePasswordEmployeeActivity.class);
                intent.putExtra("EMPLOYEE_NUMBER", employeeNumber);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this, "User ID is empty", Toast.LENGTH_SHORT).show();
        }
    }
}