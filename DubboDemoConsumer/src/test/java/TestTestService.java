import cn.luwfls.demo.dubbo.test.fascade.exception.UserNotExistException;
import cn.luwfls.demo.dubbo.test.fascade.pojo.User;
import cn.luwfls.demo.dubbo.test.fascade.service.TestService;
import cn.luwfls.demo.dubbo.test.fascade.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author luwenlong
 * @date 2017/9/30
 * @description 类描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application-dubbo.xml"})
public class TestTestService {


    @Autowired
    private ApplicationContext applicationContext;



    @Test
    public void userServiceTest() throws IOException {
        TestService testService = (TestService) applicationContext.getBean("testService");
        int test = testService.test();
        System.out.println(test);

    }
}
