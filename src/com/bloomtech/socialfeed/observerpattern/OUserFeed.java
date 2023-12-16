package com.bloomtech.socialfeed.observerpattern;

import com.bloomtech.socialfeed.App;
import com.bloomtech.socialfeed.models.Post;
import com.bloomtech.socialfeed.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Implement Observer Pattern
public class OUserFeed implements Observer {
    private User user;
    private List<Post> feed = new ArrayList<>();
    private SourceFeed sourceFeed;

    public OUserFeed(User user, SourceFeed sf) {
        this.user = user;
        //TODO: update OUserFeed in constructor after implementing observer pattern
        this.sourceFeed = sf;
    }

    public User getUser() {
        return user;
    }

    public List<Post> getFeed() {
        return feed;
    }

    @Override
    public void update() {
        if (sourceFeed != null) {
            List<Post> allPosts = sourceFeed.getPosts();
            feed = allPosts.stream()
                    .filter(post -> user.getFollowing().contains(post.getUsername()))
                    .collect(Collectors.toList());
        }
    }
}
