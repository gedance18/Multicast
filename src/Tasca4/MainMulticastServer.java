package Tasca4;

import java.io.IOException;

public class MainMulticastServer {
    public static void main(String[] args) throws IOException {
        MulticastServer serverMulticast = new MulticastServer(5557,"224.1.1.1");
        serverMulticast.runServer();
    }
}