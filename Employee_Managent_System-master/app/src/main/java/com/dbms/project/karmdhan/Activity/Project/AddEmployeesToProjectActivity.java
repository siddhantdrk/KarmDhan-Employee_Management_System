package com.dbms.project.karmdhan.Activity.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.ProjectEmployeeOperations;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddEmployeesToProjectBinding;

import java.util.List;

public class AddEmployeesToProjectActivity extends AppCompatActivity {
    private ActivityAddEmployeesToProjectBinding binding;
    private ProjectOperations projectOperations;
    private ProjectEmployeeOperations projectEmployeeOperations;
    private List<Employee> employeeList;
    private int projectNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmployeesToProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setProjectDetails();
        setUpProjectLeaderSpinner();
        binding.addEmployeeToProjectBtn.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_employee_to_project_btn:
                addEmployeeToProject();
                break;
        }
    }

    private void addEmployeeToProject() {
        String chargePerHour = binding.chargePerHrEdt.getText().toString().trim();
        String hoursBilled = binding.hrsBilledEdt.getText().toString().trim();
        int employeeNumber = employeeList.get(binding.employeeSpinner.getSelectedItemPosition()).getEmployeeNumber();
        if (hoursBilled.isEmpty()) {
            hoursBilled = "0.00";
        }
        if (chargePerHour.isEmpty()) {
            binding.chargePerHrTil.setError("Required !!");
            Toast.makeText(this, "Please add the details.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (projectEmployeeOperations.addProjectEmployee(new ProjectEmployee(projectNum, employeeNumber, Double.parseDouble(chargePerHour), Double.parseDouble(hoursBilled)))) {
            setFieldsNull();
            setUpProjectLeaderSpinner();
            Toast.makeText(this, "Employee Added to Project Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Oops !! Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void setProjectDetails() {
        projectOperations = new ProjectOperations(this);
        projectNum = Integer.parseInt(getIntent().getStringExtra("ProjectNumber"));
        Project project = projectOperations.getProjectByNumber(projectNum);
        if (project != null) {
            binding.projectNumberValueTv.setText(String.valueOf(project.getProjectNumber()));
            binding.projectNameValueTv.setText(project.getProjectName());
            binding.projectLeaderValueTv.setText("Employee : " + project.getProjectLeaderEmployeeNumber() + "\n" + project.getProjectLeaderName());
        } else {
            Toast.makeText(this, "Oops !! something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setUpProjectLeaderSpinner() {
        projectEmployeeOperations = new ProjectEmployeeOperations(this);
        employeeList = projectEmployeeOperations.getAllRemainingEmployees(projectNum);
        ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, employeeList);
        binding.employeeSpinner.setAdapter(arrayAdapter);
    }

    private void setFieldsNull() {
        binding.chargePerHrEdt.setText(null);
        binding.hrsBilledEdt.setText(null);
        binding.employeeSpinner.setSelection(0);
    }
}