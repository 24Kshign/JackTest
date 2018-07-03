package cn.share.jack.module2.proxy.static_proxy;

/**
 * Created by jack on 2018/3/26
 * 银行工作人员——代理对象
 *
 */

public class BankWorker implements IBank {

    private IBank iBank;

    /**
     * 代理对象持有被代理对象
     * @param iBank
     */
    public BankWorker(IBank iBank) {
        this.iBank=iBank;
    }

    @Override
    public void applyBank() {
        System.out.println("开始受理办卡");
        iBank.applyBank();
        System.out.println("办卡完成");
    }
}