package Tasca4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Random;

public class MulticastServer {
    String[] frases = {
            "Muy buenas",
            "Que tal todo?",
            "Viva One Piece",
            "Hay que ir al gym para no atrofiarse",
            "Pasamos demasiadas horas sentados delante de una pantalla",
            "Este año no termino el curso"
    };

    private final MulticastSocket socket;
    private volatile boolean continueRunning = true;
    private final int port;
    private final InetAddress multicastIP;

    private final Random random;

    public MulticastServer(int port, String multicastIP) throws IOException {
        this.port = port;
        this.multicastIP = InetAddress.getByName(multicastIP);
        this.socket = new MulticastSocket(port);
        this.random = new Random();
    }

    public void runServer() {
        while (continueRunning) {
            String frase = frases[random.nextInt(frases.length)];
            byte[] sendingData = frase.getBytes();
            DatagramPacket packet = new DatagramPacket(sendingData, sendingData.length, multicastIP, port);
            try {
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("Error sending packet: " + e.getMessage());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("Error sleeping thread: " + e.getMessage());
            }
        }
        socket.close();
    }

    public void stop() {
        continueRunning = false;
    }
}
