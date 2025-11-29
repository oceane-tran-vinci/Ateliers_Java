package server;

import domaine.Query;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class QueryHandler {

  // L'objet Query contient les infos nécessaires : URL + méthode (GET/POST)
  private final Query query;

  // HttpClient utilisé pour envoyer les requêtes HTTP
  private final HttpClient client = HttpClient.newHttpClient();

  // On injecte une Query lors de la création du QueryHandler
  public QueryHandler(Query query) {
    this.query = query;
  }

  // Méthode principale : elle envoie la requête et affiche la réponse.
  // Elle est asynchrone → retourne un CompletableFuture<Void>
  public CompletableFuture<Void> sendQueryAndPrintResponse() {

    // 1. Construction de la requête HTTP à partir de l'URL de la Query
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(query.getUrl()))  // utilisation de l'URL fournie par Query
        .GET()                            // pour ce projet, uniquement GET
        .build();

    // 2. Envoi asynchrone de la requête
    //   → sendAsync retourne un CompletableFuture contenant la réponse
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())

        // 3. Exécution de ce bloc quand la requête est terminée
        //    thenAccept = on consomme la réponse (pas de valeur de retour)
        .thenAccept(response -> {
          System.out.println("Status code : " + response.statusCode());
          System.out.println("HTML :");
          System.out.println(response.body());
        });
  }
}
