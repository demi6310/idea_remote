package com.itheima.dao;

import com.itheima.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author： ZY
 * @Description:
 * @Date：Created 10:55 2018/8/3
 */

public interface ItemDao {
    //查询索引库,参数 solrQuery 返回值:ResultModel
    public ResultModel queryProducts(SolrQuery solrQuery);


}
