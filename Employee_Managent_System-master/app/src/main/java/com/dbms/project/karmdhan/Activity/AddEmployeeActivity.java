package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddEmployeeBinding;

import java.util.Random;

public class AddEmployeeActivity extends AppCompatActivity {
    private ActivityAddEmployeeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.addEmployeeBtn.setOnClickListener(this::OnCLick);
    }

    private void OnCLick(View view) {
        switch (view.getId()) {
            case R.id.add_employee_btn:
                addEmployee();
                break;
        }
    }

    private void addEmployee() {
        int employeeNumber = Integer.parseInt(binding.employeeNumberEdt.getText().toString().trim());
        String employeeName = binding.employeeNameEdt.getText().toString().trim();

    }

    String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }
}