package com.dbms.project.karmdhan.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.DB.EmployeeOperations;
import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class ViewAllProjectRvAdapter extends RecyclerView.Adapter<ViewAllProjectRvAdapter.ProjectViewHolder> {
    private final Context context;
    private final List<Project> projectList;
    private EmployeeOperations employeeOperations;

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
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class ProjectViewHolder extends RecyclerView.ViewHolder {
        private final TextView projectNumber;
        private final TextView projectName;
        private final TextView projectLeader;

        public ProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNumber = itemView.findViewById(R.id.project_number_value_tv);
            projectName = itemView.findViewById(R.id.project_name_value_tv);
            projectLeader = itemView.findViewById(R.id.project_leader_value_tv);
        }
    }
}
