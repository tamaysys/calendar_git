//package com.naserb.newcalendar.service;
//
//import UpdateUtils;
//import Utils;
//
//import androidx.work.Worker;
//
//public class UpdateWorker extends Worker {
//    @Override
//    public Worker.Result doWork() {
//        Utils.setChangeDateWorker();
//        Utils.updateStoredPreference(getApplicationContext());
//        UpdateUtils.update(getApplicationContext(), true);
//        return Result.SUCCESS;
//    }
//}