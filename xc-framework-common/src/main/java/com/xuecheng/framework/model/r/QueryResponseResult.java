package com.xuecheng.framework.model.r;

import com.xuecheng.framework.model.response.QueryResult;

/**
 * @author Daniel Liu 2020/4/22 15:50
 */
public class QueryResponseResult extends ResponseResult {
    QueryResult queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }
}