package com.dbms.project.karmdhan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class ViewAllEmployeeRvAdapter extends RecyclerView.Adapter<ViewAllEmployeeRvAdapter.EmployeeViewHolder> {
    private final List<Employee> employeeList;
    private final Context context;
    private AdminOperations adminOperations;

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
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminOperations = new AdminOperations(context);
                removeEmployee(employeeList.get(position).getEmployeeNumber(), position);
            }
        });
    }

    private void updateEmployee() {
    }

    private void removeEmployee(int employeeNumber, int position) {
        if (adminOperations.removeEmployee(employeeNumber)) {
            Toast.makeText(context, "Employee Removed Successfully", Toast.LENGTH_SHORT).show();
            employeeList.remove(position);
            notifyItemRemoved(position);
        } else {
            Toast.makeText(context, "Oops !! something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final TextView employeeNumber;
        private final TextView employeeName;
        private final TextView employeeJobClass;
        private final Button updateBtn;
        private final Button removeBtn;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employee_name_value_tv);
            employeeNumber = itemView.findViewById(R.id.employee_number_value_tv);
            employeeJobClass = itemView.findViewById(R.id.employee_job_class_value_tv);
            updateBtn = itemView.findViewById(R.id.update_employee_btn);
            removeBtn = itemView.findViewById(R.id.remove_employee_btn);
        }
    }
}
