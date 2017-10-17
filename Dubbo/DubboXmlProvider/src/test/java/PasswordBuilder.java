import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

/**
 * @author luwenlong
 * @date 2017/10/13
 * @description 类描述
 */
public class PasswordBuilder {
    @Test
    public void generate() {
        try {
            System.out.println(DigestAuthenticationProvider.generateDigest("luwfls:luwfls"));
            System.out.println(DigestAuthenticationProvider.generateDigest("super:superpw"));
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
