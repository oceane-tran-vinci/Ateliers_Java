package be.vinci.ipl.compteur_future;

import be.vinci.ipl.compteur_thread.CompteurThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestCompteurEtPositionCompletableFuture {

  private static long start;
  private static long end;
  private static long duration;
  private static CompletableFuture[] futures;

  public static void main(String[] args) {

    CompteurEtPosition[] compteurs = {new CompteurEtPosition("Bolt", 10),
        new CompteurEtPosition("Jakson", 10),
        new CompteurEtPosition("Robert", 10), new CompteurEtPosition("Stéphanie", 10)};

    System.out.println(
        "0. Exécuter tous les compteurs et déterminer la position de manière synchrone");
    start = System.currentTimeMillis();

    Arrays.asList(compteurs).forEach(compteur -> System.out.println(
        "Compteur : " + compteur.getNom() + " - Position : " + compteur.countAndGetPosition()));

    end = System.currentTimeMillis();
    duration = end - start;
    System.out.println(
        "0. Durée d'exécution pour tous les compteurs de manière synchrone : " + duration + " ms");

    //ces 3 lignes était déjà là mais ça fait partie du TODO1
    System.out.println("1. Récupérer les valeurs de toutes les futures countAndGetPositionAsync");
    CompteurEtPosition.resetOrdreArrivee();
    start = System.currentTimeMillis();

    // TODO : 1. Exécuter tous les compteurs et déterminer la positon de manière asynchrone
    //  en faisant appel à la méthode countAndGetPositionAsync
    futures = Arrays
        .stream(compteurs)
        .map(compteur -> compteur.countAndGetPositionAsync().thenAccept(
            position -> System.out.println(
                "Compteur : " + compteur.getNom() + " - Position : " + position))
        )
        .toArray(CompletableFuture[]::new);
    CompletableFuture.allOf(futures).join();
    //Fin TODO1

    end = System.currentTimeMillis();
    duration = end - start;
    System.out.println("1. Durée d'exécution : " + duration + " ms");

    //ces 3 lignes était déjà là mais ça fait partie du TODO2
    System.out.println("2. Créer des futures à partir de countAndGetPosition");
    CompteurEtPosition.resetOrdreArrivee();
    start = System.currentTimeMillis();

    // TODO : 2. Exécuter tous les compteurs et déterminer la position de manière asynchrone
    //  en faisant appel à la méthode countAndGetPosition
    futures = Arrays
        .stream(compteurs)
        .map(compteur -> CompletableFuture.runAsync(() ->
          { System.out.println("Compteur : " + compteur.getNom() + " - Position : "+ compteur.countAndGetPosition());})
        )
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();
    //Fin TODO2

    end = System.currentTimeMillis();
    duration = end - start;
    System.out.println("2. Durée d'exécution : " + duration + " ms");
  }


}
