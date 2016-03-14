package cz.adek.elasticsearch.test;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.io.IOException;
import java.net.InetAddress;

;

public class RemoteNode implements TestNode {

    private static final int DEFAULT_ES_PORT = 9300;

    private final String connectionString;
    private final String clusterName;
    private static Client client;

    public RemoteNode(String clusterName, String connectionString) {
        this.clusterName = clusterName;
        this.connectionString = connectionString;
    }

    public Client getClient() throws IOException {
        if (client == null)
            client = createConnection(clusterName, connectionString);
        return client;
    }

    public void start() {
        //do nothing
    }

    public void terminate() throws IOException {
        //do nothing
    }

    private Client createConnection(String clusterName, String connectionString) throws IOException {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", clusterName)
                .build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        addTransportAddresses(client, connectionString);
        return client;
    }

    private void addTransportAddresses(TransportClient client, String connectionString) throws IOException {
        String[] hostPort = connectionString.trim().split(":");
        String host = hostPort[0].trim();
        int port = hostPort.length == 2 ? Integer.parseInt(hostPort[1].trim()) : DEFAULT_ES_PORT;
        client.addTransportAddresses(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }
}
