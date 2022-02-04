package com.examples.restapp.data;

import com.examples.restapp.model.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostClint {

   // https://jsonplaceholder.typicode.com/
    private static final String baseUrl="https://picsum.photos/v2/";
    private Postinterfase postinterfase;
    private static PostClint INSTENCE;

    public PostClint(){
        Retrofit retrofit= new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();
       postinterfase=retrofit.create(Postinterfase.class);
    }

    public static PostClint getINSTENCE(){
        if (INSTENCE==null){
            INSTENCE =new PostClint();
        }
        return INSTENCE;
    }
    public Call<List<PostModel>> getPost(){
        return postinterfase.getPost();
    }
}
