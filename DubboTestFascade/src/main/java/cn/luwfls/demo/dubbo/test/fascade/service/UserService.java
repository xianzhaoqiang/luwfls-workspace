package cn.luwfls.demo.dubbo.test.fascade.service;

import cn.luwfls.demo.dubbo.test.fascade.exception.UserNotExistException;
import cn.luwfls.demo.dubbo.test.fascade.pojo.User;

/**
 * @author luwenlong
 * @date 2017/9/29
 * @description 类描述
 */
public interface UserService {
    User getUser() throws UserNotExistException;

    User createUser(String name, User.SEX sex);

    int test();
}
