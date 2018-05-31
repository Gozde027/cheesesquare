package com.support.android.designlibdemo.launcher;

import com.support.android.designlibdemo.Cheese;

import java.util.List;

public interface ConnectionResponseListener {
    void executeSuccess(List<Cheese> result);
    void executeError();
}
