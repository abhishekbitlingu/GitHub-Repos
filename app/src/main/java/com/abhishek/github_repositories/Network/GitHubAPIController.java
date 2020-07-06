package com.abhishek.github_repositories.Network;

import android.util.Log;

import androidx.annotation.NonNull;

import com.abhishek.github_repositories.AppConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubAPIController implements Callback<GitHubResponseModel> {
    private static final String TAG = GitHubAPIController.class.getName();
    public GitHubAPIResponseListener listener;

    public GitHubAPIController(GitHubAPIResponseListener listener) {
        this.listener = listener;
    }
    public void loadRepositories(Long page) {
        Log.d(TAG, "loadRepositories: page = " + page);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GitHubAPI gitHubAPI = retrofit.create(GitHubAPI.class);

        Call<GitHubResponseModel> call = gitHubAPI.loadRepositories(AppConstants.QUERY_PARAM_VALUE,
                AppConstants.PAGE_MAX_SIZE, AppConstants.QUERY_SORT_VALUE, AppConstants.QUERY_ORDER_VALUE, page);
        call.enqueue(this);
    }

    @Override
    public void onResponse(@NonNull Call<GitHubResponseModel> call, Response<GitHubResponseModel> response) {
        if(response.isSuccessful()) {
            Log.d(TAG, "onResponse: " + response.body());
            if (response.body() != null) {
                GitHubResponseModel responseModel = response.body();
                if (!responseModel.getRepositories().isEmpty()) {
                    listener.onSuccessResponse(responseModel.getRepositories(), responseModel.getTotalCount());
                }
            }
        } else {
            Log.d(TAG, "onResponse Error body: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(@NonNull Call<GitHubResponseModel> call, Throwable t) {
        t.printStackTrace();
        listener.onErrorResponse();
    }
}