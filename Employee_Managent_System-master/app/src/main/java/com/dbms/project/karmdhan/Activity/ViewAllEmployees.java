package com.dbms.project.karmdhan.Activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class ViewAllEmployees extends ListActivity {

    private EmployeeOperations employeeOperations;
    List<Employee> employees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_employees);

        employeeOperations=new EmployeeOperations(this);
        employeeOperations.open();
        employees=employeeOperations.getAllEmployees();
        employeeOperations.close();
        if(!employees.isEmpty()) {
            ArrayAdapter<Employee> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, employees);
            setListAdapter(adapter);
        }
        else
            Toast.makeText(this, "No Data Present", Toast.LENGTH_SHORT).show();
    }
}
