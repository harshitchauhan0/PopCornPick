package com.harshit.popcornpick.Helper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    //    Executors are used to background work
//     we use this to make the calls for searching movies background
    private static AppExecutors instance;

    public static AppExecutors getInstance(){
        if(instance == null){
            return new AppExecutors();
        }
        return instance;
    }
    //    we have to search for movies without showing to user
//    we get data using from api in background thread
    private final ScheduledExecutorService mNetwork = Executors.newScheduledThreadPool(4);
    //    one for connecting to retrofit ,one for closing the retrofit
    public ScheduledExecutorService networkIo() {
        return mNetwork;
    }
}
