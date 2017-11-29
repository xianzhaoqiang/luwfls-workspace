import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.ACL;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author luwenlong
 * @date 2017/10/18 0018
 * @description 类描述
 */
public class CuratorClient {
    private CuratorFramework client;

    @Before
    public void initClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client =
                CuratorFrameworkFactory.builder()
                        .connectString("172.16.103.33:2181")
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .authorization("digest", "super:superpw".getBytes())
                        .build();
    }

    @Test
    public void zkCuratorTest() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

        CuratorFramework client =
                CuratorFrameworkFactory.builder()
                        .connectString("172.16.103.33:2181")
                        .sessionTimeoutMs(5000)
                        .connectionTimeoutMs(5000)
                        .retryPolicy(retryPolicy)
                        .authorization("digest", "super:superpw".getBytes())
                        .build();
        client.start();

    }

    @Test
    public void createZnode() throws Exception {
        client.start();
        String path = client.create().creatingParentsIfNeeded()
                //.withMode(CreateMode.EPHEMERAL)
                .forPath("/curator/key1","131".getBytes());
        System.out.println(path);
    }

    @Test
    public void queryNode(){
        client.start();
        List<String> list = null;
        try {
            list = client.getChildren().forPath("/curator");
        } catch (Exception e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);

    }

    @Test
    public void queryData(){
        client.start();
        byte[] data = null;
        try {
            data = client.getData().forPath("/curator/key1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(new String(data));
    }

    @Test
    public void getACL(){
        client.start();
        try {
            List<ACL> acls = client.getACL().forPath("/curator");
            acls.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}