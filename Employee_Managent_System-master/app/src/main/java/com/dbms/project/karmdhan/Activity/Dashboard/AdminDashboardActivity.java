package com.dbms.project.karmdhan.Activity.Dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dbms.project.karmdhan.Activity.Employee.AddEmployeeActivity;
import com.dbms.project.karmdhan.Activity.Employee.ViewAllEmployeeActivity;
import com.dbms.project.karmdhan.Activity.Project.AddProjectActivity;
import com.dbms.project.karmdhan.Activity.Project.ViewAllProjectActivity;
import com.dbms.project.karmdhan.Activity.WelcomeActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.R;
import com.dbms.project.karmdhan.Storage.SharedPreferenceManager;
import com.dbms.project.karmdhan.databinding.ActivityAdminDashboardBinding;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityAdminDashboardBinding binding;
    private AdminOperations adminOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        adminOperations = new AdminOperations(this);
        binding.logoutBtn.setOnClickListener(this::OnClick);
        binding.addEmployee.setOnClickListener(this::OnClick);
        binding.viewAllEmployeeBtn.setOnClickListener(this::OnClick);
        binding.addProjectBtn.setOnClickListener(this::OnClick);
        binding.viewAllProjectBtn.setOnClickListener(this::OnClick);
        setDetails();
    }

    private void setDetails() {
        binding.userIdValueTv.setText(SharedPreferenceManager.getInstance(this).getToken());
        binding.totalCostValueTv.setText("$" + String.format("%1$,.2f", adminOperations.getTotalCost()));
    }

    private void OnClick(View view) {
        switch (view.getId()) {
            case R.id.logout_btn:
                Intent intent = new Intent(this, WelcomeActivity.class);
                SharedPreferenceManager.getInstance(this).clear();
                Toast.makeText(this, "Logged out Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
            case R.id.add_employee:
                startActivity(new Intent(this, AddEmployeeActivity.class));
                break;
            case R.id.view_all_employee_btn:
                Intent viewAllIntent = new Intent(this, ViewAllEmployeeActivity.class);
                startActivity(viewAllIntent);
                break;
            case R.id.add_project_btn:
                startActivity(new Intent(this, AddProjectActivity.class));
                break;
            case R.id.view_all_project_btn:
                startActivity(new Intent(this, ViewAllProjectActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDetails();
    }
}