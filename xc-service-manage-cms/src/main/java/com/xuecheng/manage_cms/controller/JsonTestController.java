package com.xuecheng.manage_cms.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author Daniel Liu 2020/3/5 16:12
 */

@RestController
@RequestMapping( "/json/test" )
public class JsonTestController {
    private String sampleNo;

//    @Autowired
//    private JsonTestService jsonTestService;

    @PostMapping( "/person" )
    public ResponseResult create(/*@RequestBody @Valid Person person,*/ HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        System.out.println("person = " + person);
        String samples = req.getParameter("samples");
        System.out.println("samples = " + samples);
        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(samples);

//        String samples1 = JSONArray.fromObject(req.getParameter("samples")).toString();
        String samples1 = null;
        JsonNode jsonNode = objectMapper.readTree(samples1);
        for (int i = 0; i < jsonNode.size(); i++) {

            JsonNode jsonNode1 = jsonNode.get(0);
            System.out.println("jsonNode1.asText() = " + jsonNode1.get("sheetNo").asText());
        }
//        System.out.println("jsonNode = " + jsonNode);
        com.alibaba.fastjson.JSONArray jsonArray = com.alibaba.fastjson.JSONArray.parseArray(samples1);
        JSONObject json = jsonArray.getJSONObject(0);

        org.apache.commons.lang3.StringUtils.isNotEmpty(json.getString("hh"));
        getFiledValue(json);
        boolean hh = !org.springframework.util.StringUtils.isEmpty("hh");
//        Arrays.asList(objectMapper.readValue(samples1))
//        JsonNode jsonNode1 = jsonNode.get(0);
//        System.out.println("jsonNode1.asText() = " + jsonNode1.asText());
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private String getFiledValue(JSONObject json) {
        this.sampleNo = json.getString("sampleNo");
        if (1 == 1) {
            String sampleNo = StringUtils.isNotEmpty(this.sampleNo) ? this.sampleNo : null;//样品编号
        }
        String sampleName = StringUtils.isNotEmpty(json.getString("sampleName")) ? json.getString("sampleName") : null;//样品名称
        return sampleName;
    }

    @GetMapping( "/{id}" )
    public ResponseResult get(@PathVariable @Valid String id) {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @PutMapping( "/{id}" )
    public ResponseResult update(@PathVariable String id, @RequestBody @Valid Void input) {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @DeleteMapping( "/{id}" )
    public ResponseResult delete(@PathVariable String id, @RequestBody @Valid Void input) {
        return new ResponseResult(CommonCode.SUCCESS);
    }

}