package com.example.weatherapplication20;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private final Context context;
    private final List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        // Set user name and content
        holder.userName.setText("Place_Holder");  // Placeholder for user name
        holder.postContent.setText(post.getContent());

        // Load user avatar (you can replace it with actual user image)
        Glide.with(context)
                .load(R.drawable.ic_user_placeholder)  // Placeholder avatar
                .into(holder.userAvatar);

        // Load post media if available
        if (post.getMediaUrl() != null) {
            holder.postMedia.setVisibility(View.VISIBLE);
            Glide.with(context).load(post.getMediaUrl()).into(holder.postMedia);
        } else {
            holder.postMedia.setVisibility(View.GONE);
        }

        // Set up comments RecyclerView with the CommentsAdapter
        holder.commentsRecyclerView.setAdapter(new CommentsAdapter(post.getComments()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView userName, postContent;
        ImageView userAvatar, postMedia;
        RecyclerView commentsRecyclerView;  // Add RecyclerView for comments

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the views
            userName = itemView.findViewById(R.id.userName);
            postContent = itemView.findViewById(R.id.postContent);
            userAvatar = itemView.findViewById(R.id.userAvatar);
            postMedia = itemView.findViewById(R.id.postMedia);
            commentsRecyclerView = itemView.findViewById(R.id.commentsRecyclerView);
        }
    }
}
