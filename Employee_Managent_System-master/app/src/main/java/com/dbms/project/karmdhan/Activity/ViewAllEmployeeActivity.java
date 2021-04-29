package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Adapters.ViewAllEmployeeRvAdapter;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.DataOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.databinding.ActivityViewAllEmployeeBinding;

import java.util.List;

public class ViewAllEmployeeActivity extends AppCompatActivity {
    private ActivityViewAllEmployeeBinding binding;
    private AdminOperations adminOperations;
    private ViewAllEmployeeRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllEmployeeBinding.inflate(LayoutInflater.from(this));
        new DataOperations(this).addEmployeeTableData();
        setContentView(binding.getRoot());
        setEmployeeListRv();
    }

    private List<Employee> getAllEmployees() {
        adminOperations = new AdminOperations(this);
        return adminOperations.getAllEmployees();
    }

    private void setEmployeeListRv() {
        List<Employee> employeeList = getAllEmployees();
        if (employeeList.size() == 0) {
            Toast.makeText(this, "Sorry, No data found", Toast.LENGTH_SHORT).show();
        }
        adapter = new ViewAllEmployeeRvAdapter(employeeList, this);
        binding.viewAllEmployeeRv.setAdapter(adapter);
    }
}