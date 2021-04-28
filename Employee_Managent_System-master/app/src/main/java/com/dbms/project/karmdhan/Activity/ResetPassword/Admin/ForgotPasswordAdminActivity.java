package com.dbms.project.karmdhan.Activity.ResetPassword.Admin;

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
import com.dbms.project.karmdhan.databinding.ActivityForgotPasswordAdminBinding;

public class ForgotPasswordAdminActivity extends AppCompatActivity {
    private ActivityForgotPasswordAdminBinding binding;
    private AdminOperations adminOperations;
    private SQLiteOpenHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordAdminBinding.inflate(LayoutInflater.from(this));
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
                    if (adminOperations.updatePassword(Integer.parseInt(userId), confPassword)) {
                        Toast.makeText(this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPasswordAdminActivity.this, AdminLoginActivity.class);
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