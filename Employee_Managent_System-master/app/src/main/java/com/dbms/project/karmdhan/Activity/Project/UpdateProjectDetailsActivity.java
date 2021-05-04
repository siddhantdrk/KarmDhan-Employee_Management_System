package com.dbms.project.karmdhan.Activity.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

import java.util.List;

public class UpdateProjectDetailsActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private int projectNum;
    private ProjectOperations projectOperations;
    private List<Employee> employeeList;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        projectOperations = new ProjectOperations(this);
        projectNum = getIntent().getIntExtra("ProjectNumber", -1);
        hideUnNecessaryAndUpdateUI();
        setUpProjectLeaderSpinner();
        setCurrentDetails();
        binding.addProjectBtn.setOnClickListener(this::OnClick);
    }


    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_project_btn:
                updateProject();
                break;
        }
    }

    private void setCurrentDetails() {
        project = projectOperations.getProjectByNumber(projectNum);
        binding.projectNameEdt.setText(project.getProjectName());
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmployeeNumber() == project.getProjectLeaderEmployeeNumber()) {
                binding.projectLeaderSpinner.setSelection(i);
            }
        }
    }


    private void updateProject() {
        String projectName = binding.projectNameEdt.getText().toString().trim();
        int projectLeaderEmployeeNumber = employeeList.get(binding.projectLeaderSpinner.getSelectedItemPosition()).getEmployeeNumber();
        if (projectName.equals(project.getProjectName()) && projectLeaderEmployeeNumber == project.getProjectLeaderEmployeeNumber()) {
            Toast.makeText(this, "No Change Found", Toast.LENGTH_SHORT).show();
        } else {
            project.setProjectName(projectName);
            project.setProjectLeaderEmployeeNumber(projectLeaderEmployeeNumber);
            if (projectOperations.updateProjectDetails(project)) {
                Toast.makeText(this, "Project Details Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Oops Something went wrong.", Toast.LENGTH_SHORT).show();
            }
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
        binding.fillUpDetailsProjectLeaderTv.setVisibility(View.GONE);
        binding.chargePerHrEdt.setVisibility(View.GONE);
        binding.chargePerHrEdt.setVisibility(View.GONE);
        binding.chargePerHrTil.setVisibility(View.GONE);
        binding.hrsBilledTil.setVisibility(View.GONE);
        binding.addProjectBtn.setText("Update");
    }
}