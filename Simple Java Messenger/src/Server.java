import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    static String IP;
    static String PORT;
    Thread t1;
    Thread t2;
    ArrayList outputStreams;

    public class Clients implements Runnable {
        BufferedReader reader;
        Socket socket;

        public Clients(Socket clientSocket) {
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("Odczytano: " + message);
                    sendtoAll(message);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        System.out.println("Server is running");
        if (args.length == 2) {
            IP = args[0];
            PORT = args[1];
        }
        Server serwer = new Server();

        serwer.t1 = new Thread(() -> {
            Communicator k1 = new Communicator();
            k1.startWork();
        });
        serwer.t2 = new Thread(() -> {
            Communicator k2 = new Communicator();
            k2.startWork();
        });

        serwer.t1.start();
        serwer.t2.start();
        serwer.doWork();
    }


    public void doWork() {
        outputStreams = new ArrayList();

        try {
            ServerSocket ss = new ServerSocket(9998);

            while (true) {
                Socket clientSocket = ss.accept();
                PrintWriter pr = new PrintWriter(clientSocket.getOutputStream());
                outputStreams.add(pr);
                Thread t = new Thread(new Clients(clientSocket));
                t.start();

                System.out.println("Connected");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void sendtoAll(String message) {
        Iterator it = outputStreams.iterator();
        while (it.hasNext()){
            try {
                PrintWriter pr = (PrintWriter) it.next();
                pr.println(message);
                pr.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
