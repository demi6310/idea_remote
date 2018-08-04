package com.itheima.service;

import com.itheima.pojo.ResultModel;
import org.springframework.stereotype.Service;

/**
 * @Author： ZY
 * @Description:
 * @Date：Created 11:38 2018/8/3
 */
public interface ItemService {
    /*
    * 需求: 根据关键词查询索引库商品数据
    * 参数: String queryString,String catelog_name,String price,String sort,Integer page,Integer rows
    * 返回值:ResultModel
    * */

    public ResultModel queryProducts(String queryString,String catelog_name,String price,String sort,Integer page,Integer rows);
}
