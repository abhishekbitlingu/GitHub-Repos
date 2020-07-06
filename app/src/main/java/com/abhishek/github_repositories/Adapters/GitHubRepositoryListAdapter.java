package com.abhishek.github_repositories.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.github_repositories.DataModel.GitHubRepositoryModel;
import com.abhishek.github_repositories.R;
import com.abhishek.github_repositories.Utils.AppUtils;
import com.abhishek.github_repositories.custom.recyclerview.RecyclerLayoutClickListener;
import com.abhishek.github_repositories.databinding.ListItemRepoBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GitHubRepositoryListAdapter extends RecyclerView.Adapter<GitHubRepositoryListAdapter.CustomViewHolder> {
    private Context context;
    private List<GitHubRepositoryModel> items;
    private RecyclerLayoutClickListener listener;

    public GitHubRepositoryListAdapter(Context context, RecyclerLayoutClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.items = new ArrayList<>();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ListItemRepoBinding itemBinding = ListItemRepoBinding.inflate(layoutInflater, parent, false);
            CustomViewHolder customItemViewHolder = new CustomViewHolder(itemBinding);
            itemBinding.cardView.setOnClickListener(v -> customItemViewHolder.onLayoutButtonClick());
            itemBinding.btnShare.setOnClickListener(view -> customItemViewHolder.onShareButtonClick());
            return customItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    public void setItems(List<GitHubRepositoryModel> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public GitHubRepositoryModel getItem(int position) {
        return items.get(position);
    }

    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private ListItemRepoBinding binding;
        public CustomViewHolder(ListItemRepoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindTo(GitHubRepositoryModel githubEntity) {
            Picasso.get().load(githubEntity.getOwner().getAvatarUrl())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.itemProfileImg);

            binding.itemTitle.setText(githubEntity.getFullName());
            binding.itemTime.setText(String.format(context.getString(R.string.item_date),
                    AppUtils.getDate(githubEntity.getCreatedAt()),
                    AppUtils.getTime(githubEntity.getCreatedAt())));

            binding.itemDesc.setText(githubEntity.getDescription());

            if(githubEntity.getLanguage() != null) {
                binding.itemImgLanguage.setVisibility(View.VISIBLE);
                binding.itemLikes.setVisibility(View.VISIBLE);
                binding.itemLikes.setText(githubEntity.getLanguage());

                GradientDrawable drawable = (GradientDrawable) context.getResources().getDrawable(R.drawable.ic_circle);
                drawable.setColor(AppUtils.getColorByLanguage(context, githubEntity.getLanguage()));
                binding.itemImgLanguage.setImageDrawable(drawable);

            } else {
                binding.itemLikes.setVisibility(View.GONE);
                binding.itemImgLanguage.setVisibility(View.GONE);
            }

        }

        private void onLayoutButtonClick() {
            listener.redirectToDetailScreen(binding.itemProfileImg, binding.itemTitle, binding.itemImgLanguage, binding.itemLikes, getItem(getLayoutPosition()));
        }

        private void onShareButtonClick() {
            listener.sharePost(getItem(getLayoutPosition()));
        }
    }
}
