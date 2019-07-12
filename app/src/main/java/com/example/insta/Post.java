package com.example.insta;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;


@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LIKED_BY = "likedBy";

    public Post(){}

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser parseUser) {
        put(KEY_USER, parseUser);
    }

    public boolean isLiked() {
        JSONArray a = getLikedBy();

        for(int i = 0; i < a.length(); i++){
            try {
               if( a.getJSONObject(i).getString("objectId").equals(ParseUser.getCurrentUser().getObjectId())){
                   return true;
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public void like() {
        ParseUser u = ParseUser.getCurrentUser();
        add(KEY_LIKED_BY, u);
    }

    public void unlike() {
        ParseUser u = ParseUser.getCurrentUser();
        ArrayList<ParseUser> users = new ArrayList<>();
        users.add(u);
        removeAll(KEY_LIKED_BY, users);
    }

    public int getNumLikes() {
        return getLikedBy().length();
    }

    public JSONArray getLikedBy(){
        JSONArray a = getJSONArray(KEY_LIKED_BY);
        if(a == null){
            return new JSONArray();
        }
       return a;
    }
}
