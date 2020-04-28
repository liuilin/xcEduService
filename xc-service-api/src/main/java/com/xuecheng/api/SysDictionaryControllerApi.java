package com.xuecheng.api;

import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Daniel Liu 2020/4/28 10:55
 */
public interface SysDictionaryControllerApi {
    @ApiOperation( "数据字典" )
    SysDictionary findByType(@PathVariable String type);
}
