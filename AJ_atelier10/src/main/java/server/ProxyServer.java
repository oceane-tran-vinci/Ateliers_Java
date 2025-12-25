package server;

import blacklist.BlacklistServiceImpl;
import domaine.Query;
import domaine.QueryFactory;
import domaine.QueryFactoryImpl;
import java.util.Scanner;
import blacklist.BlacklistService;
import utils.Inject;

public class ProxyServer {
  //Mnt qu'on a fait un properties pour inject on peut utiliser l'interface
  @Inject
  private QueryFactory queryFactory;       // sera injecté automatiquement
  @Inject
  private BlacklistService blacklistService; // sera injecté automatiquement

  // plus besoin de constructeur avec paramètres

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
          System.err.println("Query rejected : domain blacklisted !");
        }
      }
    }
  }
}
