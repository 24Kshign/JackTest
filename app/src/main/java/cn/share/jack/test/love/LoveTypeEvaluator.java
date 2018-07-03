package cn.share.jack.test.love;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by jack on 2018/2/6
 */

public class LoveTypeEvaluator implements TypeEvaluator<PointF> {

    public PointF point1;
    public PointF point2;

    public LoveTypeEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        //t是[0-1]的范围
        PointF pointF = new PointF();
        pointF.x = point0.x * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.x * t * (1 - t) * (1 - t)
                + 3 * point2.x * t * t * (1 - t)
                + point3.x * t * t * t;

        pointF.y = point0.y * (1 - t) * (1 - t) * (1 - t)
                + 3 * point1.y * t * (1 - t) * (1 - t)
                + 3 * point2.y * t * t * (1 - t)
                + point3.y * t * t * t;
        return pointF;
    }
}
