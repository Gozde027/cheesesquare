package com.support.android.designlibdemo.launcher;

import android.os.AsyncTask;

import com.support.android.designlibdemo.Cheese;
import com.support.android.designlibdemo.CheeseApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CheeseApiAsyncTask extends AsyncTask<Void,Void,List<Cheese>> {

    private ConnectionResponseListener listener;

    public CheeseApiAsyncTask(final ConnectionResponseListener listener){
        this.listener = listener;
    }

    @Override
    protected List<Cheese> doInBackground(Void... voids) {
        try {
            return CheeseApi.listCheeses(30);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Cheese> result) {
        super.onPostExecute(result);
        if(result.isEmpty()){
            listener.executeError();
        }else{
            listener.executeSuccess(result);
        }
    }
}
