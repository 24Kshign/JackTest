package cn.share.javalbrary.strategy.sample2;

import cn.share.javalbrary.strategy.sample2.inter.IAttackBehavior;
import cn.share.javalbrary.strategy.sample2.inter.IDefendBehavior;
import cn.share.javalbrary.strategy.sample2.inter.IDisplayBehavior;
import cn.share.javalbrary.strategy.sample2.inter.IRunBehavior;

/**
 * Created by jack on 2018/6/7
 */
public class Role {
    protected String name;

    protected IDefendBehavior defendBehavior;
    protected IDisplayBehavior displayBehavior;
    protected IRunBehavior runBehavior;
    protected IAttackBehavior attackBehavior;

    public Role setDisplayBehavior(IDisplayBehavior displayBehavior) {
        this.displayBehavior = displayBehavior;
        return this;
    }

    public Role setDefendBehavior(IDefendBehavior defendBehavior) {
        this.defendBehavior = defendBehavior;
        return this;
    }

    public Role setRunBehavior(IRunBehavior runBehavior) {
        this.runBehavior = runBehavior;
        return this;
    }

    public Role setAttackBehavior(IAttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
        return this;
    }

    protected void display() {
        displayBehavior.display();
    }

    protected void run() {
        runBehavior.run();
    }

    protected void defend() {
        defendBehavior.defend();
    }

    protected void attack() {
        attackBehavior.attack();
    }
}