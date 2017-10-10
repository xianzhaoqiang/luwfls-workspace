package cn.luwfls.demo.dubbo.test.fascade.exception;

/**
 * @author luwenlong
 * @date 2017/9/30
 * @description 类描述
 */
public class UserNotExistException extends Exception {
    public UserNotExistException(String message) {
        super(message);
    }
}
