package com.wasu.ptyw.service;

import com.wasu.ptyw.MessageDO;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

public interface IndexService {
    public  void  index(String id);

    public  void  get();
    public  void  del(String id);
    public  void  update(String id) throws  Exception;
    public  void  multiGet(String ... ids) throws  Exception;
    public  void  bulk() throws  Exception;
    public List<MessageDO> query(QueryBuilder queryBuilder) throws Exception;
    public  void  bulkProcesstor(String index,String type,String... ids) throws  Exception;
}
