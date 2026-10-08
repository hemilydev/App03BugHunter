package com.hemily.app03_bughunter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hemily.app03_bughunter.databinding.ItemBugBinding;

import java.util.List;

public class BugAdapter extends RecyclerView.Adapter<BugViewHolder> {

    private final List<Bug> bugs;
    private final OnBugClickListener listener;
    private final OnBugLongClickListener longListener;

    public BugAdapter(List<Bug> bugs, OnBugClickListener listener, OnBugLongClickListener longListener) {
        this.bugs = bugs;
        this.listener = listener;
        this.longListener = longListener;
    }

    public interface OnBugClickListener {
        void onBugClick(Bug bug);
    }

    public interface OnBugLongClickListener {
        void onBugLongClick(Bug bug);
    }

    @NonNull
    @Override
    public BugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBugBinding binding = ItemBugBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new BugViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BugViewHolder holder, int position) {
        Bug bug = bugs.get(position);
        holder.bind(bug, listener, longListener);
    }

    @Override
    public int getItemCount() {
        return bugs.size();
    }
}