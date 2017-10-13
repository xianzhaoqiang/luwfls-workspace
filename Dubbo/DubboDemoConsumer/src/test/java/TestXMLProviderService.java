import cn.luwfls.demo.dubbo.test.fascade.exception.UserNotExistException;
import cn.luwfls.demo.dubbo.test.fascade.pojo.User;
import cn.luwfls.demo.dubbo.test.fascade.service.UserService;
import cn.luwfls.demo.dubbo.test.fascade.service.XmlProviderService;
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
public class TestXMLProviderService {

    @Autowired
    private XmlProviderService xmlProviderService ;


    @Test
    public void getUserTest()  {
        while (true) {
            try {
                System.out.println(xmlProviderService.xmlProviderMethod());
            } catch (Exception e) {
                //e.printStackTrace();
                System.out.println("error");
            }
        }
    }


}
