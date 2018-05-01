package com.wasu.ptyw;

import com.wasu.ptyw.service.IndexService;
import com.wasu.ptyw.service.impl.IndexServiceImpl;

public class Demo {
    public static void main(String[] args){
        IndexService indexService=new IndexServiceImpl();
        try {
            indexService.bulk();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
