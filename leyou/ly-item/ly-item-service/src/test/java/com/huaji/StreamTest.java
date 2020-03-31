package com.huaji;

import org.apache.commons.collections.bag.SynchronizedSortedBag;

import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        //定义一个流的数组
        Stream<String> stringStream = Stream.of("a", "f", "e", "r", "d");
        Stream<String> stream = stringStream.map(String::toUpperCase);
        stream.forEach((i)->{                   //s -> s.toUpperCase()
            System.out.println(i);
        });
//        System.out.println(stringStream1);
    }
}
