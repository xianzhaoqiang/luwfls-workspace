import org.I0Itec.zkclient.ZkClient;
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
    private static final String ZKADDRESS = "172.16.101.130:2190";
    private static final String SUPERAUTH = "super:superpw";
    private static final String LUWFLS = "luwfls:luwfls";
    private static final String DIGEST = "digest";

    private static ZkClient zkClient = new ZkClient(ZKADDRESS);

    @Test
    public void testZooKeeperConnect() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        //zooKeeper.addAuthInfo(DIGEST,"super:superpw".getBytes());
        ZooKeeper.States state = zooKeeper.getState();
        System.out.println("状态： "+state);

    }

    /**
     * 超级管理员身份 修改根目录权限 为 任何人任何权限
     */
    @Test
    public void setRootWorldCDRWA() throws Exception {
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo(DIGEST,SUPERAUTH.getBytes());
        ArrayList<ACL> acls = new ArrayList<>();
        acls.add(new ACL(ZooDefs.Perms.ALL,new Id("world","anyone")));
        zooKeeper.setACL("/dubbo",acls,13);
    }

    /**
     * 设置IP段 白名单
     * 有问题 KeeperErrorCode = InvalidACL for /dubbo
     */
    @Test
    public void setIPS() throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo(DIGEST,LUWFLS.getBytes());
        ArrayList<ACL> acls = new ArrayList<>();
        acls.add(new ACL(ZooDefs.Perms.ALL,new Id("ip","172.16.103.33")));
        acls.add(new ACL(ZooDefs.Perms.ALL,new Id("ip","172.16.103.60")));
        //当前version 可理解为乐观锁的最后一个版本号（屁民理论）
        zooKeeper.setACL("/test123",acls,zooKeeper.exists("/test123",false).getAversion());
    }
    /**
     * 查询权限
     */
    @Test
    public void getAcl() throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo("digest","luwfls:luwfls".getBytes());
        ZooKeeper.States state = zooKeeper.getState();
        System.out.printf("state  " + state);
        List<ACL> acl = zooKeeper.getACL("/test123", new Stat());
        acl.forEach(acl1 -> System.out.println(acl1));
    }

    /**
     * 查询 节点版本 version
     * 更改权限的时候需要设置 当前节点的 可用版本号 Stat.aversion
     */

    @Test
    public void queryVersion() throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper(ZKADDRESS, 500, watchedEvent -> System.out.println("已经触发了" + watchedEvent.getType() + "事件！"));
        zooKeeper.addAuthInfo("digest","luwfls:luwfls".getBytes());
        Stat stat = zooKeeper.exists("/dubbo", false);
        System.out.println(String.format("version %s  cversion %s aversion %s ", stat.getVersion(),stat.getCversion(),stat.getAversion()));
        System.out.println(stat);
    }
    /**
     * 创建节点
     */
    @Test
    public void testCreatePersistent() {
       zkClient.createPersistent("/test123");
    }

}
