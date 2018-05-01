package com.wasu.ptyw.service;

public interface IndexService {
    public  void  index(String id);

    public  void  get();
    public  void  del(String id);
    public  void  update(String id) throws  Exception;
    public  void  multiGet(String ... ids) throws  Exception;
    public  void  bulk() throws  Exception;
    public  void  bulkProcesstor(String index,String type,String... ids) throws  Exception;
}
