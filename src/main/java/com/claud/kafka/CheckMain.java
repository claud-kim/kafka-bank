package com.claud.kafka;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CheckMain {


    public static void main(String[] args) {


        List<String> inLists = FileUtil.readFile(AppConstants.IN_GEN);
        List<String> outLists = FileUtil.readFile(AppConstants.OUT_GEN);

        System.out.println("================================");
        System.out.println("env BOOTSTRAP_SERVERS=" + AppConstants.BOOTSTRAP_SERVERS);
        System.out.println("env TOPIC_NAME=" + AppConstants.TOPIC_NAME);
        System.out.println("env PRODUCER_NUM=" + AppConstants.PRODUCER_NUM);
        System.out.println("env GEN_USER_SIZE=" + AppConstants.GEN_USER_SIZE);
        System.out.println("env GEN_SIZE_PER_SECOND=" + AppConstants.GEN_SIZE_PER_SECOND);
        System.out.println("env CONSUMER_NUM_PER_THREAD=" + AppConstants.CONSUMER_NUM_PER_THREAD);
        System.out.println("env CONSUMER_UPDATE_NUM=" + AppConstants.CONSUMER_UPDATE_NUM);
        System.out.println("env INSERT_MONEY_MIN=" + AppConstants.INSERT_MONEY_MIN);
        System.out.println("env INSERT_MONEY_MAX=" + AppConstants.INSERT_MONEY_MAX);
        System.out.println("env IN_GEN=" + AppConstants.IN_GEN);
        System.out.println("env OUT_GEN=" + AppConstants.OUT_GEN);
        System.out.println("================================");


        if (inLists.toString().equals(outLists.toString())) {
            System.out.println("================================");
            System.out.println("GOOD");
            System.out.println("================================");
        }else
        {
            System.out.println("================================");
            System.out.println("BAD");
            System.out.println("================================");

            System.out.println("IN >>> ");
            System.out.println(inLists.toString());
            System.out.println("OUT >>> ");
            System.out.println(outLists.toString());
        }
    }
}
