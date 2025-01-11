package com.example.weatherapplication20;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private final List<Group> groupList;
    private final Context context;

    // Constructor to pass context and group list
    public GroupAdapter(Context context, List<Group> groupList) {
        this.context = context;
        this.groupList = groupList;
    }

    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getName());
        holder.activeUsers.setText("Active Users: " + group.getActiveUsers());
        holder.groupLogo.setImageResource(group.getLogoResId());

        // Set OnClickListener for the card
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupHomepageActivity.class);
            intent.putExtra("GROUP_NAME", group.getName()); // Pass the group name to the next activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {
        TextView groupName, activeUsers;
        ImageView groupLogo;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.groupName);
            activeUsers = itemView.findViewById(R.id.activeUsers);
            groupLogo = itemView.findViewById(R.id.groupLogo);
        }
    }
}
