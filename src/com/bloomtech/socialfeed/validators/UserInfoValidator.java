package com.bloomtech.socialfeed.validators;

import com.bloomtech.socialfeed.exceptions.UserValidationException;
import com.bloomtech.socialfeed.models.Role;
import com.bloomtech.socialfeed.models.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoValidator implements Validator {

    private boolean isValidUsername(String username) {
        //TODO: validate username begins with an uppercase letter, is at least 4 characters long, and only contains
        //letters, numbers, and underscores
        String userRegex = "[A-Z][A-Za-z0-9_]{3,}";
        Pattern userPattern = Pattern.compile(userRegex);
        Matcher userMatcher = userPattern.matcher(username);

        return userMatcher.matches();
    }

    private boolean isValidPassword(String password) {
        //TODO: validate password contains at least 8 characters, an uppercase, a lowercase letter, and a number.
        //valid symbols include: !@#$%^&*
        String passwordGeneralRegex = "(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9!@#\\$\\%\\^&\\*]{8,}";

        Pattern passwordGeneralPattern = Pattern.compile(passwordGeneralRegex);

        Matcher passwordGeneralMatcher = passwordGeneralPattern.matcher(password);

        return passwordGeneralMatcher.matches();
    }

    @Override
    public void validate(Object userData) {

        User user = (User) userData;

        if (!isValidUsername(user.getUsername())) {
            throw new UserValidationException("Invalid Username: Username must be at least 4 characters long, " +
                    "must begin with an uppercase letter, and may not contain special characters or spaces!");
        }
        if (!isValidPassword(user.getPassword())) {
            throw new UserValidationException("Invalid Password: Password must be at least 8 characters long, " +
                    "contain at least one uppercase letter, one lowercase letter, and one special character!");
        }
        if (user.getRole() == null) { user.setRole(Role.USER); }
    }
}
