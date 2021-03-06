package com.wasu.ptyw;

import com.wasu.ptyw.service.IndexService;
import com.wasu.ptyw.service.impl.IndexServiceImpl;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

public class Demo {
    public static void main(String[] args){
        IndexService indexService=new IndexServiceImpl();
        try {
//            indexService.bulk();
            QueryBuilder matchQuery = QueryBuilders.rangeQuery("projectCode")
                    .from("100112").to("100118");

            List<MessageDO> result=indexService.query(matchQuery);
            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
