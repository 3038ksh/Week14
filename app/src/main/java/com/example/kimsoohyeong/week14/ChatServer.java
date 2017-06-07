package com.example.kimsoohyeong.week14;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by KimSooHyeong on 2017. 6. 7..
 */

public class ChatServer {

    public static void main(String[] args) {
        int SERVER_PORT = 200;
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Server]서버가 실행됨.  포트 : " + SERVER_PORT);

            while (true) {
                Socket aSocket = server.accept();
                System.out.println("Server]클라이언트 연결 : " + aSocket.getLocalAddress());

                ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
                Object obj = instream.readObject();
                System.out.println("Server]받은 메세지 : " + obj);

                ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
                outstream.writeObject("from Server >> " + obj);
                outstream.flush();
                System.out.println("Server]보낸 메세지 : from Server >> " + obj);

                aSocket.close();
                System.out.println("Server]클라이언트 접속 종료");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
