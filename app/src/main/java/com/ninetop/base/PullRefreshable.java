package com.ninetop.base;

/**
 *
 * Created by jill on 2016/12/9.
 */

public interface PullRefreshable {

    void refreshEnd(boolean hasNext, boolean loadSuccess);
}
