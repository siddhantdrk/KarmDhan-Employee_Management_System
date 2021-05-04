package com.dbms.project.karmdhan.Activity.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Authentication.PasswordAuthentication;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.DataOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddEmployeeBinding;

import java.util.Random;

public class AddEmployeeActivity extends AppCompatActivity {
    private ActivityAddEmployeeBinding binding;
    private AdminOperations adminOperations;
    private PasswordAuthentication passwordAuthentication;
    private DataOperations dataOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeeBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        addGivenEmployeeData();
        binding.addEmployeeBtn.setOnClickListener(this::OnCLick);
    }

    private void OnCLick(View view) {
        switch (view.getId()) {
            case R.id.add_employee_btn:
                addEmployee();
                break;
        }
    }

    //add given employee data
    private void addGivenEmployeeData() {
        dataOperations = new DataOperations(this);
        dataOperations.addEmployeeTableData();
    }

    private void addEmployee() {
        String employeeNumber = binding.employeeNumberEdt.getText().toString().trim();
        String employeeName = binding.employeeNameEdt.getText().toString().trim();
        int position = binding.jobClassSpinner.getSelectedItemPosition();
        if (employeeNumber.isEmpty()) {
            binding.employeeNumberTil.setError("Employee Number is required");
            return;
        }
        if (employeeName.isEmpty()) {
            binding.employeeNameTil.setError("Employee name is required !!");
            return;
        }
        if (position == 0) {
            Toast.makeText(this, "Please Select Job Class for Employee", Toast.LENGTH_SHORT).show();
        } else {
            String jobClass = binding.jobClassSpinner.getSelectedItem().toString().trim();
            String employeePassword = GenerateRandomNumber(8);
            adminOperations = new AdminOperations(this);
            if (adminOperations.checkIfEmployeeNumberExist(Integer.parseInt(employeeNumber))) {
                binding.employeeNumberTil.setError("Please enter another Employee Number");
                Toast.makeText(this, "Employee Number Already Exist", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adminOperations.addEmployee(new Employee(Integer.parseInt(employeeNumber), employeeName, jobClass, employeePassword))) {
                setFieldsNull();
                Toast.makeText(this, "Employee Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }

    private void setFieldsNull() {
        binding.employeeNumberEdt.setText(null);
        binding.employeeNameEdt.setText(null);
        binding.jobClassSpinner.setSelection(0);
    }
}