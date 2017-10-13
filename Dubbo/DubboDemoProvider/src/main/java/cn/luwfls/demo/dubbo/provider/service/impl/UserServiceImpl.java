package cn.luwfls.demo.dubbo.provider.service.impl;

import cn.luwfls.demo.dubbo.test.fascade.exception.UserNotExistException;
import cn.luwfls.demo.dubbo.test.fascade.pojo.User;
import cn.luwfls.demo.dubbo.test.fascade.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.Random;

/**
 * @author luwenlong
 * @date 2017/9/29
 * @description 类描述
 */
@Service(version="1.0.0")
public class UserServiceImpl implements UserService {
    @Override
    public User getUser()throws UserNotExistException {
        //throw new UserNotExistException("用户 不存在");
        return createUser("test", User.SEX.WOMEN);
    }

    @Override
    public User createUser(String name, User.SEX sex) {
        User user = new User();
        user.setSex(sex);
        user.setUserName(name);
        return user;
    }

    @Override
    public int test() {
        return new Random().nextInt();
    }
}
