package Tasca4;

import java.io.IOException;

public class MainMulticastClient {
    public static void main(String[] args) throws IOException {
        MulticastClient clientMulticast = new MulticastClient(5557,"224.1.1.1");
        clientMulticast.runClient();
    }
}
