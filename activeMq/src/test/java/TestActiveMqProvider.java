import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.TextMessage;

/**
 * @author luwenlong
 * @date 2017/9/27
 * @description 消息队列 消息生产者
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/application-mq.xml"})
public class TestActiveMqProvider {

    @Autowired
    private JmsTemplate template;

    /**
     * 测试发送话题
     */
    @Test
    public void sendTopicTest(){
        template.send(session ->{
            TextMessage msg = session.createTextMessage();
            // 设置消息属性
            msg.setStringProperty("phrCode", "C001");
            // 设置消息内容
            msg.setText("hello world");
            return msg;
        });
    }
}
