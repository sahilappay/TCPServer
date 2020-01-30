import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file name or file path:");
        String fileName = sc.nextLine();
        connectServerForFileNew(fileName);
    }

    public static void connectServer() throws Exception{
        ServerSocket ourFirstSocket = new ServerSocket(6789);// localhost ve ya 127.0.0.1
        while (true){
            Socket connectionSocket = ourFirstSocket.accept();
            InputStream is = connectionSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);// melumati qebul edir
            BufferedReader bfr = new BufferedReader(isr);
            String clientMessage = bfr.readLine();
            System.out.println(clientMessage);
            System.out.println("File accepted!");
        }
    }

    public static void connectServerForFile(String fileName) throws Exception{
        ServerSocket ourFirstSocket = new ServerSocket(6789);
        while (true){
            Socket connectionSocket = ourFirstSocket.accept();
            InputStreamReader isr = new InputStreamReader(connectionSocket.getInputStream());
            InputStream in = connectionSocket.getInputStream();
            OutputStream os = new FileOutputStream(fileName);
            byte [] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = in.read(buffer)) != -1 ){
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            System.out.println("File accepted!");
        }
    }

    public static void writeUsingPath(String data,String filePath, boolean append) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, data.getBytes(), StandardOpenOption.APPEND);
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public static void writeUsingPath(String data,String filePath) {
        writeUsingPath(data,filePath, false);
    }
    
    ////////////////NEW METHODS\\\\\\\\\\\\\\\\\
    
    
     public static void connectServerForFileNew(String fileName) throws Exception{
        ServerSocket ourFirstSocket = new ServerSocket(6789);// localhost ve ya 127.0.0.1
        while (true){
            Socket connectionSocket = ourFirstSocket.accept();
            InputStream is = connectionSocket.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            int length = dis.readInt();
            byte[] bytes = new byte[length];
            dis.readFully(bytes);
            writeBytes(fileName, bytes);
            System.out.println("File accepted!");
        }
    }
    
      public static void writeBytes(String fileName, byte[] data) throws Exception {
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
//            byte[] bytes = object.toString().getBytes();
            fos.write(data);
            fos.flush(); // topladigi melumatlari yazir ve prosesi tesdiqleyir
        }
//        System.out.println("Done");
    }
}
