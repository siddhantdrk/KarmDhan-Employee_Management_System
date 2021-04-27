package com.dbms.project.karmdhan.Activity.ResetPassword;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Login.AdminLoginActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private AdminOperations adminOperations;
    private SQLiteOpenHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
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
                    String userId = getIntent().getStringExtra("USERID");
                    adminOperations.updatePassword(Integer.parseInt(userId), password);
                    Intent intent = new Intent(ForgotPasswordActivity.this, AdminLoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }else{
                binding.confPasswordTil.setError("password field should not kept empty");
                Toast.makeText(this, "Confirm Password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            binding.newPasswordTil.setError("Confirm password field should not kept empty");
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}