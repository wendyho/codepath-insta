package com.example.insta.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.insta.LoginActivity;
import com.example.insta.Post;
import com.example.insta.ProfileAdapter;
import com.example.insta.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends PostsFragment {

    private ProfileAdapter adapter;
    private RecyclerView rvProfilePosts;
    private Button btnLogout;

    // onCreateView method is called when Fragment should create its view object hierarchy,
    // either dynamically or via XML layout inflation
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error with query");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(objects);
                adapter.notifyDataSetChanged();
                for(int i = 0; i < objects.size(); i++){
                    Log.d(TAG, "Post: " + objects.get(i).getDescription() +", username: " + objects.get(i).getUser().getUsername());

                }
            }
        });
    }

    // This event is triggered soon after onCreteView()
    // Any view setup should occur here
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rvProfilePosts = view.findViewById(R.id.ivProfileProfileUsername);


        // create the data source
        mPosts = new ArrayList<>();
        // create the adapter
        adapter = new ProfileAdapter(getContext(), mPosts);
        // set the adapter on the recycler view
        rvProfilePosts.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvProfilePosts.setLayoutManager(new GridLayoutManager(getContext(), 3));

        // set the button
        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);

            }
        });
        queryPosts();




    }
}
