package cn.share.javalbrary.strategy.sample1;

/**
 * Created by jack on 2018/6/6
 */
public class RoleA extends Role {

    public RoleA(String name) {
        this.name = name;
    }

    @Override
    protected void display() {
        System.out.println("样子A");
    }

    @Override
    protected void run() {
        System.out.println("金蝉脱壳");
    }

    @Override
    protected void attack() {
        System.out.println("降龙十八掌");
    }

    @Override
    protected void defend() {
        System.out.println("铁头功");
    }
}
