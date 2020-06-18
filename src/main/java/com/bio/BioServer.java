package com.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * bio demo
 *
 * @author yanzy
 * @date 2020-06-18 10:01
 */
public class BioServer {

    public static void main(String[] args) throws Exception {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务端开启");
        //监听客户端连接
        while (true) {
            final Socket socket = serverSocket.accept();
            //有客户端连接时开启新线程 打印线程信息与客户端信息
            System.out.println("连接到客户端");
            threadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    private static void handler(Socket socket) {
        System.out.println(Thread.currentThread().getId() + "线程名: " + Thread.currentThread().getName());
        try {
            byte[] buffer = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            int read = inputStream.read(buffer);
            while (true) {
                if (read != -1) {
                    System.out.println(new String(buffer, 0, read));
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
