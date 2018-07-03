package cn.share.javalbrary.strategy.sample2;

import cn.share.javalbrary.strategy.sample2.inter.IAttackBehavior;

/**
 * Created by jack on 2018/6/6
 */
public class AttackJYSG implements IAttackBehavior {
    @Override
    public void attack() {
        System.out.println("九阳神功");
    }
}