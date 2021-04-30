package com.dbms.project.karmdhan.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityAddProjectBinding;

import java.util.List;

public class UpdateProjectDetailsActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private int projectNum;
    private AdminOperations adminOperations;
    private List<Employee> employeeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProjectBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
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
        String chargePerHour = binding.chargePerHrEdt.getText().toString().trim();
        String hoursBilled = binding.hrsBilledEdt.getText().toString().trim();

    }

    private void setUpProjectLeaderSpinner() {
        adminOperations = new AdminOperations(this);
        employeeList = adminOperations.getAllEmployees();
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