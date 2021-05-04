package com.dbms.project.karmdhan.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.Model.ProjectEmployee;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class projectEmployeeOnlyRvAdapter extends RecyclerView.Adapter<projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder> {
    private List<Employee> employeeList;
    private Context context;
    private int projectNum;
    private ProjectOperations projectOperations;
    private Project project;
    private OnUpdateClickListener onUpdateClickListener;

    public projectEmployeeOnlyRvAdapter(List<Employee> employeeList, Context context, int projectNum, OnUpdateClickListener onUpdateClickListener) {
        this.employeeList = employeeList;
        this.context = context;
        this.projectNum = projectNum;
        projectOperations = new ProjectOperations(context);
        project = projectOperations.getProjectByNumber(projectNum);
        this.onUpdateClickListener = onUpdateClickListener;
    }

    @NonNull
    @Override
    public projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.project_employee_only_rv_item,parent,false);
        return new projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull projectEmployeeOnlyRvAdapter.ProjectEmployeeViewHolder holder, int position) {
        holder.employeeNumber.setText(String.valueOf(employeeList.get(position).getEmployeeNumber()));
        holder.employeeName.setText(employeeList.get(position).getEmployeeName());
        holder.jobClass.setText(employeeList.get(position).getEmployeeJobClass());
        holder.hoursBilled.setText(employeeList.get(position).getHoursBilled() + " Hrs");
        holder.chargePerHour.setText("$" + employeeList.get(position).getChargePerHour());
        holder.removeEmpBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure ? you want remove this Employee from this Project.");
            builder.setPositiveButton("YES", (dialogInterface, i) -> {
                if (project.getProjectLeaderEmployeeNumber() != employeeList.get(position).getEmployeeNumber()) {
                    if (projectOperations.removeEmployeeFromProject(projectNum, employeeList.get(position).getEmployeeNumber())) {
                        Toast.makeText(context, "Employee removed from Project successfully", Toast.LENGTH_SHORT).show();
                        employeeList.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, "Oops something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "You can't remove Leader. To do so First Change Leader !!", Toast.LENGTH_LONG).show();
                }
            });
            builder.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.bind(new ProjectEmployee(projectNum, employeeList.get(position).getEmployeeNumber(), employeeList.get(position).getChargePerHour(), employeeList.get(position).getHoursBilled()), onUpdateClickListener);
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public interface OnUpdateClickListener {
        void onClick(ProjectEmployee projectEmployee);
    }

    public class ProjectEmployeeViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeNumber;
        private TextView employeeName;
        private TextView jobClass;
        private TextView chargePerHour, hoursBilled;
        private Button removeEmpBtn, updateEmpBtn;
        private CardView cardView;

        public ProjectEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeNumber = itemView.findViewById(R.id.employee_number_value_tv);
            employeeName = itemView.findViewById(R.id.employee_name_value_tv);
            jobClass = itemView.findViewById(R.id.employee_job_class_value_tv);
            chargePerHour = itemView.findViewById(R.id.charge_per_hr_value_tv);
            hoursBilled = itemView.findViewById(R.id.hours_billed_value_tv);
            removeEmpBtn = itemView.findViewById(R.id.remove_project_employee_btn);
            updateEmpBtn = itemView.findViewById(R.id.update_project_employee_btn);
            cardView = itemView.findViewById(R.id.project_emp_only_cv);
        }

        public void bind(ProjectEmployee projectEmployee, projectEmployeeOnlyRvAdapter.OnUpdateClickListener listener) {
            updateEmpBtn.setOnClickListener(v -> listener.onClick(projectEmployee));
        }
    }
}
