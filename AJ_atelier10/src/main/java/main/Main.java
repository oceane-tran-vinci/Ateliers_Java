package main;

import blacklist.BlacklistService;
import blacklist.BlacklistServiceImpl;
import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import server.ProxyServer;

public class Main {

  public static void main(String[] args) {
    QueryFactory queryFactory = new QueryFactoryImpl(); // Création de la factory
    BlacklistService blacklistService = new BlacklistServiceImpl(); //4.1.3 : Création de la dépendance BlacklistService
    ProxyServer server = new ProxyServer(queryFactory, blacklistService); // Injection de la factory et BlacklistService dans le serveur
    server.startServer();// Démarrage du serveur

  }
}
