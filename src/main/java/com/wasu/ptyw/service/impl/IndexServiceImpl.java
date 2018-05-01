package com.wasu.ptyw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wasu.ptyw.MessageDO;
import com.wasu.ptyw.service.IndexService;
import com.wasu.ptyw.utils.ESCLient;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class IndexServiceImpl implements IndexService {

    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    private ESCLient client = new ESCLient();

    @Override
    public void index(String id) {
        //        String json = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
        try {


            Map<String, Object> jsonMap = new HashMap<String, Object>();
            jsonMap.put("name", "jim" + id);
            jsonMap.put("age", 20 + id);
            jsonMap.put("date", new Date());
            jsonMap.put("message", "测试" + id);
            jsonMap.put("tel", "1234567");
            //IndexResponse indexResponse = client.getConnection().prepareIndex("twitter", "tweet").setSource(JSONObject.toJSON(jsonMap), XContentType.JSON).get();
            IndexResponse indexResponse = client.getConnection().prepareIndex("xiaot", "test", id).setSource(jsonMap).get();
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
            System.out.println(_index + "_" + _type + "_" + _id + "_" + _version + "_" + status);
        } catch (Exception ex) {
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
        String INDEX = "monitor-" + simpleDateFormat.format(new Date());
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());

        for (int i = 110; i < 120; i++) {
            Random random = new Random();
            bulkRequest.add(client.getConnection().prepareIndex(INDEX, "alarm")
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("projectCode", "100" + i)
                            .field("taskId", "80" + random.nextInt())
                            .field("result", random.nextBoolean() ? "true" : "false")
                            .field("type",random.nextInt(3))
                            .field("postDate", calendar.getTimeInMillis())
                            .field("message", "trying out Elastic Search")
                            .endObject()
                    )
            );
            calendar.add(Calendar.MINUTE,random.nextInt(5));
        }
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            //处理错误
            logger.info("error to bulk");
        }
        logger.info("end to bulk");

    }

    @Override
    public List<MessageDO> query(QueryBuilder queryBuilder) throws Exception {
        String INDEX = "monitor-" + simpleDateFormat.format(new Date());
        List<MessageDO> messageDOS=new ArrayList<>();

//        HighlightBuilder hiBuilder=new HighlightBuilder();
//        hiBuilder.preTags("<h2>");
//        hiBuilder.postTags("</h2>");
//        hiBuilder.field("type");
        // 搜索数据
        SearchResponse response = client.getConnection().prepareSearch(INDEX)
                .setQuery(queryBuilder)
//                .highlighter(hiBuilder)
                .execute().actionGet();
        //获取查询结果集
        SearchHits searchHits = response.getHits();
        System.out.println("共搜到:"+searchHits.getTotalHits()+"条结果!");
        //遍历结果
        for(SearchHit hit:searchHits){
            System.out.println("String方式打印文档搜索内容:");
            System.out.println(hit.getSourceAsString());
            System.out.println("Map方式打印高亮内容");
            System.out.println(hit.getHighlightFields());
            JSONObject jsonObject = JSONObject.parseObject(hit.getSourceAsString());
            MessageDO item=JSONObject.toJavaObject(jsonObject,MessageDO.class);
            messageDOS.add(item);
//            System.out.println("遍历高亮集合，打印高亮片段:");
//            Text[] text = hit.getHighlightFields().get("title").getFragments();
//            for (Text str : text) {
//                System.out.println(str.string());
//            }
        }
        System.out.println(messageDOS);
        return messageDOS;
    }

    @Override
    public void bulkProcesstor(String index, String type, String... ids) throws Exception {

    }
}
