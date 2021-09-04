package com.xuecheng.rabbitmq.config;

/**
 * 打印 Zi...0，因为如果从子类往上开始找，一旦发现执行的方法对应的子类有重写，必走子类.
 * @author liuqiang
 * @since 2021-09-02
 */
public class Demo2 {
    public static void main(String[] args) {
        Zi zi = new Zi();
    }
}

class Fu {
    int x = 1;

    Fu() {
        print();
    }

    public void print() {
        System.out.println("Fu..." + x);
    }
}

class Zi extends Fu {
    int x = 20;

    Zi() {
    }

    public void print() {
        System.out.println("Zi..." + x);
    }
}