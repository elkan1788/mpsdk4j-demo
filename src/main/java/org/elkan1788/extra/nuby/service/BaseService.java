package org.elkan1788.extra.nuby.service;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Criteria;
import org.nutz.lang.Lang;
import org.nutz.service.IdEntityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务基础功能
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2015/4/13
 * @version 1.0.4
 */
public class BaseService<T> extends IdEntityService<T> {

    public BaseService() {
    }

    public BaseService(Dao dao) {
        super(dao);
    }

    public BaseService(Dao dao, Class<T> entityType) {
        super(dao, entityType);
    }

    /**
     * 依据单个列名获取数据
     * @param name    名称
     * @param value     键值
     * @return  实体数据
     */
    public T get(String name, Object value) {
        T get = null;
        try {
            List<T> list = dao().query(getEntityClass(), Cnd.where(name, "=", value));
            get = !Lang.isEmpty(list) ? list.get(0) : null;
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "%s 依据单个属性[%s -- %d]获取数据异常!!!",
                    getEntityClass().getSimpleName(), name, value);
        }
        return get;
    }

    /**
     * Easyui Datagrid 组件分页方法
     * @param curPage   当前页码
     * @param pageSize  每页记录
     * @param cri       条件
     * @param fetch 是否抓取关联数据
     * @return  组件数据
     */
    public Map<String, Object> easyuiDGPager(Integer curPage, Integer pageSize, Criteria cri, boolean fetch) {
        Map<String, Object> map = new HashMap<>();

        Pager pager = dao().createPager(curPage, pageSize);
        List<T> data = null;

        try {
            data = dao().query(getEntityClass(), cri, pager);
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "%s 创建DataGrid分页数据异常!!!条件: %s",
                    getEntityClass().getSimpleName(), cri.toString());
        }

        if (fetch) {
            try {
                map.put("rows", dao().fetchLinks(data, null));
            } catch (Exception e) {
                throw Lang.wrapThrow(e, "%s 关联查询数据时异常!!!",
                        getEntityClass().getSimpleName());
            }
        } else {
            map.put("rows", data);
        }

        try {
            map.put("total", dao().count(getEntityClass(), cri));
        } catch (Exception e) {
            throw Lang.wrapThrow(e, "%s 统计总记录数据时异常!!!",
                    getEntityClass().getSimpleName());
        }

        return map;
    }
}
