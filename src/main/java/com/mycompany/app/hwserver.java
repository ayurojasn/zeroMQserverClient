//  Hello World server in Java
//  Binds REP socket to tcp://*:5555
//  Expects "Hello" from client, replies with "World"
package com.mycompany.app;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class hwserver
{
  public static void main(String[] args) throws Exception
  {
    try (ZContext context = new ZContext()) {
      //  Socket to talk to clients
      ZMQ.Socket socket = context.createSocket(SocketType.REP);
      socket.bind("tcp://*:8080");

      while (!Thread.currentThread().isInterrupted()) {
        byte[] reply = socket.recv(0);
        System.out.println(
          "Received " + ": [" + new String(reply, ZMQ.CHARSET) + "]"
        );
        String response = "world";
        socket.send(response.getBytes(ZMQ.CHARSET), 0);
        Thread.sleep(1000); //  Do some 'work'
      }
    }
  }
}