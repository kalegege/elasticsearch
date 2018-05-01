package com.wasu.ptyw.service.impl;

import com.wasu.ptyw.service.IndexService;
import com.wasu.ptyw.utils.ESCLient;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class IndexServiceImpl implements IndexService {

    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    private static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");

    private ESCLient client=new ESCLient();

    @Override
    public void index(String id) {
        //        String json = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
        try {


            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("name","jim"+id);
            jsonMap.put("age",20+id);
            jsonMap.put("date",new Date());
            jsonMap.put("message","测试"+id);
            jsonMap.put("tel","1234567");
            //IndexResponse indexResponse = client.getConnection().prepareIndex("twitter", "tweet").setSource(JSONObject.toJSON(jsonMap), XContentType.JSON).get();
            IndexResponse indexResponse = client.getConnection().prepareIndex("xiaot", "test",id).setSource(jsonMap).get();
            // Index name
            String _index = indexResponse.getIndex();
            // Type name
            String _type = indexResponse.getType();
            // Document ID (generated or not)
            String _id = indexResponse.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long _version = indexResponse.getVersion();
            // status has stored current instance statement.
            RestStatus status = indexResponse.status();
            System.out.println(_index+"_"+_type+"_"+_id+"_"+_version+"_"+status);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void get() {

    }

    @Override
    public void del(String id) {

    }

    @Override
    public void update(String id) throws Exception {

    }

    @Override
    public void multiGet(String... ids) throws Exception {

    }

    @Override
    public void bulk() throws Exception {
        logger.info("start to bulk");
        BulkRequestBuilder bulkRequest = client.getConnection().prepareBulk();
        String INDEX="festival-"+simpleDateFormat.format(new Date());

        bulkRequest.add(client.getConnection().prepareIndex(INDEX, "test")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elastic Search")
                        .endObject()
                )
        );
        bulkRequest.add(client.getConnection().prepareIndex(INDEX, "test")
                .setSource(jsonBuilder()
                        .startObject()
                        .field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post")
                        .endObject()
                )
        );
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            //处理错误
            logger.info("error to bulk");
        }
        logger.info("end to bulk");

    }

    @Override
    public void bulkProcesstor(String index, String type, String... ids) throws Exception {

    }
}
