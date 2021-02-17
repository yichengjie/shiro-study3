package com.yicj.study.test;

public class HelloWorld {

    static class A{
        public A(){
            System.out.println("AAAAAAAAAA");
        }
    }

    static class B extends A{
        public B(){
            System.out.println("BBBBBBBBB");
        }
        public void hello(){
            System.out.println("hello b");
        }
    }

    public static void main(String[] args) {
        B b = new B() ;
        b.hello();
    }
}
