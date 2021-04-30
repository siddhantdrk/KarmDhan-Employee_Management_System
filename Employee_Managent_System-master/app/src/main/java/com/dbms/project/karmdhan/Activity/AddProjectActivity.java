package com.dbms.project.karmdhan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class AddProjectActivity extends AppCompatActivity {
    private ActivityAddProjectBinding binding;
    private AdminOperations adminOperations;
    private List<Employee> employeeList;
    private ProjectOperations projectOperations;
    private TextInputEditText chargePerHourEdt, hoursBilledEdt;
    private TextInputLayout chargePerHourTil, hoursBilledTil;
    private Button addDetailsDialogueBtn;
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
            chargePerHourTil.setError("Required !!");
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

    private void addProjectEmployeeTableDetails(String projectNumber, int projectLeaderEmployeeNumber, String projectName, String projectLeader) {
        String chargePerHour = chargePerHourEdt.getText().toString().trim();
        String hoursBilled = hoursBilledEdt.getText().toString().trim();
        if (hoursBilled.isEmpty()) {
            hoursBilled = "0.00";
        }
        if (chargePerHour.isEmpty()) {
            chargePerHourTil.setError("Required !!");
            Toast.makeText(AddProjectActivity.this, "Please add the details.", Toast.LENGTH_SHORT).show();
            return;
        }
        projectEmployeeOperations = new ProjectEmployeeOperations(this);
        if (projectEmployeeOperations.addProjectEmployee(new ProjectEmployee(Integer.parseInt(projectNumber), projectLeaderEmployeeNumber, Double.parseDouble(chargePerHour), Double.parseDouble(hoursBilled)))) {
            Toast.makeText(this, "Project Leader Employee Charge Details Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid Details", Toast.LENGTH_SHORT).show();
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
        addDetailsDialogueBtn = dialogView.findViewById(R.id.update_employee_project_details_btn);
        dialogBuilder.setView(dialogView);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        alertDialog.setCancelable(false);
    }

    private void setFieldsNull() {
        binding.projectNumberEdt.setText(null);
        binding.projectNameEdt.setText(null);
        binding.projectLeaderSpinner.setSelection(0);
    }
}