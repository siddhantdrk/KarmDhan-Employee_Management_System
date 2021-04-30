package com.dbms.project.karmdhan.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbms.project.karmdhan.Model.Project;
import com.dbms.project.karmdhan.R;

import java.util.List;

public class EmployeeProjectRvAdapter extends RecyclerView.Adapter<EmployeeProjectRvAdapter.EmployeeProjectViewHolder> {
    private List<Project> projectList;
    private Context context;

    public EmployeeProjectRvAdapter(List<Project> projectList, Context context) {
        this.projectList = projectList;
        this.context = context;
    }

    @NonNull
    @Override
    public EmployeeProjectRvAdapter.EmployeeProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.employee_project_rv_item, parent, false);
        return new EmployeeProjectRvAdapter.EmployeeProjectViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeeProjectRvAdapter.EmployeeProjectViewHolder holder, int position) {
        holder.projectName.setText(projectList.get(position).getProjectName());
        holder.projectNumber.setText(String.valueOf(projectList.get(position).getProjectNumber()));
        holder.projectLeader.setText("Employee : " + projectList.get(position).getProjectLeaderEmployeeNumber() + "\n" + projectList.get(position).getProjectLeaderName());
        holder.hoursBilled.setText(projectList.get(position).getHoursBilled() + " Hrs");
        holder.chargePerHour.setText("$" + projectList.get(position).getChargePerHour());
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class EmployeeProjectViewHolder extends RecyclerView.ViewHolder {
        private TextView projectNumber;
        private TextView projectName;
        private TextView projectLeader;
        private TextView chargePerHour, hoursBilled;

        public EmployeeProjectViewHolder(@NonNull View itemView) {
            super(itemView);
            projectNumber = itemView.findViewById(R.id.project_number_value_tv);
            projectName = itemView.findViewById(R.id.project_name_value_tv);
            projectLeader = itemView.findViewById(R.id.project_leader_value_tv);
            chargePerHour = itemView.findViewById(R.id.charge_per_hr_value_tv);
            hoursBilled = itemView.findViewById(R.id.hours_billed_value_tv);
        }
    }
}
