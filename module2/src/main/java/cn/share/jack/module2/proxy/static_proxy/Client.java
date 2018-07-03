package cn.share.jack.module2.proxy.static_proxy;

/**
 * Created by jack on 2018/3/26
 */

public class Client {

    public static void main(String[] args) {
        Man man = new Man("24K纯帅");
        BankWorker bankWorker = new BankWorker(man);
        bankWorker.applyBank();
    }
}
