package server;

import domaine.Query;
import domaine.QueryFactory;
import java.util.Scanner;

public class ProxyServer {
  QueryFactory queryFactory;

  public ProxyServer(QueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }


  public void startServer() {
    // Scanner pour lire les URL depuis le clavier
    // try-with-resources : le scanner sera automatiquement fermé
    try (Scanner scanner = new Scanner(System.in)) {
      // Boucle infinie : on continue de lire des URLs tant que l'application tourne
      while (true) {
        System.out.print("Entrez une URL : ");
        String url = scanner.nextLine(); // lecture de l'URL tapée par l'utilisateur

        // Création de la Query via la factory
        // On récupère un objet "vide" de type Query (interface)
        Query query = this.queryFactory.getQuery();
        query.setMethod(Query.QueryMethod.GET); // on définit la méthode HTTP GET
        query.setUrl(url);                  // on définit l'URL

        // Création d'un QueryHandler qui s'occupera de faire la requête
        QueryHandler handler = new QueryHandler(query);

        // Envoi asynchrone de la requête
        // La méthode retourne immédiatement, le programme continue de tourner
        handler.sendQueryAndPrintResponse(); // asynchrone
      }
    }
  }
}
