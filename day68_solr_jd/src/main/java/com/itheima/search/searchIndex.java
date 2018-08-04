package com.itheima.search;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Author： ZY
 * @Description: 查询索引
 * @Date：Created 9:04 2018/8/3
 */
public class searchIndex {
    @Test
    //简单查询
    public void simpleSearch() throws Exception {
        //创建solr服务对象
        String url = "http://localhost:8080/solr/item";
        SolrServer solrServer = new HttpSolrServer(url);

        //创建查询对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询表达式
        solrQuery.setQuery("*:*");  //查询所有
        //执行查询,返回response对象
        QueryResponse response = solrServer.query(solrQuery);
        //获取结果集
        SolrDocumentList solrDocumentList = response.getResults();
        //输出文档总数
        System.out.println("命中文档总数是: " +solrDocumentList.getNumFound());
        for (SolrDocument solrDocument:
             solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("product_name"));
            System.out.println(solrDocument.get("product_price"));
        }
    }

    @Test
    //solrj复杂查询
    public void complexSearch() throws SolrServerException {
        //1 指定远程服务器索引库地址
        String url = "http://localhost:8080/solr/item";
        //2 连接solr远程索引库
        SolrServer solrServer = new HttpSolrServer(url);
        //3 创建封装查询条件对象
        SolrQuery solrQuery = new SolrQuery();
        //4 设置主查询条件
            //solrQuery.set("q","*:*");
        solrQuery.setQuery("台灯");

        // 5.设置过滤查询条件：FQ：Field query
        // a.查询商品类别是时尚卫浴
        solrQuery.addFilterQuery("product_catalog_name:时尚卫浴");
        //b.商品价格在1--20
        solrQuery.addFilterQuery("product_price:[1 TO 20]");

        // 6.设置排序
        // 第一个参数：指定对那个域进行排序
        // 第二个参数：升序，降序
        solrQuery.setSort("product_price", SolrQuery.ORDER.asc);

        //7.分页
        solrQuery.setStart(1);
        solrQuery.setRows(5);

        // 8.设置过滤字段
        // 参数：查询需要过滤显示的字段，字段之间使用空格，或者逗号分隔。
        //solrQuery.setFields("product_name,product_price");

        // 9.设置高亮
        // 开启高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        // 10.设置默认查询字段。默认查询通常和主查询条件结合使用
        solrQuery.set("df","product_keywords");

        //使用solrServer执行查询
        QueryResponse response =solrServer.query(solrQuery);
        //获取结果集对象
        SolrDocumentList results = response.getResults();
        //获取命中总记录数
        long numFound = results.getNumFound();
        System.out.println("命中总记录数: " +numFound);

        //循环获取每一个文档对象
        for (SolrDocument sdoc :
                results) {
            String id = (String) sdoc.get("id");
            System.out.println("文档ID: "+id);
            String productName = (String) sdoc.get("product_name");

            //获取高亮
            Map<String,Map<String,List<String>>> highlighting = response.getHighlighting();
            //第一个map的key就是ID
            Map<String,List<String>> map = highlighting.get(id);
            //第二个map的key是域名
            List<String> list = map.get("product_name");

            if(list != null && list.size()>0) {
                productName = list.get(0);
            }
            System.out.println("商品名称: " +productName);
           //商品价格
            Float productPrice = (Float) sdoc.get("product_price");
            System.out.println("商品价格：" + productPrice);
            //商品描述
            String productDescription = (String) sdoc.get("product_description");
            System.out.println("商品描述：" + productDescription);
            //商品图片
            String productPicture = (String) sdoc.get("product_picture");
            System.out.println("商品图片：" + productPicture);
           //商品分类
            String productCatalogName = (String) sdoc.get("product_catalog_name");
            System.out.println("商品类别：" + productCatalogName);

        }

    }
}
