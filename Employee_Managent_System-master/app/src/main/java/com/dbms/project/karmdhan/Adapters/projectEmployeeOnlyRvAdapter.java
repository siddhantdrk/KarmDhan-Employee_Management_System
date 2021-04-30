package com.dbms.project.karmdhan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class projectEmployeeOnlyRvAdapter extends RecyclerView.Adapter<projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder>{
    private List<Employee> employeeList;
    private Context context;

    public projectEmployeeOnlyRvAdapter(List<Employee> employeeList, Context context) {
        this.employeeList = employeeList;
        this.context = context;
    }

    @NonNull
    @Override
    public projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_employee_only_rv_item,parent,false);
        return new projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class ProjectEmployeeViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeNumber;
        private TextView employeeName;
        private TextView jobClass;
        private TextView chargePerHour, hoursBilled;

        public ProjectEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeNumber = itemView.findViewById(R.id.employee_number_value_tv);
            employeeName = itemView.findViewById(R.id.employee_name_value_tv);
            jobClass = itemView.findViewById(R.id.employee_job_class_value_tv);
            chargePerHour = itemView.findViewById(R.id.charge_per_hr_value_tv);
            hoursBilled = itemView.findViewById(R.id.hours_billed_value_tv);
        }
    }
}
