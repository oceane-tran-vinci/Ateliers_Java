package server;

import domaine.Query;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class QueryHandler {

  // L'objet Query contient les infos nécessaires : URL + méthode (GET/POST)
  private Query query;

  // On injecte une Query lors de la création du QueryHandler
  public QueryHandler(Query query) {
    this.query = query;
  }

  // Méthode principale : elle envoie la requête et affiche la réponse.
  // Elle est asynchrone → retourne un CompletableFuture<Void>
  public CompletableFuture<Void> sendQueryAndPrintResponse() {
    // Vérification : pour l'instant, seule la méthode GET est supportée
    if (query.getMethod() != Query.QueryMethod.GET) {
      throw new IllegalStateException("Only GET method is currently supported");
    }

    // HttpClient utilisé pour envoyer les requêtes HTTP
    HttpClient client = HttpClient.newHttpClient();

    // 1. Construction de la requête HTTP à partir de l'URL de la Query
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(query.getUrl()))  // utilisation de l'URL fournie par Query
        .GET()                            // pour ce projet, uniquement GET
        .build();

    // 2. Envoi asynchrone de la requête
    //   → sendAsync retourne un CompletableFuture contenant la réponse
    return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
        // 3. Premier traitement après réception de la réponse
        .thenApply(response -> {
          // Simuler un long traitement (optionnel)
          try {
            Thread.sleep(10000);
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          // Affiche le code HTTP (200, 404, etc.)
          System.out.println("\n=== Requête terminée ===");
          System.out.println("URL : " + query.getUrl());
          System.out.println("Status code : " + response.statusCode());
          return response;
        })
        .thenApply(HttpResponse::body)//On récupère uniquement le corps (HTML) de la réponse
        .thenAccept(System.out::println); //On affiche le contenu du site dans la console
  }
}
