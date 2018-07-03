package cn.share.jack.module2.proxy.static_proxy;

/**
 * Created by jack on 2018/3/26
 * 我们——被代理对象
 */

public class Man implements IBank {

    private String name;

    public Man(String name) {
        this.name = name;
    }

    @Override
    public void applyBank() {
        System.out.println(name + "：申请办卡");
    }
}