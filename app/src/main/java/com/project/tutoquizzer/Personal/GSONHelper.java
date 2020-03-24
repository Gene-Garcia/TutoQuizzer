package com.project.tutoquizzer.Personal;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GSONHelper {
    public static void saveData(Context context, User user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("task list", json);
        editor.apply();
    }

    public static User loadData(Context context) {
        User user;

        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);

        if (user == null) {
            user = new User("Default", "Default", "Default");
        }

        return user;
    }

}
