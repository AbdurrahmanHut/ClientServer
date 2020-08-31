package com.company;
import java.io.*;
import java.util.Scanner;
import java.net.Socket;

public class Client {

    final String hostName ;
    final int port ;
    private String userName;

    public Client(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void execute(){
        String command;
        try {
            Scanner myObj = new Scanner(System.in);
            Socket socket = new Socket(hostName, port);
            // Obtaining input and output streams
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            System.out.println("Enter User Name: ");
            this.setUserName(myObj.nextLine());
            System.out.println("User Name: "+this.getUserName());
            while (true){
                System.out.println(dis.readUTF());
                command = myObj.nextLine();
                dos.writeUTF(command);
                // If client enter command Exit, Close this connection and then break from the while loop
                if(command.equals("Exit")){
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }
                // Printing response of the Server
                String received = dis.readUTF();
                System.out.println(received);
            }
            // Closing resources
            myObj.close();
            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        String hostname = "localhost";
        int port = 7760;
        Client client = new Client(hostname, port);
        client.execute();
    }
}
