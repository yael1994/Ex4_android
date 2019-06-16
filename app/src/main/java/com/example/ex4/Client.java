package com.example.ex4;

import java.io.*;
import java.net.*;

class Client {
    private String ip;
    private int port;
    private  OutputStream opStream;
    private Socket socket;

    public Client(String ip, int port){

        this.ip = ip;
        this.port = port;
    }

    public void Connect() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Connecting..");
                    socket = new Socket(ip, port);
                    opStream = socket.getOutputStream();
                    System.out.println("Just connected ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }




    public void SendToServer(String msg){
        byte[] byteArray = msg.getBytes();
        try {
            if (socket != null) {
                opStream.write(byteArray, 0, byteArray.length);
                opStream.flush();
            } else {
                System.out.println("client is null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

}