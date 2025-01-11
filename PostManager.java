package com.example.weatherapplication20;

import android.net.Uri;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class PostManager {

    private final FirebaseFirestore firestore;

    public PostManager() {
        firestore = FirebaseFirestore.getInstance();
    }

    public interface PostCreationCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public interface PostsFetchCallback {
        void onSuccess(List<Post> posts);
        void onFailure(String errorMessage);
    }

    public void createPost(String groupId, String content, Uri mediaUri, PostCreationCallback callback) {
        String mediaUrl = mediaUri != null ? mediaUri.toString() : null;

        Post post = new Post(groupId, content, mediaUrl, System.currentTimeMillis());

        firestore.collection("groups")
                .document(groupId)
                .collection("posts")
                .add(post)
                .addOnSuccessListener(documentReference -> {
                    if (callback != null) callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    public void fetchPosts(String groupId, PostsFetchCallback callback) {
        firestore.collection("groups")
                .document(groupId)
                .collection("posts")
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Post> posts = new ArrayList<>();
                    queryDocumentSnapshots.forEach(snapshot -> posts.add(snapshot.toObject(Post.class)));
                    if (callback != null) callback.onSuccess(posts);
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }
}
