package com.company.Thread;
import java.io.*;
import java.net.Socket;

public class UserThread extends Thread{

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    public UserThread(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }
    public void run() {
        String command = "";
        String response = "";
        while (true){
            try {
                dos.writeUTF("Enter Command: ");
                // Recieve the command to be run on Server from the Client
                command = dis.readUTF();

                if(command.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                //Execute the command on Server
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(command);
                p.waitFor();
                BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
                StringWriter stringWriter = new StringWriter();
                PrintWriter writer = new PrintWriter(stringWriter, true);
                while ((response = b.readLine()) != null) {
                    writer.println(response);
                }
                dos.writeUTF(stringWriter.toString());
                b.close();
                // Write on output of the command
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        try
        {
            // Closing resources
            this.dis.close();
            this.dos.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
