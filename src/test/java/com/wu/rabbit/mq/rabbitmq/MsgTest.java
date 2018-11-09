package com.wu.rabbit.mq.rabbitmq;

import com.wu.rabbit.mq.MqApplication;
import com.wu.rabbit.mq.service.MsgProducer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MqApplication.class)
@WebAppConfiguration
public class MsgTest {

    @Autowired
    private MsgProducer msgProducer;

    @Before
    public void before(){

    }

    @Test
    public void testMq(){
        String content = "this is content!";
        for (int i = 0 ; i < 1000 ; i++){
            content += i;
            msgProducer.sendMsg(content);
            content = "this is content!";
        }
    }

    @After
    public void after(){

    }
}
