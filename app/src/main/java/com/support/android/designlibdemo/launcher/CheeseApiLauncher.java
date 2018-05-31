package com.support.android.designlibdemo.launcher;

import android.os.AsyncTask;

import com.support.android.designlibdemo.Cheese;
import com.support.android.designlibdemo.CheeseData;

import java.util.List;

/**
 * Created by Gozde Kaval on 5/31/2018.
 */

public class CheeseApiLauncher {

    private SuccessHandler successHandler;
    private ErrorHandler errorHandler;

    public CheeseApiLauncher(final SuccessHandler successHandler, final ErrorHandler errorHandler){
        this.successHandler = successHandler;
        this.errorHandler = errorHandler;
    }

    public void launch(){
        callApi();
    }

    private void callApi(){
        CheeseApiAsyncTask task = new CheeseApiAsyncTask(new ConnectionResponseListener() {
            @Override
            public void executeSuccess(List<Cheese> result) {
                successHandler.onSuccess(new CheeseData(result));
            }

            @Override
            public void executeError() {
                errorHandler.onError();
            }
        });
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
