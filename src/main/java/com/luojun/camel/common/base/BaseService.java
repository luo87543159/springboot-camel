package com.luojun.camel.common.base;

import java.util.List;

public interface BaseService<D> {
    /**
     * 查看所有权限内容
     * @return
     */
    List<D> viewAll();
}
