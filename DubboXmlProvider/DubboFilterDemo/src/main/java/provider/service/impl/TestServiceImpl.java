package provider.service.impl;

import cn.luwfls.demo.dubbo.test.fascade.service.TestService;

import java.util.Random;

/**
 * @author luwenlong
 * @date 2017/9/30
 * @description 类描述
 */
@com.alibaba.dubbo.config.annotation.Service(version = "1.0.0",register = false, group = "test")
public class TestServiceImpl implements TestService {
    @Override
    public int test() {
        return new Random().nextInt();
    }
}
