package com.examples.restapp.ui;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.examples.restapp.model.PostModel;
import com.examples.restapp.data.PostClint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostViewModel extends ViewModel {
    MutableLiveData<List<PostModel>> liveData=new MutableLiveData<>();

    public void getPosts(){
        PostClint.getINSTENCE().getPost().enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                Log.i("err",t.getMessage());
            }
        });
    }
}
