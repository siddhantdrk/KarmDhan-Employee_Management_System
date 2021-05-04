package com.dbms.project.karmdhan.Activity.Project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.DataOperations;
import com.dbms.project.karmdhan.DB.ProjectEmployeeOperations;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

import java.util.List;

public class AddProjectActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private AdminOperations adminOperations;
    private List<Employee> employeeList;
    private ProjectOperations projectOperations;
    private ProjectEmployeeOperations projectEmployeeOperations;
    private DataOperations dataOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setUpProjectLeaderSpinner();
        addGivenProjectData();
        binding.addProjectBtn.setOnClickListener(this::OnClick);
    }

    private void addGivenProjectData() {
        dataOperations = new DataOperations(this);
        dataOperations.addProjectTableData();
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_project_btn:
                addProject();
                break;
        }
    }

    private void addProject() {
        String projectNumber = binding.projectNumberEdt.getText().toString().trim();
        String projectName = binding.projectNameEdt.getText().toString().trim();
        int projectLeaderEmployeeNumber = employeeList.get(binding.projectLeaderSpinner.getSelectedItemPosition()).getEmployeeNumber();
        String chargePerHour = binding.chargePerHrEdt.getText().toString().trim();
        String hoursBilled = binding.hrsBilledEdt.getText().toString().trim();
        if (hoursBilled.isEmpty()) {
            hoursBilled = "0.00";
        }
        if (projectNumber.isEmpty()) {
            binding.projectNumberTil.setError("Required!!");
            Toast.makeText(this, "Project Number is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (projectName.isEmpty()) {
            binding.projectNameTil.setError("Required!!");
            Toast.makeText(this, "Project Name is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (chargePerHour.isEmpty()) {
            binding.chargePerHrTil.setError("Required !!");
            Toast.makeText(AddProjectActivity.this, "Please add the details.", Toast.LENGTH_SHORT).show();
            return;
        }
        projectOperations = new ProjectOperations(this);
        if (!projectOperations.checkIfProjectNumberExists(Integer.parseInt(projectNumber))) {
            if (projectOperations.addProject(new Project(Integer.parseInt(projectNumber), projectName, projectLeaderEmployeeNumber), new ProjectEmployee(Integer.parseInt(projectNumber), projectLeaderEmployeeNumber, Double.parseDouble(chargePerHour), Double.parseDouble(hoursBilled)))) {
                Toast.makeText(this, "Project Added Successfully", Toast.LENGTH_SHORT).show();
                setFieldsNull();
                Intent intent = new Intent(this, AddEmployeesToProjectActivity.class);
                intent.putExtra("ProjectNumber", projectNumber);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Oops !! something went wrong.", Toast.LENGTH_SHORT).show();
            }
        } else {
            binding.projectNumberTil.setError("Enter another project number");
            Toast.makeText(this, "Project Number Already Exist", Toast.LENGTH_SHORT).show();
        }
    }


    private void setUpProjectLeaderSpinner() {
        adminOperations = new AdminOperations(this);
        employeeList = adminOperations.getAllEmployees();
        ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, employeeList);
        binding.projectLeaderSpinner.setAdapter(arrayAdapter);
    }


    private void setFieldsNull() {
        binding.projectNumberEdt.setText(null);
        binding.projectNameEdt.setText(null);
        binding.projectLeaderSpinner.setSelection(0);
    }
}