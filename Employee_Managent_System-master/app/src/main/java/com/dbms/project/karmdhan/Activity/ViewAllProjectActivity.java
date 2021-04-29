package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;

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
        adapter = new ViewAllProjectRvAdapter(this, projectList);
        binding.viewAllProjectRv.setAdapter(adapter);
    }
}