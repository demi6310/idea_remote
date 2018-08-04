package com.itheima.dao.impl;

import com.itheima.dao.ItemDao;
import com.itheima.pojo.Item;
import com.itheima.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author： ZY
 * @Description:
 * @Date：Created 11:03 2018/8/3
 */
@Repository
public class ItemDaoImpl implements ItemDao {
    //注入solrj服务对象
    @Autowired
    private SolrServer solrServer;

    @Override
    public ResultModel queryProducts(SolrQuery solrQuery) {
        //创建返回结果包装类对象
        ResultModel model = new ResultModel();
        //创建集合对象,封装从索引库查询的商品数据
        List<Item> itemList = new ArrayList<>();
        try {
            //使用solrServer执行查询
            QueryResponse response = solrServer.query(solrQuery);
            //获取结果集对象
            SolrDocumentList results = response.getResults();
            //获取命中总记录数
            long numFound = results.getNumFound();
            System.out.println("命中总记录数: " + numFound);

            //循环获取每一个文档对象
            for (SolrDocument sdoc :
                    results) {
                //创建商品对象item
                Item item = new Item();

                //获取ID
                String id = (String) sdoc.get("id");
                item.setPid(id);
                System.out.println("文档ID: " + id);
                //商品标题
                String productName = (String) sdoc.get("product_name");

                //获取高亮
                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                //第一个map的key就是ID
                Map<String, List<String>> map = highlighting.get(id);
                //第二个map的key是域名
                List<String> list = map.get("product_name");

                //判断高亮是否存在
                if (list != null && list.size() > 0) {
                    productName = list.get(0);
                }

                item.setName(productName);
                //商品名称
                System.out.println("商品名称: " + productName);
                //商品价格
                Float productPrice = (Float) sdoc.get("product_price");
                System.out.println("商品价格：" + productPrice);

                //商品图片
                String productPicture = (String) sdoc.get("product_picture");
                System.out.println("商品图片：" + productPicture);

                itemList.add(item);

            }

            //把集合放入包装类对象
            model.setProductList(itemList);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return model;
    }
}
