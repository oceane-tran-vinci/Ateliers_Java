package main;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class Main {

  public static void main(String[] args) {
    // Création du client HTTP (Factory newHttpClient)
    HttpClient client = HttpClient.newHttpClient(); // factory

    // Lecture de l'URL au clavier avec try-with-resources
    try (Scanner scanner = new Scanner(System.in)) {

      System.out.print("Entrez une URL : ");
      String url = scanner.nextLine();

      // Construction de la requête GET avec l'URL entrée
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
          .GET()
          .build();

      System.out.println("Envoi de la requête à " + url + "...");

      CompletableFuture<HttpResponse<String>> future =
          client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
      //Envoi asynchrone de la requête
      future.thenAccept(response -> {
        System.out.println("Status code : " + response.statusCode());
        System.out.println("HTML :");
        System.out.println(response.body());
      });

      future.join(); // attendre la fin
    }

  }
}
