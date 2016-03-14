package cz.adek.elasticsearch.test;

import org.elasticsearch.action.get.GetResponse;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class ExampleIntegrationTest {

    private static final String CLUSTER_NAME = "elasticsearch";

    /**
     * use local server
     */
    private static final TestNode node = new LocalNode(CLUSTER_NAME);

    /**
     * use remote server
     */
//    private static final TestNode node = new RemoteNode(CLUSTER_NAME, "127.0.0.1");

    @BeforeClass
    public static void createIndex() throws IOException{
        node.start();
        node.getClient()
                .prepareIndex("test-index", "test-type", "1")
                .setSource(new HashMap<String, Object>() {{
                    put("test_value", "1");
                }}).execute().actionGet();
    }

    @AfterClass
    public static void cleanup() throws IOException {
        node.getClient().admin().indices().prepareDelete("test-index").execute().actionGet();
        node.terminate();
    }

    @Test
    public void simpleTest() throws IOException {
        GetResponse r = node.getClient().prepareGet("test-index", "test-type", "1").execute().actionGet();
        assertEquals(r.getSource().get("test_value"), "1");
    }

}
