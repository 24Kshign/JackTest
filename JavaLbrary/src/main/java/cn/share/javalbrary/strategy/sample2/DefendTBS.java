package cn.share.javalbrary.strategy.sample2;

import cn.share.javalbrary.strategy.sample2.inter.IDefendBehavior;

/**
 * Created by jack on 2018/6/6
 */
public class DefendTBS implements IDefendBehavior {

    @Override
    public void defend() {
        System.out.println("铁布衫");
    }
}