package cz.adek.elasticsearch.test;

import org.elasticsearch.client.Client;

import java.io.IOException;

public interface TestNode {
    Client getClient() throws IOException;
    void start();
    void terminate() throws IOException;
}
