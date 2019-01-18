package com.hyh.ease.rent;

import com.hyh.ease.rent.entity.User;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Test {

    static ThreadLocal<User> s = new ThreadLocal<>();

    public static void main(String[] args) {
        test();


    }

    public static Unsafe getUnsafeInstance() throws Exception{
        Field unsafeStaticField =
                Unsafe.class.getDeclaredField("theUnsafe");
        unsafeStaticField.setAccessible(true);
        return (Unsafe) unsafeStaticField.get(Unsafe.class);
    }


    public static void test()
    {
        String s = "abc";

//保存s的引用
        s.intern();

//此时s1==s，地址相同
        String s1 = "abc";

        Unsafe u = null;
        try {
            u = getUnsafeInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

//获取s的实例变量value
        Field valueInString;
        try {
            valueInString = String.class.getDeclaredField("value");

//获取value的变量偏移值
            long offset = u.objectFieldOffset(valueInString);

//value本身是一个char[],要修改它元素的值，仍要获取baseOffset和indexScale
            long base = u.arrayBaseOffset(char[].class);

            long scale = u.arrayIndexScale(char[].class);

//获取value
            char[] values = (char[]) u.getObject(s, offset);

//为value赋值
            u.putChar(values, base + scale, 'c');

            System.out.println("s:"+s+" s1:"+s1);

//将s的值改为 abc
            s = "abc";

            String s2 = "abc";

            String s3 = "abc";

            System.out.println("s:"+s+" s1:"+s1);
            System.out.println(s == s1);
            System.out.println(s == s2);
            System.out.println(s2 == s3);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

    }
}
