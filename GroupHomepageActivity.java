package com.example.weatherapplication20;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GroupHomepageActivity extends AppCompatActivity {

    private static final int PICK_MEDIA_REQUEST = 1;

    private RecyclerView postsRecyclerView;
    private PostAdapter postAdapter;
    private List<Post> posts;
    private PostManager postManager;

    private EditText postContent;
    private ImageView addMedia;
    private Button publishPost;

    private Uri mediaUri = null; // Media selected by the user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_homepage);

        String groupId = "save_the_planet"; // Example group ID

        // Initialize views
        postsRecyclerView = findViewById(R.id.postsRecyclerView);
        postContent = findViewById(R.id.postContent);
        addMedia = findViewById(R.id.addMedia);
        publishPost = findViewById(R.id.publishPost);

        // Initialize RecyclerView
        posts = new ArrayList<>();
        postAdapter = new PostAdapter(this, posts);
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(postAdapter);

        // Initialize PostManager
        postManager = new PostManager();

        // Add media functionality
        addMedia.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/* video/*");
            startActivityForResult(intent, PICK_MEDIA_REQUEST);
        });

        // Publish post functionality
        publishPost.setOnClickListener(v -> {
            String content = postContent.getText().toString().trim();

            if (content.isEmpty() && mediaUri == null) {
                Toast.makeText(this, "Post content or media is required", Toast.LENGTH_SHORT).show();
                return;
            }

            postManager.createPost(groupId, content, mediaUri, new PostManager.PostCreationCallback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(GroupHomepageActivity.this, "Post published successfully!", Toast.LENGTH_SHORT).show();
                    postContent.setText("");
                    mediaUri = null;
                    fetchPosts(groupId); // Refresh posts
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(GroupHomepageActivity.this, "Failed to publish post: " + errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("GroupHomepageActivity", "Post creation error: " + errorMessage);
                }
            });
        });

        // Fetch posts
        fetchPosts(groupId);
    }

    private void fetchPosts(String groupId) {
        postManager.fetchPosts(groupId, new PostManager.PostsFetchCallback() {
            @Override
            public void onSuccess(List<Post> fetchedPosts) {
                posts.clear();
                posts.addAll(fetchedPosts);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(GroupHomepageActivity.this, "Failed to fetch posts: " + errorMessage, Toast.LENGTH_SHORT).show();
                Log.e("GroupHomepageActivity", "Post fetch error: " + errorMessage);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_MEDIA_REQUEST && resultCode == RESULT_OK && data != null) {
            mediaUri = data.getData();
            Toast.makeText(this, "Media selected!", Toast.LENGTH_SHORT).show();
        }
    }
}
