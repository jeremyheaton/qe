import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

/**
 * Created by jerem on 3/24/2020.
 */
public class MessageServer {
    public static void main(String[] args) {
        connectToServerMultiple();
    }

    private static void connectToServerMultiple() {
        int max_con = 3; //indicate maximum number of connections.
        int port = 1133;

        //Create multiple ServerSockets to connect to a server
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Create a for loop that creates max_con number of threads
        for (int i = 0; i < max_con; i++) {
            ServerSocket finalServerSocket = serverSocket;
            //Create the thread
            Runnable runnable = () -> {
                try {
                    Socket listenerSocket;
                    synchronized (finalServerSocket) {
                        listenerSocket = finalServerSocket.accept();
                    }
                    InputStream inputToServer = listenerSocket.getInputStream();
                    OutputStream outputFromServer = listenerSocket.getOutputStream();

                    Scanner input = new Scanner(inputToServer);
                    PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputFromServer), true);

                    printWriter.println("Connected to qe instnace");

                    boolean done = false;
                    while (input.hasNextLine()) {
                        String line = input.nextLine();

                        String[] params = line.split(" ", 3);

                        if (params[0].equals("publish")) {
                            MessageQueueManager.getInstance().publish(params[1], params[2]).thenAccept(printWriter::println);
                        } else if (params[0].equals("consume")) {
                            try {
                                MessageQueueManager.getInstance().consume(params[1]).thenAccept(printWriter::println);
                            } catch (Exception ex) {
                                printWriter.println("consumer failed");
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            //Execute the runnables!
            Executors.newCachedThreadPool().execute(runnable);
        }
    }
}