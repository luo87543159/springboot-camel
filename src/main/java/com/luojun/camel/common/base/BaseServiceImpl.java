package com.luojun.camel.common.base;

import com.luojun.camel.admin.domain.SysPermission;
import com.luojun.camel.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<D extends BaseDomain, T, M extends Mapper<T>> implements BaseService<D>{

    @Autowired
    protected M mapper;

    //获取泛型的class
    private Class<D> entityDClass= (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    @Override
    public List<D> viewAll() {
        return BeanUtils.copyProperties(mapper.selectAll(), entityDClass);
    }
}
