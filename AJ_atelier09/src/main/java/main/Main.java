package main;

import server.ProxyServer;

public class Main {

  public static void main(String[] args) {
    ProxyServer server = new ProxyServer();
    server.startServer();

  }
}
