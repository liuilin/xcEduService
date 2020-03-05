package com.xuecheng.rabbitmq.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Daniel Liu 2019/12/8 22:34
 */
@SpringBootTest
@RunWith( SpringRunner.class )
public class RabbitmqConfigTest {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void mqTest() {
        String msg = "sending msg";
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_INFORM_TOPIC, "inform.sms.email", msg + i);
            System.out.println(msg+i);
        }
    }

    @Test
    public void a(){
//        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
//        integers.stream().map((integer -> {
//            integers.size();
//            return null;
//        }));
        String[] names = {"Sam", "Pamela", "Dave", "Pascal", "Erik"};
        IntStream.range(0, names.length)
                .filter(i -> names[i].length() <= i)
                .mapToObj(i -> names[i])
                .collect(Collectors.toList());
    }
}