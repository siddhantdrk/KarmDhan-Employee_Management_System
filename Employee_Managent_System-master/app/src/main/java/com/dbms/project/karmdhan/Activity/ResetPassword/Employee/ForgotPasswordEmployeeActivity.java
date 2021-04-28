package com.dbms.project.karmdhan.Activity.ResetPassword.Employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dbms.project.karmdhan.Activity.Login.EmployeeLoginActivity;
import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityForgotPasswordEmployeeBinding;

public class ForgotPasswordEmployeeActivity extends AppCompatActivity {
    private ActivityForgotPasswordEmployeeBinding binding;
    private EmployeeOperations employeeOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordEmployeeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        employeeOperations = new EmployeeOperations(this);
        binding.resetPasswordBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if(view.getId()== R.id.resetPassword_btn){
            resetPassword();
        }
    }

    private void resetPassword() {
        String password = binding.newPasswordEdt.getText().toString().trim();
        String confPassword = binding.cnfPasswordEdt.getText().toString().trim();

        if(!password.isEmpty()){
            if(!confPassword.isEmpty()){
                if (password.equals(confPassword)){
                    String userId = getIntent().getStringExtra("EMPLOYEE_NUMBER");
                    if (employeeOperations.updatePassword(Integer.parseInt(userId), confPassword)) {
                        Toast.makeText(this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordEmployeeActivity.this, EmployeeLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }else{
                binding.confPasswordTil.setError("Required!!");
                Toast.makeText(this, "Confirm Password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            binding.newPasswordTil.setError("Required!!");
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}