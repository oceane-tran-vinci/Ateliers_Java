package main;

import domaine.QueryFactory;
import server.ProxyServer;

public class Main {

  public static void main(String[] args) {
    QueryFactory queryFactory = new QueryFactory(); // Création de la factory
    ProxyServer server = new ProxyServer(queryFactory); // Injection de la factory dans le serveur
    server.startServer();// Démarrage du serveur

  }
}
