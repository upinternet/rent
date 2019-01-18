package com.hyh.ease.rent;

public class Son extends Father{
    int a = 4;
    String b = "hello world";

    void print()
    {
        super.print();
        System.out.println("Father :a = " + a + " b = " + b);
    }

    public static void main(String[] args) {
        Father f = new Father();
        f.print();
        System.out.println("-----------------");
        Father s = new Son();
        s.print();

        System.out.println("-----------------");
        Son ss = new Son();
        ss.print();
    }
}
