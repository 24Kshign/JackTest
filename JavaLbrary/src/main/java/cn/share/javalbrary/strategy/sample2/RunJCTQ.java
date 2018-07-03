package cn.share.javalbrary.strategy.sample2;

import cn.share.javalbrary.strategy.sample2.inter.IRunBehavior;

/**
 * Created by jack on 2018/6/6
 */
public class RunJCTQ implements IRunBehavior {

    @Override
    public void run() {
        System.out.println("金蝉脱壳");
    }
}