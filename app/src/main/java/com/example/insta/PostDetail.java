package com.example.insta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

public class PostDetail extends AppCompatActivity {

    Post post;
    TextView tvHandleDetail;
    ImageView ivProfileDetailSmall;
    ImageView ivImageDetail;
    TextView tvDescriptionDetail;
    TextView tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        tvHandleDetail = (TextView) findViewById(R.id.tvHandleDetail);
        ivProfileDetailSmall = (ImageView) findViewById(R.id.ivProfileSmallDetail);
        ivImageDetail = (ImageView) findViewById(R.id.ivImageDetail);
        tvDescriptionDetail = (TextView) findViewById(R.id.tvDescriptionDetail);
        tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);

        // unwrap the post passed in via intent

        post = (Post) getIntent().getSerializableExtra("post");
        tvHandleDetail.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
       if(image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImageDetail);
        }
        tvDescriptionDetail.setText(post.getUser().getUsername() + " " + post.getDescription());

    }

}
