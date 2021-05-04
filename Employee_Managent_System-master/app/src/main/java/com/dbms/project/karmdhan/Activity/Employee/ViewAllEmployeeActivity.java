package com.dbms.project.karmdhan.Activity.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Adapters.ViewAllEmployeeRvAdapter;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.DataOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.databinding.ActivityViewAllEmployeeBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ViewAllEmployeeActivity extends AppCompatActivity implements ViewAllEmployeeRvAdapter.OnUpdateClickListener {
    private ActivityViewAllEmployeeBinding binding;
    private AdminOperations adminOperations;
    private ViewAllEmployeeRvAdapter adapter;
    private TextInputEditText employeeNameEdt;
    private Spinner jobClassSpinner;
    private Button updateDetailsBtn;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllEmployeeBinding.inflate(LayoutInflater.from(this));
        new DataOperations(this).addEmployeeTableData();
        setContentView(binding.getRoot());
        setEmployeeListRv();
    }

    private List<Employee> getAllEmployees() {
        adminOperations = new AdminOperations(this);
        return adminOperations.getAllEmployees();
    }

    private void setEmployeeListRv() {
        List<Employee> employeeList = getAllEmployees();
        if (employeeList.size() == 0) {
            Toast.makeText(this, "Sorry, No data found", Toast.LENGTH_SHORT).show();
        }
        adapter = new ViewAllEmployeeRvAdapter(employeeList, this, this);
        binding.viewAllEmployeeRv.setAdapter(adapter);
    }

    @Override
    public void onClick(Employee employee) {
        showProjectEmployeeFieldsDialogue();
        updateDetailsBtn.setOnClickListener(view -> updateEmployeeDetails(employee));
    }

    private void updateEmployeeDetails(Employee employee) {
        String employeeName = employeeNameEdt.getText().toString().trim();
        int position = jobClassSpinner.getSelectedItemPosition();
        if (employeeName.isEmpty() && position == 0) {
            Toast.makeText(this, "No change found to update", Toast.LENGTH_SHORT).show();
            return;
        }
        String jobClass = jobClassSpinner.getSelectedItem().toString().trim();
        if (employeeName.equals(employee.getEmployeeName()) && jobClass.equals(employee.getEmployeeJobClass())) {
            Toast.makeText(this, "No change found to update", Toast.LENGTH_SHORT).show();
            return;
        }
        if (employeeName.length() != 0)
            employee.setEmployeeName(employeeName);
        if (position != 0)
            employee.setEmployeeJobClass(jobClass);
        if (adminOperations.updateEmployeeDetails(employee)) {
            Toast.makeText(this, "Details Updated Successfully", Toast.LENGTH_SHORT).show();
            setEmployeeListRv();
            alertDialog.dismiss();
        }
    }

    private void showProjectEmployeeFieldsDialogue() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_employee_details_dialogue, null);
        employeeNameEdt = dialogView.findViewById(R.id.employee_name_edt);
        jobClassSpinner = dialogView.findViewById(R.id.job_class_spinner);
        updateDetailsBtn = dialogView.findViewById(R.id.update_details_btn);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}