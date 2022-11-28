
import java.io.DataInputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MonoThreadClientHandler implements Runnable {
    public Socket clientDialog;
    public MonoThreadClientHandler(Socket client) {
        this.clientDialog=client;
    }

    @Override
    public void run() {
        try{
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            System.out.println("DataInputStream created");

            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());

            while (!clientDialog.isClosed())
            {
                System.out.println("Server reading from channel");

                String entry = in.readUTF();

                System.out.println("READ from clientDialog message - " + entry);

                if(entry.equalsIgnoreCase("quit"))
                {
                    System.out.println("Client initialize connections suicide ...");
                    out.writeUTF("Server reply - " + entry + " - OK");
                    Thread.sleep(3000);
                    break;
                }
                System.out.println("Server try writing to channel");
                out.writeUTF("Server reply - " + entry + " - OK");
                System.out.println("Server Wrote message to clientDialog.");

                out.flush();
            }

            System.out.println("Client disconnected");
            System.out.println("Closing connections & channels.");

            in.close();
            out.close();

            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
