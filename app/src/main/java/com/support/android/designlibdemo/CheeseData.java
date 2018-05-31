package com.support.android.designlibdemo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheeseData implements Serializable {

    final List<Cheese> cheeseList;
    final boolean hasCheese;

    public CheeseData(List<Cheese> cheeseList){
        this.cheeseList = cheeseList;
        this.hasCheese = true;
    }

    public CheeseData(){
        this.hasCheese = false;
        this.cheeseList = new ArrayList<>();
    }

    public List<Cheese> getCheeseList() {
        return cheeseList;
    }

    public boolean isHasCheese() {
        return hasCheese;
    }

}
