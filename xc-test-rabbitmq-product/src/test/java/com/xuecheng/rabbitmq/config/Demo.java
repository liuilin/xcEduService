package com.xuecheng.rabbitmq.config;

/**
 * 父类静态
 * 子类静态
 * C 类静态
 * C 类构造方法
 * 父类构造方法
 * C 类构造方法
 * 子类构造代码块
 * 子类成员方法
 * 子类构造方法
 *
 * @author liuqiang
 * @since 2021-09-02
 */
public class Demo {
    public static void main(String[] args) {
        B b = new B();
    }
}

class B extends A {
    static {
        System.out.println("子类静态");
    }

    C c = new C();

    {
        System.out.println("子类构造代码块");
    }

    B() {
        method();
        System.out.println("子类构造方法");
    }

    private void method() {
        System.out.println("子类成员方法");
    }
}

class A {
    static {
        System.out.println("父类静态");
    }

    C c = new C();

    A() {
        System.out.println("父类构造方法");
    }
}

class C {
    static {
        System.out.println("C 类静态");
    }

    C() {
        System.out.println("C 类构造方法");
    }

    public static void method() {
        System.out.println("C 类静态方法");
    }
}
