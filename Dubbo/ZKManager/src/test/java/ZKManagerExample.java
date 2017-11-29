package cn.luwfls.demo.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luwenlong
 * @date 2017/10/17 0017
 * @description zookeeper 管理
 */
public class ZKManagerExample {
    @Test
    public void testSuperServer() {
        List<ACL> acls = new ArrayList<ACL>(2);
        try {
            Id id1 = new Id("digest", DigestAuthenticationProvider.generateDigest("fish:fishpw"));
            ACL acl1 = new ACL(ZooDefs.Perms.WRITE, id1);

            Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("qsd:qsdpw"));
            ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);

            acls.add(acl1);
            acls.add(acl2);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        ZooKeeper zk = null;
        try {
            //10.0.1.75:2181,10.0.1.76:2181,10.0.1.77:2181 zookeeper集群
            zk = new ZooKeeper("172.16.103.33:2181", 300000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });
            zk.addAuthInfo("digest", "super:superpw".getBytes());

            if (zk.exists("/test", true) == null) {
                System.out.println(zk.create("/test1", "ACL测试".getBytes(), acls, CreateMode.PERSISTENT));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    @Test
    public void testSuperClient() {
        try {
            ZooKeeper zk = new ZooKeeper("172.16.103.33:2181", 300000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });
            zk.addAuthInfo("digest", "super:superpw".getBytes());
            System.out.println(new String(zk.getData("/test", null, null)));
            zk.setData("/test", "I change！".getBytes(), -1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
