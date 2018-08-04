package com.itheima.controller;

import com.itheima.pojo.ResultModel;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author： ZY
 * @Description:
 * @Date：Created 10:27 2018/8/3
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    //注入service对象
    @Autowired
    private ItemService itemService;
    /*
     * 需求: 根据关键词查询索引库商品数据
     * 参数: String queryString,String catelog_name,String price,String sort,Integer page,Integer rows
     * 返回值:ResultModel
     * 业务:
     *  1.接收参数
     *  2.数据查询结果页面回显
     * */
    //需求:展示搜索列表页面
    @RequestMapping("list")
    public String showList(String queryString,
                           String catelog_name,
                           String price,
                           @RequestParam(defaultValue = "1")
                           String sort,
                           @RequestParam(defaultValue = "1")
                           Integer page,
                           @RequestParam(defaultValue = "1")
                           Integer rows,
                           Model model){
        //调用service方法,实现索引库搜索
        ResultModel rmodel1 = itemService.queryProducts(queryString, catelog_name, price, sort, page, rows);
        //页面处理回显
        model.addAttribute("queryString",queryString);
        //分类名称回显
        model.addAttribute("catelog_name",catelog_name);
        //价格回显
        model.addAttribute("price",price);
        //排序回显
        model.addAttribute("sort",sort);
        model.addAttribute("result",rmodel1.getCurPage());


        return  "product_list";
    }

    
}
