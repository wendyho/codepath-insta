package com.example.insta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.io.Serializable;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        viewHolder.bind(post);

    }

    // Clean all elements of the recycler
    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvHandle;
        private ImageView ivImage;
        private TextView tvDescription;
        private ImageView ivProfileSmall;
        private ImageButton btnLike;
        private TextView tvNumLike;

        public ViewHolder(View itemView){
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivProfileSmall = itemView.findViewById(R.id.ivProfileSmall);
            btnLike = itemView.findViewById(R.id.btnLike);
            tvNumLike = itemView.findViewById(R.id.tvNumLike);
            itemView.setOnClickListener(this);

        }

        public void bind(final Post post) {
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivImage);
            }
            tvDescription.setText(post.getUser().getUsername() + " " + post.getDescription());
            tvNumLike.setText(Integer.toString(post.getNumLikes()));
            if(post.isLiked()) {
                btnLike.setImageResource(R.drawable.ufi_heart_active);
                btnLike.setColorFilter(Color.argb(255,194, 12, 36));
            }
            else{
                btnLike.setImageResource(R.drawable.ufi_heart);
                btnLike.setColorFilter(Color.argb(255, 0,0,0));
            }

            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!post.isLiked()) {
                        //like
                        post.like();
                        btnLike.setImageResource(R.drawable.ufi_heart_active);
                        btnLike.setColorFilter(Color.argb(255,194, 12, 36));
                    }
                    else{
                        //unlike
                        post.unlike();
                        btnLike.setImageResource(R.drawable.ufi_heart);
                        btnLike.setColorFilter(Color.argb(255, 0,0,0));
                    }
                    post.saveInBackground();
                    tvNumLike.setText(Integer.toString(post.getNumLikes()));
                }
            });



        }

        @Override
        public void onClick(View v) {
            //gets item position
            int position = getAdapterPosition();
            // make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position
                Post post = posts.get(position);
                // create intent for new activity
                // create intent for new activity
                Intent intent = new Intent(context, PostDetail.class);
                // serialize the movie using parceler, use its short name as a key
               intent.putExtra("post", post);
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
