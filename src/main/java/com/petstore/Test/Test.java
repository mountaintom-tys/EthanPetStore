package com.petstore.Test;

import java.util.ArrayList;
import java.util.List;

public class Test {
    int a;
    public static void main(String[] args) {
        List<Test> list= new ArrayList<>();
        Test test=new Test();
        test.a=1;
        list.add(test);
        test.a=2;
        System.out.println(list.toString());
    }
    public void out(){
        System.out.println(a);
    }
}
