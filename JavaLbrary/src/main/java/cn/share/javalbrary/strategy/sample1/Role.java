package cn.share.javalbrary.strategy.sample1;

/**
 * Created by jack on 2018/6/6
 */
public abstract class Role {

    protected String name;

    protected abstract void display();

    protected abstract void run();

    protected abstract void attack();

    protected abstract void defend();

}