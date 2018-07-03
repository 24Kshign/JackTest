package cn.share.jack.module2.proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by jack on 2018/3/26
 * 银行办理业务-动态代理-InvocationHandler
 */

public class BankInvocationHandler implements InvocationHandler {

    private Object mObject;

    public BankInvocationHandler(Object object) {
        this.mObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //执行方法，目标接口调用的方法都会来到这里
        System.out.println("name--->" + method.getName());
        //调用被代理对象的方法，这里其实调用的就是man里面的applyBank方法
        Object voidObject = method.invoke(mObject, args);
        return voidObject;
    }
}
