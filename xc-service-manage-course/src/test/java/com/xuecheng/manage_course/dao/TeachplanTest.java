package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Daniel Liu 2019/12/17 20:32
 */

@RunWith( SpringRunner.class )
@SpringBootTest
@Log4j2
public class TeachplanTest {

    @Autowired
    private TeachplanMapper teachPlanMapper;

    @Test
    public void t(){
        TeachplanNode teachPlanById = teachPlanMapper.findTeachPlanById("4028e581617f945f01617f9dabc40000");
    }

}