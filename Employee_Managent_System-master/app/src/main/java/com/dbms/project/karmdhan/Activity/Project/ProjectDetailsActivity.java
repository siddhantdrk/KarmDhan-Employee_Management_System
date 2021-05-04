package com.dbms.project.karmdhan.Activity.Project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Adapters.projectEmployeeOnlyRvAdapter;
import com.dbms.project.karmdhan.DB.ProjectEmployeeOperations;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityProjectDetailsBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ProjectDetailsActivity extends AppCompatActivity implements projectEmployeeOnlyRvAdapter.OnUpdateClickListener {
    private ActivityProjectDetailsBinding binding;
    private int projectNum;
    private ProjectOperations projectOperations;
    private projectEmployeeOnlyRvAdapter adapter;
    private List<Employee> employeeList;
    private TextInputEditText chargePerHourEdt, hoursBilledEdt;
    private TextInputLayout chargePerHourTil, hoursBilledTil;
    private Button updateDetailsDialogueBtn;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectDetailsBinding.inflate(LayoutInflater.from(this));
        projectNum = getIntent().getIntExtra("ProjectNumber", -1);
        setContentView(binding.getRoot());
        setProjectDetails();
        setAllEmployeeRv();
        binding.addEmployeeToProjectBtn.setOnClickListener(this::OnClick);
        binding.updateProjectDetails.setOnClickListener(this::OnClick);
    }

    private List<Employee> getAllProjects() {
        projectOperations = new ProjectOperations(this);
        return projectOperations.getAllEmployeeForAProject(projectNum);
    }

    private void setAllEmployeeRv() {
        employeeList = getAllProjects();
        if (employeeList != null && employeeList.size() != 0) {
            adapter = new projectEmployeeOnlyRvAdapter(employeeList, this, projectNum, this);
            binding.projectEmployeesOnlyRv.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Sorry, No data found", Toast.LENGTH_SHORT).show();
        }
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

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void setProjectDetails() {
        projectOperations = new ProjectOperations(this);
        Project project = projectOperations.getProjectByNumber(projectNum);
        if (project != null) {
            binding.projectNumberValueTv.setText(String.valueOf(project.getProjectNumber()));
            binding.projectNameValueTv.setText(project.getProjectName());
            binding.projectLeaderValueTv.setText("Employee : " + project.getProjectLeaderEmployeeNumber() + "\n" + project.getProjectLeaderName());
            binding.projectCostValueTv.setText("$" + String.format("%1$,.2f", projectOperations.getProjectCost(projectNum)));
        } else {
            Toast.makeText(this, "Oops !! something went wrong", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showProjectEmployeeFieldsDialogue() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_project_employe_fields_dialogue, null);
        chargePerHourEdt = dialogView.findViewById(R.id.charge_per_hr_edt);
        hoursBilledEdt = dialogView.findViewById(R.id.hrs_billed_edt);
        chargePerHourTil = dialogView.findViewById(R.id.charge_per_hr_til);
        hoursBilledTil = dialogView.findViewById(R.id.hrs_billed_til);
        updateDetailsDialogueBtn = dialogView.findViewById(R.id.update_employee_project_details_btn);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void updateProjectEmployeeDetails(ProjectEmployee projectEmployee) {
        String chargePerHour = chargePerHourEdt.getText().toString().trim();
        String hoursBilled = hoursBilledEdt.getText().toString().trim();
        if (!chargePerHour.isEmpty() && !hoursBilled.isEmpty())
            if (Double.parseDouble(chargePerHour) == projectEmployee.getChargePerHour() && Double.parseDouble(hoursBilled) == projectEmployee.getHoursBilled()) {
                Toast.makeText(this, "No Change Found", Toast.LENGTH_SHORT).show();
                return;
            }
        if (chargePerHour.isEmpty() && hoursBilled.isEmpty()) {
            Toast.makeText(this, "No Change Found", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!hoursBilled.isEmpty()) {
            projectEmployee.setHoursBilled(Double.parseDouble(hoursBilled));
        }
        if (!chargePerHour.isEmpty()) {
            projectEmployee.setChargePerHour(Double.parseDouble(chargePerHour));
        }
        if (new ProjectEmployeeOperations(this).updateProjectEmployee(projectEmployee)) {
            Toast.makeText(this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            setAllEmployeeRv();
            binding.projectCostValueTv.setText("$" + String.format("%1$,.2f", projectOperations.getProjectCost(projectNum)));
            alertDialog.dismiss();
        } else {
            Toast.makeText(this, "Oops !! something went wrong.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProjectDetails();
        setAllEmployeeRv();
    }

    @Override
    public void onClick(ProjectEmployee projectEmployee) {
        showProjectEmployeeFieldsDialogue();
        updateDetailsDialogueBtn.setOnClickListener(view -> {
            updateProjectEmployeeDetails(projectEmployee);
        });
    }
}