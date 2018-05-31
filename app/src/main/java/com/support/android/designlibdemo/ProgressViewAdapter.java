package com.support.android.designlibdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class ProgressViewAdapter extends RecyclerView.Adapter<ProgressViewAdapter.ProgressViewHolder> {

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;

        private ProgressViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    @Override
    public ProgressViewAdapter.ProgressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
        return new ProgressViewAdapter.ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgressViewAdapter.ProgressViewHolder holder, int position) {
        holder.progressBar.setIndeterminate(true);
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
