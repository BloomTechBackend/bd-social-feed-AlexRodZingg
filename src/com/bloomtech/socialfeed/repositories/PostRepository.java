package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.models.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepository {
    private static final String POST_DATA_PATH = "src/resources/PostData.json";

    public PostRepository() {
    }

    public List<Post> getAllPosts() {
        //TODO: return all posts from the PostData.json file
        List<Post> allPosts = new ArrayList<>();
        try {
            String json = new String(Files.readAllBytes(Paths.get(POST_DATA_PATH)));
            if (json == null || json.isEmpty()) {
                return new ArrayList<>();
            }
            Gson gson = new Gson();
            Type postType = new TypeToken<ArrayList<Post>>(){}.getType();
            allPosts = gson.fromJson(json, postType);
            if (allPosts == null) {
                return new ArrayList<>();
            }
            return allPosts;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Post> findByUsername(String username) {
        return getAllPosts()
                .stream()
                .filter(p -> p.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<Post> addPost(Post post) {
        List<Post> allPosts = getAllPosts();
        allPosts.add(post);

        //TODO: Write the new Post data to the PostData.json file
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(allPosts);
        try {
            Files.write(Paths.get(POST_DATA_PATH), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //TODO: Return an updated list of all posts
        return allPosts;
    }
}
