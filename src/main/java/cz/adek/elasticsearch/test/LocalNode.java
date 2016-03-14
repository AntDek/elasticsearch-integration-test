package cz.adek.elasticsearch.test;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;

import java.io.IOException;
import java.nio.file.Paths;

public class LocalNode implements TestNode {

    private final static String DEFAULT_CLUSTER_NAME = "elasticsearch";

    private final static String DATA_HOME_DIR = "./target/elasticsearch-test";

    protected Node node;

    public LocalNode(String clusterName) {
        node = new Node(getSettings(clusterName, "node-test-" + System.currentTimeMillis()));
    }

    public LocalNode() {
        this(DEFAULT_CLUSTER_NAME);
    }

    public Client getClient() throws IOException {
        return node.client();
    }

    public void start() {
        node.start();
    }

    public void terminate() throws IOException {
        if(getClient() != null) {
            getClient().close();
        }

        if(node != null && !node.isClosed()) {
            node.close();
        }

        FileSystemUtils.deleteSubDirectories(Paths.get(DATA_HOME_DIR));
    }

    private Settings getSettings(String clusterName, String nodeName) {
        return Settings.settingsBuilder()
                .put(new Object[]{"path.home", DATA_HOME_DIR})
                .put("name", nodeName)
                .put("cluster.name", clusterName)
                .put("node.mode", "local")
                .put("index.number_of_shards", "1")
                .put("index.store.type", "mmapfs")
                .build();
    }
}
