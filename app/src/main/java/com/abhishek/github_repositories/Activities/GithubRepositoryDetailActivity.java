package com.abhishek.github_repositories.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.abhishek.github_repositories.AppConstants;
import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.abhishek.github_repositories.R;
import com.abhishek.github_repositories.Utils.AppUtils;
import com.abhishek.github_repositories.Utils.NavigatorUtils;
import com.abhishek.github_repositories.Utils.ShareUtils;
import com.abhishek.github_repositories.databinding.ActivityGithubRepositoryDetailBinding;
import com.squareup.picasso.Picasso;

public class GithubRepositoryDetailActivity extends AppCompatActivity {

    private GitHubRepositoryModel githubRepositoryModel;
    private ActivityGithubRepositoryDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialiseView();
    }

    private void initialiseView() {
        binding = ActivityGithubRepositoryDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        githubRepositoryModel = getIntent().getParcelableExtra(AppConstants.INTENT_POST);

        Picasso.get().load(githubRepositoryModel.getOwner().getAvatarUrl())
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.itemProfileImg);

        binding.itemTitle.setText(githubRepositoryModel.getFullName());
        binding.itemStars.setText(String.valueOf(githubRepositoryModel.getStarsCount()));
        binding.itemWatchers.setText(String.valueOf(githubRepositoryModel.getWatchers()));
        binding.itemForks.setText(String.valueOf(githubRepositoryModel.getForks()));

        if(githubRepositoryModel.getLanguage() != null) {
            binding.itemImgLanguage.setVisibility(View.VISIBLE);
            binding.itemLanguage.setVisibility(View.VISIBLE);
            binding.itemLanguage.setText(githubRepositoryModel.getLanguage());
            updateColorTheme();

        } else {
            binding.itemImgLanguage.setVisibility(View.INVISIBLE);
            binding.itemLanguage.setVisibility(View.INVISIBLE);
        }

        binding.btnShare.setOnClickListener(v -> ShareUtils.shareUrl(GithubRepositoryDetailActivity.this, githubRepositoryModel.getHtmlUrl()));
        binding.btnVisit.setOnClickListener(v -> NavigatorUtils.openBrowser(GithubRepositoryDetailActivity.this, githubRepositoryModel.getHtmlUrl()));
    }


    private void updateColorTheme() {
        int bgColor = AppUtils.getColorByLanguage(getApplicationContext(), githubRepositoryModel.getLanguage());

        binding.appBarLayout.setBackgroundColor(bgColor);
        binding.mainToolbar.toolbar.setBackgroundColor(bgColor);
        binding.btnShare.setTextColor(bgColor);
        binding.itemImgLanguage.setImageDrawable(AppUtils.updateGradientDrawableColor(getApplicationContext(), bgColor));
        binding.btnVisit.setBackgroundDrawable(AppUtils.updateStateListDrawableColor(getResources().getDrawable(R.drawable.btn_visit), bgColor));
        binding.btnShare.setBackgroundDrawable(AppUtils.updateStateListDrawableStrokeColor(getResources().getDrawable(R.drawable.btn_share), bgColor));
        AppUtils.updateStatusBarColor(this, bgColor);
    }
}