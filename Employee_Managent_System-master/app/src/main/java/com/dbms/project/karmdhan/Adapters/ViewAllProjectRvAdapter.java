package com.dbms.project.karmdhan.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.Activity.Project.ProjectDetailsActivity;
import com.dbms.project.karmdhan.DB.AdminOperations;
import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class ViewAllProjectRvAdapter extends RecyclerView.Adapter<ViewAllProjectRvAdapter.ProjectViewHolder> {
    private Context context;
    private List<Project> projectList;
    private EmployeeOperations employeeOperations;
    private AdminOperations adminOperations;

    public ViewAllProjectRvAdapter(Context context, List<Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ViewAllProjectRvAdapter.ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_all_project_rv_item, parent, false);
        return new ViewAllProjectRvAdapter.ProjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllProjectRvAdapter.ProjectViewHolder holder, int position) {
        holder.projectName.setText(projectList.get(position).getProjectName());
        holder.projectNumber.setText(String.valueOf(projectList.get(position).getProjectNumber()));
        employeeOperations = new EmployeeOperations(context);
        holder.projectLeader.setText(employeeOperations.getEmployeeByNumber(projectList.get(position).getProjectLeaderEmployeeNumber()).getEmployeeName());
        holder.removeProjectBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure ? you want remove this Project.");
            builder.setPositiveButton("YES", (dialogInterface, i) -> {
                adminOperations = new AdminOperations(context);
                if (adminOperations.removeProject(projectList.get(position).getProjectNumber())) {
                    Toast.makeText(context, "Project Deleted Successfully", Toast.LENGTH_SHORT).show();
                    projectList.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Oops !! something went wrong.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("NO", (dialogInterface, i) -> dialogInterface.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        holder.viewProjectBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, ProjectDetailsActivity.class);
            intent.putExtra("ProjectNumber", projectList.get(position).getProjectNumber());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        private TextView projectNumber;
        private TextView projectName;
        private TextView projectLeader;
        private Button removeProjectBtn, viewProjectBtn;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNumber = itemView.findViewById(R.id.project_number_value_tv);
            projectName = itemView.findViewById(R.id.project_name_value_tv);
            projectLeader = itemView.findViewById(R.id.project_leader_value_tv);
            removeProjectBtn = itemView.findViewById(R.id.remove_project_btn);
            viewProjectBtn = itemView.findViewById(R.id.view_project_btn);
        }
    }
}
