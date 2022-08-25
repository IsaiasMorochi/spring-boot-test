package com.imorochi;

import org.junit.jupiter.api.Test;

public class CoreApi {

    public static void main(String[] args) {
//        String a = "abc";
//        String b = a.toUpperCase();
//        b = b.replace("B", "2").replace("C", "3");
//        System.out.println(a);
//        System.out.println(b);

//        String alpha = "";
//        for (char current = 'a'; current <= 'z'; current++) {
//            alpha += current;
//        }
//        System.out.println(alpha);
//
//        StringBuilder alphab = new StringBuilder();
//        for (char current = 'a'; current <= 'z'; current++) {
//            alphab.append(current);
//        }
//        System.out.println(alpha);
//
//        StringBuilder alphab2 = new StringBuilder(); // soporta concurrency
//
        var sb = new StringBuilder("abcdef");
        sb.delete(1, 3);

    }
}
