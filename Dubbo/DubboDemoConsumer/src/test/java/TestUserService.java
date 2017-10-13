import cn.luwfls.demo.dubbo.test.fascade.exception.UserNotExistException;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getUserTest() throws UserNotExistException {
        while (true) {
            User user = userService.getUser();
            System.out.println(user);
        }
    }

    @Test
    public void dubboReferenceTest() throws UserNotExistException {
        UserService userService = (UserService) applicationContext.getBean("userService");
        for (int i = 1; i > 0; i++) {
            userService.getUser();
            userService.createUser("aa", User.SEX.MEN);
            //System.out.println(userService.test());
        }
    }
}
