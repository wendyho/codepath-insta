package com.example.insta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        post = (Post) getIntent().getParcelableExtra("post");
        try {
            tvHandleDetail.setText(post.getUser().fetchIfNeeded().getUsername());
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        }
        ParseFile image = post.getImage();
       if(image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImageDetail);
        }
        tvDescriptionDetail.setText(post.getUser().getUsername() + " " + post.getDescription());
       tvTimestamp.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
    }
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
