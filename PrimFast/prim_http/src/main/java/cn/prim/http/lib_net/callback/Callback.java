package cn.prim.http.lib_net.callback;

import okhttp3.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求回调
 * @time 2019/1/2 - 4:28 PM
 */
public abstract class Callback<T> implements Converter {
    public abstract void onStart();

    public abstract void onSuccess(T t);

    public abstract void onCacheSuccess();

    public abstract void onError();

    public abstract void onFinish();

    public abstract void uploadProgress();

    public abstract void downloadProgress();

    @Override
    public Type getType() {
        Type genType = getClass().getGenericSuperclass();
        List<Type> needtypes = null;
        // if Type is T
        if (genType instanceof ParameterizedType) {
            needtypes = new ArrayList<>();
            Type[] parentypes = ((ParameterizedType) genType).getActualTypeArguments();
            for (Type childtype : parentypes) {
                needtypes.add(childtype);
                if (childtype instanceof ParameterizedType) {
                    Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                    Collections.addAll(needtypes, childtypes);
                }
            }
        }
        if (needtypes == null || needtypes.isEmpty()) {
            return RequestBody.class;
        }
        return needtypes.get(0);
    }
}