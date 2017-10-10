package cn.luwfls.demo.mq;

import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author luwenlong
 * @date 2017/9/27
 * @description 类描述
 */
public class FirstTopicListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println(message);
    }
}
