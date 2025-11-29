package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) {
    // 1. Création du client HTTP (Factory newHttpClient)
    HttpClient client = HttpClient.newHttpClient(); // factory

    // 2. Création de la requête GET
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://openjdk.org/"))
        .GET()
        .build();

    System.out.println("Envoi de la requête à https://openjdk.org/...");
    // 3. Envoi asynchrone de la requête
    CompletableFuture<HttpResponse<String>> future =
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

    // 4. Traitement du résultat lorsque la requête est terminée
    future.thenAccept(response -> {
      System.out.println("Status code : " + response.statusCode());
      System.out.println("HTML :");
      System.out.println(response.body());
    });

    // Important : empêcher la JVM de s'arrêter avant la fin de la requête
    future.join();

  }
}
