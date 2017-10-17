import com.alibaba.dubbo.remoting.zookeeper.ZookeeperClient;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luwenlong
 * @date 2017/10/17 0017
 * @description zookeeper 管理
 */
public class ZKManager {
    private static final String ZKADDRESS = "172.16.103.33:2181";
    private static final String SUPERAUTH = "super:superpw";
    private static final String DIGEST = "super:superpw";

    private static ZkClient zkClient = new ZkClient(ZKADDRESS);

    @Test
    public void testZooKeeperConnect() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo("digest","super:superpw".getBytes());
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("状态： "+state);
    }

    /**
     * 超级管理员身份 修改根目录权限 为 任何人任何权限
     */
    @Test
    public void setRootWorldCDRWA() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo("digest","super:superpw".getBytes());
        ArrayList<ACL> acls = new ArrayList<>();
        acls.add(new ACL(ZooDefs.Perms.ALL,new Id("world","anyone")));
        zooKeeper.setACL("/dubbo",acls,1);
    }
    /**
     * 查询权限
     */
    @Test
    public void getAcl() throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo("digest","super:superpw".getBytes());
        List<ACL> acl = zooKeeper.getACL("/dubbo", new Stat());
        acl.forEach(acl1 -> System.out.println(acl1));
    }

    /**
     * 创建节点
     */
    @Test
    public void testCreatePersistent() {
       zkClient.createPersistent("/test123");
    }

}
