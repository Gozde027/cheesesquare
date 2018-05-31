/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.support.android.designlibdemo.launcher.CheeseApiLauncher;
import com.support.android.designlibdemo.launcher.ErrorHandler;
import com.support.android.designlibdemo.launcher.SuccessHandler;

import java.util.List;

import static android.view.View.GONE;

public class CheeseListFragment extends Fragment {

    private String LOG = "CheeseListFragment";

    private RecyclerView rv;
    private View noDataView;

    private CheeseData cheeseData;

    private void log(String message){
        Log.i(LOG,message);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            cheeseData = (CheeseData) savedInstanceState.getSerializable("OUTPUT");
        }
        log("onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("OUTPUT", cheeseData);
        log("onSaveInstanceState");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cheese_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView(view);
    }

    private void setupRecyclerView(final View view) {
        rv = view.findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));

        noDataView = view.findViewById(R.id.noData);

        decideView();
    }

    private void decideView(){

        if(cheeseData == null){
            rv.setAdapter(new ProgressViewAdapter());
            callApi();
        }else if(cheeseData.isHasCheese()){
            fillRecyclerViewWithCheese();
        }else{
            showNoData();
        }
    }

    private void callApi(){
        CheeseApiLauncher launcher = new CheeseApiLauncher(new SuccessHandler() {
            @Override
            public void onSuccess(CheeseData cheeseData) {
                CheeseListFragment.this.cheeseData = cheeseData;
                fillRecyclerViewWithCheese();
            }
        }, new ErrorHandler() {
            @Override
            public void onError() {
                CheeseListFragment.this.cheeseData = new CheeseData();
                showNoData();
            }
        });
        launcher.launch();
    }

    private void fillRecyclerViewWithCheese(){
        rv.setAdapter(new SimpleStringRecyclerViewAdapter(getActivity(), cheeseData.cheeseList));
    }

    private void showNoData(){
        rv.setVisibility(GONE);
        noDataView.setVisibility(View.VISIBLE);
    }

    public static class SimpleStringRecyclerViewAdapter extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<Cheese> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public Cheese mBoundItem;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public Cheese getValueAt(int position) {
            return mValues.get(position);
        }

        public SimpleStringRecyclerViewAdapter(Context context, List<Cheese> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mBoundItem = mValues.get(position);
            holder.mTextView.setText(holder.mBoundItem.getName());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, CheeseDetailActivity.class);
                    intent.putExtra(CheeseDetailActivity.EXTRA_CHEESE, holder.mBoundItem);

                    context.startActivity(intent);
                }
            });

            Glide.with(holder.mImageView.getContext())
                    .load(holder.mBoundItem.getDrawableResId())
                    .fitCenter()
                    .into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }
    }
}
