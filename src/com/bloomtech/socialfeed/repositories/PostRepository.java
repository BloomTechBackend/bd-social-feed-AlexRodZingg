package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.helpers.LocalDateTimeAdapter;
import com.bloomtech.socialfeed.models.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostRepository {
    private static final String POST_DATA_PATH = "src/resources/PostData.json";

    public PostRepository() {
    }

    private Gson createGsonWithAdapter() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();
    }

    public List<Post> getAllPosts() {
        //TODO: return all posts from the PostData.json file
        List<Post> allPosts = new ArrayList<>();
        Gson gson = createGsonWithAdapter();

        try {
            String json = new String(Files.readAllBytes(Paths.get(POST_DATA_PATH)));
            if (json == null || json.isEmpty()) {
                return allPosts;
            }
            Type postType = new TypeToken<ArrayList<Post>>(){}.getType();
            allPosts = gson.fromJson(json, postType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return allPosts;
    }

    public List<Post> findByUsername(String username) {
        return getAllPosts()
                .stream()
                .filter(p -> p.getUsername().equals(username))
                .collect(Collectors.toList());
    }

    public List<Post> addPost(Post newPost) {
        List<Post> allPosts = getAllPosts();
        allPosts.add(newPost);

        //TODO: Write the new Post data to the PostData.json file
        Gson gson = createGsonWithAdapter();
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
