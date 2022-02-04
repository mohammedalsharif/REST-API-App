package com.examples.restapp.data;

import com.examples.restapp.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Postinterfase {
         @GET("list")
        public Call<List<PostModel>>getPost();
}
