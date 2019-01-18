package com.hyh.ease.rent;

import java.util.HashMap;
import java.util.Map;

public class Father {

    int a = 2;
    double b = 3.0;
    final Map i = new HashMap();
    String ss = "sf";

    static void print1()
    {
        char aa = 'a';
        char bb = 'b';

        aa ^= bb;
        System.out.println(aa);
        bb ^= aa;
        System.out.println(bb);
        aa ^= bb;
        System.out.println(aa);
        System.out.println(bb);

    }

    void print()
    {}

        public static void main(String[] args) {
        print1();
    }
}
