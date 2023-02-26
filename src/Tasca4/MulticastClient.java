package Tasca4;

import java.io.IOException;
import java.net.*;

public class MulticastClient {
    private boolean continueRunning = true;
    private MulticastSocket socket;
    private int port;
    private InetAddress multicastIP;
    private NetworkInterface netIf;
    private InetSocketAddress group;


    public MulticastClient(int port, String multicastIP) throws IOException {
        socket = new MulticastSocket(port);
        this.multicastIP = InetAddress.getByName(multicastIP);
        this.port = port;
        netIf = socket.getNetworkInterface();
        group = new InetSocketAddress(multicastIP,port);
    }

    public void runClient() throws IOException {
        DatagramPacket packet;
        byte[] receivedData = new byte[1024];

        try {
            socket.joinGroup(group, netIf);
            System.out.printf("Connected to %s:%d%n", group.getAddress(), group.getPort());

            while (continueRunning) {
                packet = new DatagramPacket(receivedData, receivedData.length);
                socket.setSoTimeout(5000);
                try {
                    socket.receive(packet);
                    continueRunning = getData(packet.getData(), packet.getLength());
                } catch (SocketTimeoutException e) {
                    System.out.println("Lost connection to the server.");
                    continueRunning = false;
                } catch (IOException e) {
                    System.err.println("Error receiving data: " + e.getMessage());
                    continueRunning = false;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("Thread interrupted: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error joining multicast group: " + e.getMessage());
        } finally {
            socket.leaveGroup(group, netIf);
            socket.close();
        }
    }

    protected boolean getData(byte[] data,int length) {
        boolean ret = true;

        String frase = new String(data, 0, length);

        int numPalabras = comptaParaules(frase);
        if (numPalabras > 8) {
            System.out.println(frase);
        }

        return ret;
    }

    private static int comptaParaules(String text) {
        String[] words = text.trim().split(" ");
        return words.length;
    }

}