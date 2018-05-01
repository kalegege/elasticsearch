package com.wasu.ptyw.utils;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

public class ESCLient {
    private final Logger logger = LoggerFactory.getLogger(ESCLient.class);

    private static final String CLUSTER_NAME="kale-application";

    TransportClient transportClient=null;

    public ESCLient() {
        try{
            Settings settings=Settings.builder()
                    .put("cluster.name",CLUSTER_NAME)
                    .put("client.transport.sniff","true")
                    .build();
            transportClient = new PreBuiltTransportClient(settings)
                                 .addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.1.109"), 9301));
        }catch(Exception e){
            transportClient.close();
            logger.error(e.getMessage());
        }
        logger.info("connect to es success");
    }

    public TransportClient getConnection(){
        if(null == transportClient){
            synchronized (ESCLient.class){
                if(null == transportClient){
                    new ESCLient();
                }
            }
        }
        return transportClient;
    }

}
