import cn.luwfls.demo.dubbo.test.fascade.pojo.User;
import cn.luwfls.demo.dubbo.test.fascade.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author luwenlong
 * @date 2017/9/30
 * @description 类描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application-dubbo.xml"})
public class TestUserService {

    //@Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getUserTest(){
        User user =userService.getUser();
        System.out.println(user);
    }

    @Test
    public void dubboReferenceTest(){
        UserService userService = (UserService) applicationContext.getBean("userService");
        System.out.println(userService.test());
    }
}
