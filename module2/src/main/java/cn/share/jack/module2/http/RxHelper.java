//package cn.share.jack.module2.http;
//
//import android.support.annotation.NonNull;
//
//import com.mifengkong.frtools.util.FRLog;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.ObservableSource;
//import io.reactivex.ObservableTransformer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Function;
//import io.reactivex.functions.Predicate;
//import io.reactivex.schedulers.Schedulers;
//import io.reactivex.subjects.PublishSubject;
//
///**
// * Created by jack on 2018/6/21
// */
//public class RxHelper {
//
//    /**
//     * 利用Observable.takeUntil()停止网络请求
//     *
//     * @param event
//     * @param lifecycleSubject
//     * @param <T>
//     * @return
//     */
//    @NonNull
//    public <T> ObservableTransformer<T, T> bindUntilEvent(@NonNull final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
//        return new ObservableTransformer<T, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<T> sourceObservable) {
//                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
//                        lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
//                            @Override
//                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) {
//                                return activityLifeCycleEvent.equals(event);
//                            }
//                        });
//                return sourceObservable.takeUntil(compareLifecycleObservable);
//            }
//        };
//    }
//
//
//    /**
//     * @param <T>
//     * @return
//     */
//    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
//        return new ObservableTransformer<BaseResponse<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<BaseResponse<T>> tObservable) {
//                Observable<ActivityLifeCycleEvent> compareLifecycleObservable =
//                        lifecycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
//                            @Override
//                            public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) {
//                                return activityLifeCycleEvent.equals(event);
//                            }
//                        });
//
//                return tObservable.flatMap(new Function<BaseResponse<T>, Observable<T>>() {
//                    @Override
//                    public Observable<T> apply(BaseResponse<T> result) {
//                        if (result.getStatus() != 0) {
//                            FRLog.error("数据正常------->");
//                            return createData(result.getData());
//                        } else {
//                            FRLog.error("发生错误------->");
//                            return Observable.error(new ApiException(result.getStatus(), result.getMessage(), false));
//                        }
//                    }
//                }).takeUntil(compareLifecycleObservable)
//                        .subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
//
//    /**
//     *
//     *
//     */
//
//
//    /**
//     * 创建成功的数据
//     *
//     * @param data
//     * @param <T>
//     * @return
//     */
//    private static <T> Observable<T> createData(final T data) {
//        return Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(ObservableEmitter<T> observableEmitter) {
//                try {
//                    observableEmitter.onNext(data);
//                    observableEmitter.onComplete();
//                } catch (Exception e) {
//                    observableEmitter.onError(e);
//                }
//            }
//        });
//
//    }
//}
