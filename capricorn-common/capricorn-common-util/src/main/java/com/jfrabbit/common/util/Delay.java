package com.jfrabbit.common.util;

import java.util.Scanner;

public class Delay {

    public static void sleep(int millisecond) {
        try {
            Thread.sleep(millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void suspend() {
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void scannerSuspend() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter any key to continue");
        scanner.next();
        scanner.close();
    }

}
