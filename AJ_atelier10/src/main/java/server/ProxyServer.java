package server;

import domaine.Query;
import domaine.QueryFactory;
import java.net.http.HttpClient;
import java.util.Scanner;
import blacklist.BlacklistService;

public class ProxyServer {
  QueryFactory queryFactory;
  private BlacklistService blacklistService; //4.1.3 : Injecter ce service dans ProxyServer

  public ProxyServer(QueryFactory queryFactory, BlacklistService blacklistService) {
    this.queryFactory = queryFactory;
    this.blacklistService = blacklistService;
  }

  public void startServer() {
    // Scanner pour lire les URL depuis le clavier
    // try-with-resources : le scanner sera automatiquement fermé
    try (Scanner scanner = new Scanner(System.in)) {
      // Boucle infinie : on continue de lire des URLs tant que l'application tourne
      System.out.print("Entrez une URL : ");
      while (true) {
        String url = scanner.nextLine(); // lecture de l'URL tapée par l'utilisateur

        // Création de la Query via la factory
        // On récupère un objet "vide" de type Query (interface)
        Query query = this.queryFactory.getQuery();
        query.setMethod(Query.QueryMethod.GET); // on définit la méthode HTTP GET
        query.setUrl(url);                  // on définit l'URL

        // 4.1.3 : Utilisation du BlacklistService pour vérifier la requête.
        // La méthode check(query) retourne TRUE si l'URL est AUTORISÉE.
        if (blacklistService.check(query)) {
          // Si la requête est autorisée (TRUE), le traitement normal continue.
          QueryHandler queryHandler = new QueryHandler(query);
          queryHandler.sendQueryAndPrintResponse();
        } else {
          // Si la requête est rejetée (FALSE - domaine blacklisté), on affiche un message d'erreur.
          System.err.println("Query rejected : domain blacklised !");
        }
      }
    }
  }
}
