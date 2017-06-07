//package com.example.kimsoohyeong.week14;
//
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.Socket;
//import java.util.Scanner;
//
///**
// * Created by KimSooHyeong on 2017. 6. 7..
// */
//
//public class ChatClient {
//    public static void main(String[] args) {
//        String SERVER_IP = "localhost";
//        int SERVER_PORT = 200;
//        try {
//            Socket aSocket = new Socket(SERVER_IP, SERVER_PORT);
//            System.out.println("Client] 서버 접속");
//
//            Scanner s = new Scanner(System.in);
//            System.out.print("서버에 보낼 데이터를 입력하세요 : ");
//            String data = s.next();
//
//            ObjectOutputStream outstream = new ObjectOutputStream(aSocket.getOutputStream());
//            outstream.writeObject(data);
//            outstream.flush();
//            System.out.println("Client] 전송데이터 : " + data);
//
//            ObjectInputStream instream = new ObjectInputStream(aSocket.getInputStream());
//            Object obj = instream.readObject();
//            System.out.println("Client] 받은데이터 : " + obj);
//
//            aSocket.close();
//            System.out.println("Client 서버 접속 중단");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//}
