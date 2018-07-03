package cn.share.jack.module2.proxy.dynamic_proxy;

import java.lang.reflect.Proxy;

import cn.share.jack.module2.proxy.static_proxy.IBank;
import cn.share.jack.module2.proxy.static_proxy.Man;

/**
 * Created by jack on 2018/3/26
 */

public class Client {

    public static void main(String[] args) {
        Man man = new Man("24K纯帅");
//        BankWorker bankWorker = new BankWorker(man);
//        bankWorker.applyBank();
        IBank iBank =
                //返回的是一个IBank的实例对象，这个对象是由Java给我们创建
                (IBank) Proxy.newProxyInstance(
                IBank.class.getClassLoader()  //ClassLoader类加载器
                , new Class<?>[]{IBank.class} //目标接口(interface)
                , new BankInvocationHandler(man)
        );
        //调用这个方法会来到BankInvocationHandler的invoke方法
        iBank.applyBank();
    }
}
