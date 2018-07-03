package cn.share.jack.module2.ioc;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.share.jack.module2.NetWorkUtil;

/**
 * Created by jack on 2018/2/26
 */

public class ViewUtils {


    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法，Object——>反射需要执行的类
    private static void inject(ViewFinder viewFinder, Object object) {
        injectFiled(viewFinder, object);   //注入属性
        injectEvent(viewFinder, object);  //注入事件
    }

    private static void injectFiled(ViewFinder viewFinder, Object object) {
        //1、获取类里面的所有属性（利用反射去拿）
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();   //获取所有的属性（包括私有和公有）
        //2、获取ViewById里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (null != viewById) {
                //获取注解里面的id值
                int viewId = viewById.value();
                //3、findViewById找到view
                View view = viewFinder.findViewById(viewId);
                if (null != view) {
                    field.setAccessible(true);//能够注入所有修饰符（private，protected，public）
                    //4、动态注入找到的非空view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //1、获取类里面的所有方法（利用反射去拿）
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();   //获取所有的方法（包括私有和公有）
        //2、获取OnClick里面的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (null != onClick) {
                int[] values = onClick.value();
                for (int value : values) {
                    //3、findViewById找到view
                    boolean isCheckNet = null != method.getAnnotation(CheckNet.class);
                    View view = viewFinder.findViewById(value);
                    if (null != view) {
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            mMethod = method;
            mObject = object;
            mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //是否需要检测网络
            if (mIsCheckNet) {
                if (!NetWorkUtil.isNetWorkAvailable(v.getContext())) {
                    Toast.makeText(v.getContext(), "亲，您的网络不太给力~", Toast.LENGTH_SHORT).show();
                }
            }
            //点击会调用这个方法，利用反射执行方法
            try {
                mMethod.setAccessible(true);  //所有方法都可以注入
                mMethod.invoke(mObject, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}