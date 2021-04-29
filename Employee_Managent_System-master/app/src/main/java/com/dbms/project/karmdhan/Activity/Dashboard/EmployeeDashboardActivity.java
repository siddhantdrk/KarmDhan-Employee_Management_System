package com.dbms.project.karmdhan.Activity.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.WelcomeActivity;
import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityEmployeeDashboardBinding;

public class EmployeeDashboardActivity extends AppCompatActivity {
    private ActivityEmployeeDashboardBinding binding;
    private EmployeeOperations employeeOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDashboardBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        binding.logoutBtn.setOnClickListener(this::OnClick);
        setEmployeeDetails();
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                Intent intent = new Intent(this, WelcomeActivity.class);
                SharedPreferenceManager.getInstance(this).clear();
                Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
        }
    }

    private Employee getEmployee() {
        employeeOperations = new EmployeeOperations(this);
        return employeeOperations.getEmployeeByNumber(Integer.parseInt(SharedPreferenceManager.getInstance(this).getToken()));
    }

    private void setEmployeeDetails() {
        Employee employee = getEmployee();
        if (employee != null) {
            binding.employeeNumberValueTv.setText(String.valueOf(employee.getEmployeeNumber()));
            binding.employeeNameValueTv.setText(employee.getEmployeeName());
            binding.employeeJobClassValueTv.setText(employee.getEmployeeJobClass());
        } else
            Toast.makeText(this, "Unable to fetch Details !!", Toast.LENGTH_SHORT).show();
    }
}