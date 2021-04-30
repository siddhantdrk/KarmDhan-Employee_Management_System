package com.dbms.project.karmdhan.Activity;

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
import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

import java.util.List;

public class UpdateProjectDetailsActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private int projectNum;
    private ProjectOperations projectOperations;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        projectOperations = new ProjectOperations(this);
        projectNum = getIntent().getIntExtra("ProjectNumber", -1);
        hideUnNecessaryAndUpdateUI();
        setUpProjectLeaderSpinner();
        binding.addProjectBtn.setOnClickListener(this::OnClick);
    }


    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_project_btn:
                updateProject();
                break;
        }
    }


    private void updateProject() {
        String projectName = binding.projectNameEdt.getText().toString().trim();
        int projectLeaderEmployeeNumber = employeeList.get(binding.projectLeaderSpinner.getSelectedItemPosition()).getEmployeeNumber();
        Project project = projectOperations.getProjectByNumber(projectNum);
        ProjectEmployee projectEmployee = new ProjectEmployeeOperations(this).getProjectEmployeeByProjectAndEmployeeNum(projectNum, project.getProjectLeaderEmployeeNumber());
        if (!projectName.isEmpty()) {
            project.setProjectName(projectName);
        }
        if (projectLeaderEmployeeNumber != project.getProjectLeaderEmployeeNumber()) {
            project.setProjectLeaderEmployeeNumber(projectLeaderEmployeeNumber);
        }
        if (!binding.chargePerHrEdt.getText().toString().trim().isEmpty()) {
            double chargePerHour = Double.parseDouble(binding.chargePerHrEdt.getText().toString().trim());
            if (chargePerHour != projectEmployee.getChargePerHour()) {
                projectEmployee.setChargePerHour(chargePerHour);
            }
        }
        if (!binding.hrsBilledEdt.getText().toString().trim().isEmpty()) {
            double hoursBilled = Double.parseDouble(binding.hrsBilledEdt.getText().toString().trim());
            if (hoursBilled != projectEmployee.getHoursBilled()) {
                projectEmployee.setHoursBilled(hoursBilled);
            }
        }
        if (projectOperations.updateProjectDetails(project, projectEmployee)) {
            Toast.makeText(this, "Project Details Updated Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Oops Something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setUpProjectLeaderSpinner() {
        employeeList = projectOperations.getAllEmployeeForAProject(projectNum);
        ArrayAdapter<Employee> arrayAdapter = new ArrayAdapter<>(this, R.layout.my_spinner_item, employeeList);
        binding.projectLeaderSpinner.setAdapter(arrayAdapter);
    }

    private void hideUnNecessaryAndUpdateUI() {
        binding.projectNumberTil.setVisibility(View.GONE);
        binding.projectNumberEdt.setVisibility(View.GONE);
        binding.fillUpDetailsTv.setText("Update the project Details Below");
        binding.fillUpDetailsProjectLeaderTv.setText("Update the details below for Project Leader");
        binding.addProjectBtn.setText("Update");
    }
}