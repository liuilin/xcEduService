package com.xuecheng.manage_cms_client.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**Mongo配置类，GridFSBucket用于打开下载流对象
 * @author Daniel Liu 2019/12/3 18:34
 */
@Component //用于spring初始化扫描为bean对象，为啥用@Configuration不行
public class MongoConfig {
    @Value("${spring.data.mongodb.database}")
    String db;

    @Bean
    public GridFSBucket getGridFSBucket(MongoClient mongoClient){
        MongoDatabase database = mongoClient.getDatabase(db);
        return GridFSBuckets.create(database);
    }

}