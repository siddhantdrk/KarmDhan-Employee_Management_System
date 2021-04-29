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

public class ViewAllEmployeeRvAdapter extends RecyclerView.Adapter<ViewAllEmployeeRvAdapter.EmployeeViewHolder> {
    private final List<Employee> employeeList;
    private final Context context;

    public ViewAllEmployeeRvAdapter(List<Employee> employeeList, Context context) {
        this.employeeList = employeeList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_all_employee_rv_item, parent, false);
        return new ViewAllEmployeeRvAdapter.EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.employeeName.setText(employeeList.get(position).getEmployeeName());
        holder.employeeNumber.setText(String.valueOf(employeeList.get(position).getEmployeeNumber()));
        holder.employeeJobClass.setText(employeeList.get(position).getEmployeeJobClass());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView employeeNumber;
        private final TextView employeeName;
        private final TextView employeeJobClass;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employee_name_value_tv);
            employeeNumber = itemView.findViewById(R.id.employee_number_value_tv);
            employeeJobClass = itemView.findViewById(R.id.employee_job_class_value_tv);
        }
    }
}
