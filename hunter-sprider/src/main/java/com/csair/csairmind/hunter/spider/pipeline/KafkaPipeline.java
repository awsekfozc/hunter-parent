package com.csair.csairmind.hunter.spider.pipeline;

import com.alibaba.fastjson.JSON;
import com.csair.csairmind.hunter.common.constant.DataConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by fate
 * Kafka持久化
 */
@Slf4j
public class KafkaPipeline implements Pipeline {

    private KafkaProducer<Integer, String> producer;
    private static KafkaPipeline instance = null;
    private final String topic;

    public static KafkaPipeline getInstance(String topic, String host, String port) {
        if (instance == null) {
            instance = new KafkaPipeline(topic, host, port);
        }
        return instance;
    }

    private KafkaPipeline(String topic, String host, String port) {
        Properties props = new Properties();
        props.put("bootstrap.servers", host);
        props.put("client.id", "DemoProducer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            Map<String, Object> map = resultItems.getAll();
            if (map.get(DataConstants.DATA_LIEST) != null) {//多条记录
                List<Map> list = (ArrayList)map.get(DataConstants.DATA_LIEST);
                for(Map data:list)
                    producer.send(new ProducerRecord<>(topic, JSON.toJSONString(data))).get();
            }else{//一条记录
                producer.send(new ProducerRecord<>(topic, JSON.toJSONString(map))).get();

            }
        } catch (Exception ex) {
            log.error("保存数据到kafka出错！" + ex.getMessage());
        }
    }
}
