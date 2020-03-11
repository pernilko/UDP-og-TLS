import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UDPServer {

    public static void main(String[] args) {
        final int PORT = 3000;
        System.out.println("Server starter...");

        try {
            // Step 1 : Create a socket to listen at port 1234
            DatagramSocket ds = new DatagramSocket(PORT);
            byte[] send;
            byte[] received = new byte[65535];
            DatagramPacket dpReceive;

            while (true) {
                //create a DatgramPacket to receive the data. Revieve the data in byte buffer.
                dpReceive = new DatagramPacket(received, received.length);
                ds.receive(dpReceive);
                String input = new String(dpReceive.getData(), 0, dpReceive.getLength());
                System.out.println("Klient: " + input);



                int sum = -1;
                String res = null;
                if(input.contains("-")){
                    int num1 = Integer.parseInt(input.split("-")[0]);
                    int num2 = Integer.parseInt(input.split("-")[1]);

                    sum = num1 - num2;
                    res = input + " = " + sum + "\nSkriv inn et regnestykke du ønsker å beregne, eller skriv inn 'exit' for å avslutte";
                }else if(input.contains("+")){
                    int num1 = Integer.parseInt(input.split("\\+")[0]);
                    int num2 = Integer.parseInt(input.split("\\+")[1]);
                    sum = num1 + num2;
                    res = input + " = " + sum + "\nSkriv inn et regnestykke du ønsker å beregne, eller skriv inn 'exit' for å avslutte";
                }else if(input.equals("exit")){
                    res = "exiting";
                }


                send = res.getBytes();

                InetAddress ip = dpReceive.getAddress();
                int port = dpReceive.getPort();

                DatagramPacket dpSend = new DatagramPacket(send, send.length, ip, port);
                ds.send(dpSend);

                if (input.equals("exit")) {
                    System.out.println("EXITING");
                    ds.close();
                    break;
                }

                // Clear the buffer after every message.
                received = new byte[65535];
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
