import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPClient {

    public static void main(String args[]) {
        final int PORT = 3000;
        System.out.println("Kobler til server..." +
                "\nSkriv inn et regnestykke du ønsker å beregne," +
                " eller skriv inn 'exit' for å avslutte");

        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1:Create the socket object for carrying the data.
            DatagramSocket ds = new DatagramSocket();
            InetAddress ip = InetAddress.getLocalHost();

            byte[] send;
            byte[] recived = new byte[256];

            byte buffer[];

            // loop while user not enters "bye"
            while (true) {
                String input = scanner.nextLine();

                // convert the String input into the byte array.
                send = input.getBytes();

                // Step 2 : Create the datagramPacket for sending the data.
                DatagramPacket dpSend = new DatagramPacket(send, send.length, ip, PORT);

                // Step 3 : invoke the send call to actually send the data.
                ds.send(dpSend);

                DatagramPacket dpRecive = new DatagramPacket(recived,recived.length);
                ds.receive(dpRecive);

                String res = new String(dpRecive.getData(), 0, dpRecive.getLength());
                System.out.println("Server: " + res);

                if (input.equals("exit")) {
                    ds.close();
                    break;
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
