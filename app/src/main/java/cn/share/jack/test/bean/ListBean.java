package cn.share.jack.test.bean;

/**
 * Created by jack on 2018/4/13
 */

public class ListBean {

    private int imageRes;
    private String name;

    public ListBean(int imageRes, String name) {
        this.imageRes = imageRes;
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
