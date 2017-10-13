package provider.filter;

import com.alibaba.dubbo.rpc.*;

/**
 * @author luwenlong
 * @date 2017/10/9
 * @description Dubbo 过滤器
 */
public class DubboProviderTestFilter implements Filter{
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println(invoker);
        System.out.println(invocation);
        return invoker.invoke(invocation);
    }
}
