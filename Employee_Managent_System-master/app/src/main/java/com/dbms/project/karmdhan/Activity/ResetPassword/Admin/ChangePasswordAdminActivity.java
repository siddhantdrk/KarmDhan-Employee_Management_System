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
import com.dbms.project.karmdhan.Model.Admin;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityChangePasswordAdminBinding;

public class ChangePasswordAdminActivity extends AppCompatActivity {
    private ActivityChangePasswordAdminBinding binding;
    private AdminOperations adminOperations;
    private SQLiteOpenHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChangePasswordAdminBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        binding.resetPasswordBtn.setOnClickListener(this::onClick);
    }

    private void onClick(View view) {
        if(view.getId()== R.id.resetPassword_btn){
            renewPassword();
        }
    }

    private void renewPassword() {
        String oldPassword = binding.oldPasswordEdt.getText().toString().trim();
        String newPassword = binding.newPasswordEdt.getText().toString().trim();

        if(!oldPassword.isEmpty()) {
            String userId = getIntent().getStringExtra("USERID");
            if (adminOperations.checkAdminLoginCredentials(new Admin(Integer.parseInt(userId), oldPassword))) {
                if (!newPassword.isEmpty()) {
                    if (adminOperations.updatePassword(Integer.parseInt(userId), newPassword)) {
                        Toast.makeText(this, "Password Updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChangePasswordAdminActivity.this, AdminLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } else {
                    binding.newPasswordTil.setError("Required!!");
                    Toast.makeText(this, "new Password is empty", Toast.LENGTH_SHORT).show();
                }
            } else {
                binding.oldPasswordTil.setError("Incorrect Old Password !!");
                Toast.makeText(this, "old Password is Incorrect", Toast.LENGTH_SHORT).show();
            }
        }else{
            binding.oldPasswordTil.setError("Required!!");
            Toast.makeText(this, "old Password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}