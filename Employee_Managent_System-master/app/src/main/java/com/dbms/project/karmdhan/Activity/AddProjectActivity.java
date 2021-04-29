package com.dbms.project.karmdhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.NewEmployee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

import java.util.List;

public class AddProjectActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private AdminOperations adminOperations;
    private List<NewEmployee> employeeList;
    private ProjectOperations projectOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setUpProjectLeaderSpinner();
        binding.addProjectBtn.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_project_btn:
                addProject();
        }
    }

    private void addProject() {
        String projectNumber = binding.projectNumberEdt.getText().toString().trim();
        String projectName = binding.projectNameEdt.getText().toString().trim();
        String projectLeader = employeeList.get(binding.projectLeaderSpinner.getSelectedItemPosition()).getEmployeeName();
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
        projectOperations = new ProjectOperations(this);
        if (projectOperations.addProject(new Project(Integer.parseInt(projectNumber), projectName, projectLeader))) {
            setFieldsNull();
            Toast.makeText(this, "Project Added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AddEmployeesToProjectActivity.class);
            intent.putExtra("projectNumber", projectNumber);
            startActivity(intent);
        } else {
            binding.projectNumberTil.setError("Enter another project number");
            Toast.makeText(this, "Project Number Already Exist", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpProjectLeaderSpinner() {
        adminOperations = new AdminOperations(this);
        employeeList = adminOperations.getAllEmployees();
        ArrayAdapter<NewEmployee> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, employeeList);
        binding.projectLeaderSpinner.setAdapter(arrayAdapter);
    }

    private void setFieldsNull() {
        binding.projectNumberEdt.setText(null);
        binding.projectNameEdt.setText(null);
        binding.projectLeaderSpinner.setSelection(0);
    }
}