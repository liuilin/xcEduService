package com.xuecheng.search.service;

import com.xuecheng.framework.domain.course.CoursePub;
import com.xuecheng.framework.domain.search.CourseSearchParam;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Daniel Liu 2020/7/8 10:48
 */
@Service
public class EsCourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCourseService.class);
    @Value( "${xuecheng.elasticsearch.course.index}" )
    private String index;

    @Value( "${xuecheng.elasticsearch.course.type}" )
    private String type;

    @Value( "${xuecheng.elasticsearch.course.source_field}" )
    private String sourceField;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public QueryResponseResult<CoursePub> list(int page, int size, CourseSearchParam courseSearchParam) {
        if (courseSearchParam == null) {
            courseSearchParam = new CourseSearchParam();
        }
        //设置索引和类型
        SearchRequest searchRequest = new SearchRequest(index).types(type);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //整合构建查询对象
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //source源字段过滤
        String[] sourceFields = sourceField.split(",");
        searchSourceBuilder.fetchSource(sourceFields, new String[]{});
        //关键字
        if (StringUtils.isNotEmpty(courseSearchParam.getKeyword())) {
            //匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(courseSearchParam.getKeyword(), "name", "teachplan", "desription").minimumShouldMatch("70%")//设置匹配占比
                    .field("name", 10);//提升字段boost值
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        //过滤
        if (StringUtils.isNotEmpty(courseSearchParam.getMt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",courseSearchParam.getMt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getSt())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("st", courseSearchParam.getSt()));
        }
        if (StringUtils.isNotEmpty(courseSearchParam.getGrade())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("grade", courseSearchParam.getGrade()));
        }
        //bool查询
        searchSourceBuilder.query(boolQueryBuilder);
        //请求搜索
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("xuecheng search error..{}", e.getMessage());
            return new QueryResponseResult(CommonCode.SUCCESS, new QueryResult<CoursePub>());
        }

        //结果集处理1
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();

        List<CoursePub> list = new ArrayList<>();
        for (SearchHit hit : searchHits) {
            CoursePub coursePub = new CoursePub();
            //取出source
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //取出名称
            coursePub.setName((String) sourceAsMap.get("name"));
            //图片
            coursePub.setPic((String) sourceAsMap.get("pic"));
            //价格
            double price = 0;
            if (sourceAsMap.get("price") != null) {
                price = (double) sourceAsMap.get("price");
            }
            coursePub.setPrice(price);
            double priceOld = 0;
            if (sourceAsMap.get("priceOld") != null) {
                priceOld = (double) sourceAsMap.get("priceOld");
            }
            coursePub.setPrice_old(priceOld);
            list.add(coursePub);
        }

        QueryResult<CoursePub> queryResult = new QueryResult<>();
        queryResult.setList(list);
        queryResult.setTotal(hits.getTotalHits());
        return new QueryResponseResult<>(CommonCode.SUCCESS, queryResult);
    }
}