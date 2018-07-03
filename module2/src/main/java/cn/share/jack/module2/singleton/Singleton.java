package cn.share.jack.module2.singleton;

/**
 * Created by jack on 2018/3/19
 */

public class Singleton {

    private static Singleton INSTANCE = null;

    private Singleton() {
    }

    //饿汉单例模式
//    public static Singleton getInstance(){
//        return INSTANCE;
//    }


    //懒汉单例模式
//    public static synchronized Singleton getInstance(){
//        if (null == INSTANCE){
//            INSTANCE = new Singleton();
//        }
//        return INSTANCE;
//    }


    //DCL单例模式
//    public static Singleton getInstance() {
//        if (null == INSTANCE) {
//            synchronized (Singleton.class) {
//                if (null == INSTANCE) {
//                    INSTANCE = new Singleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }


    //静态内部类单例模式
    public static class SingletonInstance {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonInstance.INSTANCE;
    }
}
