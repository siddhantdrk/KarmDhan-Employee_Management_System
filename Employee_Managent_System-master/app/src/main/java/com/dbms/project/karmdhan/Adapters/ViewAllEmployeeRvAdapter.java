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
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.ProjectOperations;
import com.dbms.project.karmdhan.Model.Employee;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class ViewAllEmployeeRvAdapter extends RecyclerView.Adapter<ViewAllEmployeeRvAdapter.EmployeeViewHolder> {
    private List<Employee> employeeList;
    private Context context;
    private AdminOperations adminOperations;
    private OnUpdateClickListener onUpdateClickListener;

    public ViewAllEmployeeRvAdapter(List<Employee> employeeList, Context context, OnUpdateClickListener onUpdateClickListener) {
        this.employeeList = employeeList;
        this.context = context;
        this.onUpdateClickListener = onUpdateClickListener;
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
        holder.removeBtn.setOnClickListener(view -> {
            List<Project> projectList = new ProjectOperations(context).EmployeeIsALeaderForProjects(employeeList.get(position).getEmployeeNumber());
            if (projectList == null || projectList.size() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure ? you want remove this employee.");
                builder.setPositiveButton("YES", (dialogInterface, i) -> {
                    adminOperations = new AdminOperations(context);
                    removeEmployee(employeeList.get(position).getEmployeeNumber(), position);
                });
                builder.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String projectString = "";
                for (Project project : projectList) {
                    projectString = projectString + project.getProjectNumber() + " : " + project.getProjectName() + "\n";
                }
                builder.setMessage("Sorry, you can't remove this employee as he is a leader for the following projects:\n" + projectString + "To remove employee First Change the  Leader of Above Projects.");
                builder.setPositiveButton("OKAY", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.show();
            }
        });
        holder.bind(employeeList.get(position), onUpdateClickListener);

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

    public interface OnUpdateClickListener {
        void onClick(Employee employee);
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private TextView employeeNumber;
        private TextView employeeName;
        private TextView employeeJobClass;
        private Button updateBtn;
        private Button removeBtn;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeName = itemView.findViewById(R.id.employee_name_value_tv);
            employeeNumber = itemView.findViewById(R.id.employee_number_value_tv);
            employeeJobClass = itemView.findViewById(R.id.employee_job_class_value_tv);
            updateBtn = itemView.findViewById(R.id.update_employee_btn);
            removeBtn = itemView.findViewById(R.id.remove_employee_btn);
        }

        public void bind(Employee employee, OnUpdateClickListener listener) {
            updateBtn.setOnClickListener(v -> listener.onClick(employee));
        }
    }
}
