import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    static ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(3345))
        {
            System.out.println("Start!!!");
            while(!server.isClosed())
            {
                Socket client = server.accept();
                executorService.execute(new MonoThreadClientHandler(client));
                System.out.println("Connect accepted");
            }

            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
