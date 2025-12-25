package main;

import blacklist.BlacklistService;
import blacklist.BlacklistServiceImpl;
import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import server.ProxyServer;
import utils.Injector;

public class Main {

  public static void main(String[] args) throws Exception {
    ProxyServer server = new ProxyServer(); // sans dépendances
    Injector.inject(server); // injection automatique
    server.startServer();// Démarrage du serveur
  }
}
