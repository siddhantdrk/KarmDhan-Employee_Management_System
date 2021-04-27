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
import com.dbms.project.karmdhan.databinding.ActivityRenewPasswordBinding;

public class RenewPasswordActivity extends AppCompatActivity {
    private ActivityRenewPasswordBinding binding;
    private AdminOperations adminOperations;
    private SQLiteOpenHelper myDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityRenewPasswordBinding.inflate(LayoutInflater.from(this));
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

        if(!oldPassword.isEmpty()){
            if(!newPassword.isEmpty()){
                String userId = getIntent().getStringExtra("USERID");
                adminOperations.updatePassword(Integer.parseInt(userId), newPassword);
                Intent intent = new Intent(RenewPasswordActivity.this, AdminLoginActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(this, "new Password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "old Password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}