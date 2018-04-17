package youbao.shopping.ninetop.service.listener;

import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.index.category.CategoryListBean;
import youbao.shopping.ninetop.common.cache.ACache;

/**
 * Created by jill on 2016/11/22.
 */

public abstract class CacheCommonResultListener<T> extends CommonResultListener<T> {

    public String cacheKey;
    public int cacheTime;

    public CacheCommonResultListener(Viewable context, int cacheTime){
        super(context);
        this.cacheTime = cacheTime;
    }

    public void successHandle(CategoryListBean result) {
        ACache mCache = ACache.get(context.getTargetActivity());
        mCache.put(cacheKey, result);
    }

    public T getCache(){
        ACache mAcache = ACache.get(context.getTargetActivity());
        return (T)mAcache.getAsObject(cacheKey);
    }

}
