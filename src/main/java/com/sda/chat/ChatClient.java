package com.sda.chat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by RENT on 2017-06-26.
 */
public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.100.185", 1234);

        Scanner socketIn = new Scanner(socket.getInputStream());
        BufferedWriter socketOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        Scanner scanner = new Scanner(System.in);
        String input;
        loginToServer(socketOut, scanner);
        ChatClientReadTask chatClientReadTask = new ChatClientReadTask(socketIn);
        Thread thread = new Thread(chatClientReadTask);
        do {
            input = scanner.nextLine();
            socketOut.write(input + "\n");
            socketOut.flush();
        } while (!"exit".equals(input));
        socket.close();
    }

    private static void loginToServer(BufferedWriter socketOut, Scanner scanner) throws IOException {
        System.out.println("Insert nickName: ");
        String nickName = scanner.nextLine();
        socketOut.write(nickName + "\n");
        socketOut.flush();
    }
}
