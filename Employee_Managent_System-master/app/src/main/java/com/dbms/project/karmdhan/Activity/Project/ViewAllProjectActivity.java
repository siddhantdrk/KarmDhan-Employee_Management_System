package com.dbms.project.karmdhan.Activity.Project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Adapters.ViewAllProjectRvAdapter;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.databinding.ActivityViewAllProjectBinding;

import java.util.List;

public class ViewAllProjectActivity extends AppCompatActivity {
    private ProjectOperations projectOperations;
    private ViewAllProjectRvAdapter adapter;
    private List<Project> projectList;
    private ActivityViewAllProjectBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        setAllProjectsRv();
    }

    private List<Project> getAllProjects() {
        projectOperations = new ProjectOperations(this);
        return projectOperations.getAllProjects();
    }

    private void setAllProjectsRv() {
        projectList = getAllProjects();
        if (projectList != null && projectList.size() != 0) {
            adapter = new ViewAllProjectRvAdapter(this, projectList);
            binding.viewAllProjectRv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Sorry, No data found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAllProjectsRv();
    }
}