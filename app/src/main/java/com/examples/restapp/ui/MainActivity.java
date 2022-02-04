package com.examples.restapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.examples.restapp.model.PostModel;
import com.examples.restapp.model.RecAdapter;
import com.examples.restapp.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static  String INTENT_IMG="intentImg";
    PostViewModel postViewModel;
    RecyclerView recyclerView;
    RecAdapter recAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);


        postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getPosts();

        recAdapter = new RecAdapter(this, new RecAdapter.OnClickItemListener() {
            @Override
            public void OnClickItem(String imgURl) {
                Intent intent =new Intent(getBaseContext(),ImageActivity.class);
                intent.putExtra(INTENT_IMG,imgURl);
                startActivity(intent);

            }
        });


        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(recAdapter);


        postViewModel.liveData.observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(List<PostModel> postModels) {

                recAdapter.setListItem(postModels);
            }
        });
    }
}