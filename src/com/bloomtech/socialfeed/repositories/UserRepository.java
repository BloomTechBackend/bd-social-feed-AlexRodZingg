package com.bloomtech.socialfeed.repositories;

import com.bloomtech.socialfeed.App;
import com.bloomtech.socialfeed.models.User;
import com.bloomtech.socialfeed.validators.UserInfoValidator;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserRepository {
    private static final String USER_DATA_PATH = "src/resources/UserData.json";

    private static final UserInfoValidator userInfoValidator = new UserInfoValidator();

    public UserRepository() {
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        //TODO: return parsed list of Users from UserData.json

        try {
            String json = new String(Files.readAllBytes(Paths.get(USER_DATA_PATH)));
            if (json == null || json.isEmpty()) {
                return new ArrayList<>();
            }
            Gson gson = new Gson();
            Type userType = new TypeToken<ArrayList<User>>(){}.getType();
            allUsers = gson.fromJson(json, userType);
            if (allUsers == null) {
                return new ArrayList<>();
            }
            return allUsers;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Optional<User> findByUsername(String username) {
        return getAllUsers()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public void save(User user) {
        List<User> allUsers = getAllUsers();

        if (allUsers != null) {
            Optional<User> existingUser = allUsers.stream()
                    .filter(u -> u.getUsername().equals(user.getUsername()))
                    .findFirst();

            if (!existingUser.isEmpty()) {
                throw new RuntimeException("User with name: " + user.getUsername() + " already exists!");
            }
        }

        allUsers.add(user);
        //TODO: Write allUsers to UserData.json
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json = gson.toJson(allUsers);

        try {
            Files.write(Paths.get(USER_DATA_PATH), json.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
