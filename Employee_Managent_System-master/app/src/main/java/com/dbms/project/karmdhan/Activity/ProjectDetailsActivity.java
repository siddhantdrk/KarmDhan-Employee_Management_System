package com.dbms.project.karmdhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityProjectDetailsBinding;

import java.util.List;

public class ProjectDetailsActivity extends AppCompatActivity {
    private ActivityProjectDetailsBinding binding;
    private int projectNum;
    private ProjectOperations projectOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectDetailsBinding.inflate(LayoutInflater.from(this));
        projectNum = getIntent().getIntExtra("ProjectNumber", -1);
        setContentView(binding.getRoot());
        setProjectDetails();
        setProjectEmployeeDetails();
        binding.addEmployeeToProjectBtn.setOnClickListener(this::OnClick);
        binding.updateProjectDetails.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.add_employee_to_project_btn:
                Intent intent = new Intent(this, AddEmployeesToProjectActivity.class);
                intent.putExtra("ProjectNumber", String.valueOf(projectNum).trim());
                startActivity(intent);
                break;
            case R.id.update_project_details:
                Intent updateIntent = new Intent(this, UpdateProjectDetailsActivity.class);
                updateIntent.putExtra("ProjectNumber", projectNum);
                startActivity(updateIntent);
                break;
        }

    }

    private void setProjectEmployeeDetails() {
        List<Employee> employeeList = projectOperations.getAllEmployeeForAProject(projectNum);
        Toast.makeText(this, "" + employeeList.size(), Toast.LENGTH_SHORT).show();
    }

    private void setProjectDetails() {
        projectOperations = new ProjectOperations(this);
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
}