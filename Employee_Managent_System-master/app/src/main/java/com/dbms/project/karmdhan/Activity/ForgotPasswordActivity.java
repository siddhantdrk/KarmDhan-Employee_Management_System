package com.dbms.project.karmdhan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.dbms.project.karmdhan.Activity.Login.AdminLoginActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.KarmDhanDBHandler;
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
            check();
        }
    }

    private void check() {
        String password = binding.newPasswordEdt.getText().toString().trim();
        String confPassword = binding.cnfPasswordEdt.getText().toString().trim();

        if(!password.isEmpty()){
            if(!confPassword.isEmpty()){
                if (password.equals(confPassword)){
                    SQLiteDatabase database = myDbHelper.getWritableDatabase();
                    String userId = getIntent().getStringExtra("USERID");
                    String strSQL = "UPDATE myTable SET COLUMN_ADMIN_PASSWORD = "+ password+"someValue WHERE COLUMN_ADMIN_ID = "+ userId;
                    database.execSQL(strSQL);
                    Intent intent = new Intent(ForgotPasswordActivity.this, AdminLoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Confirm Password is empty", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
        }
    }
}