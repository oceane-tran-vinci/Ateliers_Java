package server;

import domaine.Query;
import java.util.Scanner;

public class ProxyServer {

  public void startServer() {
    // Scanner pour lire les URL depuis le clavier
    // try-with-resources : le scanner sera automatiquement fermé
    try (Scanner scanner = new Scanner(System.in)) {
      // Boucle infinie : on continue de lire des URLs tant que l'application tourne
      while (true) {
        System.out.print("Entrez une URL : ");
        String url = scanner.nextLine(); // lecture de l'URL tapée par l'utilisateur

        Query query = new Query(url, Query.QueryMethod.GET); // Création de la Query avec l'URL et méthode GET
        QueryHandler handler = new QueryHandler(query); // Création d'un QueryHandler qui s'occupera de faire la requête

        // On envoie la requête de manière asynchrone
        // La méthode retourne immédiatement, le programme continue de tourner
        handler.sendQueryAndPrintResponse(); // asynchrone
      }
    }
  }
}
