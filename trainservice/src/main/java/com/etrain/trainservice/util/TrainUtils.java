package com.etrain.trainservice.util;

public class TrainUtils {
    public static String generatePnrNumber() {
        return String.valueOf(System.currentTimeMillis()).substring(3);
    }
}
