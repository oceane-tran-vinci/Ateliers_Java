package main;

import domaine.Employe;
import domaine.Genre;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ExerciceFunctionalInterface {

  public static List<Employe> employes;

  public static void main(String[] args) {
    employes = new ArrayList<>();

    employes.add(new Employe(Genre.HOMME, 185, "Bob"));
    employes.add(new Employe(Genre.FEMME, 225, "Alice"));
    employes.add(new Employe(Genre.HOMME, 155, "John"));
    employes.add(new Employe(Genre.FEMME, 165, "Carole"));
    employes.add(new Employe(Genre.HOMME, 185, "Alex"));
    employes.add(new Employe(Genre.HOMME, 185, "Bart"));

    exMap();

    exComparator();

    exForEach();

  }

  /**
   * Replacer l'instatiation de la classe EmployeComparator par un lambda
   */
  //le type du paramètre
  private static void exComparator() {
    /* avant :
    employes.sort(new EmployeComparator());
    System.out.println("Employés triés:");
    System.out.println(employes); */

    //Peut aussi écrire : employes.sort((e1, e2) -> e1.getNom().compareTo(e2.getNom()));
    employes.sort(Comparator.comparing(Employe::getNom));
    System.out.println("Employés triés:");
    System.out.println(employes);


  }

  /**
   * Trouver le type du paramètre de la méthode map. Ensuite créer une classe implémentant la
   * functional interface correspondante pour remplacer le lambda en paramètre par une instance de
   * celle-ci.
   */
  //le type du paramètre : Function<Employe, String>
  //Dc créer la classe EmployeNomFunction qui implémente l'interface Function
  private static void exMap() {
    Stream<String> listeNom = employes.stream()
        .filter(e -> e.getGenre() == Genre.HOMME)
        .sorted(Comparator.comparingInt(Employe::getTaille)
            .reversed())
        //avant .map( e -> e.getNom());
        //après
        .map(new EmployeNomFunction());
    listeNom.forEach(System.out::println);
  }


  /**
   * Trouver le type du paramètre de la méthode foreach. Ensuite créer une classe implémentant la
   * functional interface correspondante pour remplacer le lambda en paramètre par une instance de
   * celle-ci.
   */
  //le type du paramètre : Consumer<Employe>
  //Dc créer la classe EmployeeConsumer qui implémente l'interface Consumer
  private static void exForEach() {
    //Avant : employes.forEach(e -> System.out.println(e));
    //Après :
    employes.forEach(new EmployeeConsumer());
  }
}
