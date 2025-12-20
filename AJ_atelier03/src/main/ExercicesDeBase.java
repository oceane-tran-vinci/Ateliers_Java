package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ExercicesDeBase {

  /**
   * La liste de base de toutes les transactions.
   */
  private List<Transaction> transactions;

  public static void main(String[] args) {
    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario", "Milan");
    Trader alan = new Trader("Alan", "Cambridge");
    Trader brian = new Trader("Brian", "Cambridge");

    List<Transaction> transactions = Arrays.asList(
        new Transaction(brian, 2011, 300),
        new Transaction(raoul, 2012, 1000),
        new Transaction(raoul, 2011, 400),
        new Transaction(mario, 2012, 710),
        new Transaction(mario, 2012, 700),
        new Transaction(alan, 2012, 950)
    );
    System.out.println("Les transactions " + transactions);
    ExercicesDeBase main = new ExercicesDeBase(transactions);
    main.run();
  }


  /**
   * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point
   * de l'énoncé
   *
   * @param transactions la liste des transactions
   */
  public ExercicesDeBase(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  /**
   * Exécute chaque point de l'énoncé
   */
  public void run() {
    this.predicats1();
    this.predicats2();
    this.predicats3();
    this.map1();
    this.map2();
    this.map3();
    this.sort1();
    this.sort2();
    this.reduce1();
    this.reduce2();
  }

  //Construire la liste de toutes les transactions de 2011
  private void predicats1() {
    System.out.println("predicats1");
    Stream<Transaction> s = transactions
        .stream() // TODO filtrer
        .filter(t -> t.getYear() == 2011);
    System.out.println("sout du Stream brut" + s);
    s.forEach(System.out::println);
  }

  //Construire la liste de toutes les transactions dont la valeur est > 600
  private void predicats2() {
    System.out.println("predicats2");
    var s = transactions
        .stream() // TODO filtrer
        .filter(t -> t.getValue() > 600);
    s.forEach(System.out::println);
  }


  //Construire la liste de toutes les transactions de Raou
  private void predicats3() {
    System.out.println("predicats3");
    var s = transactions
        .stream() // TODO filtrer
        .filter(t -> t.getTrader().getName() == "Raoul");
    s.forEach(System.out::println);
  }

  //Construire une liste des courtiers (traders).
  private void map0() {
    System.out.println("map0");
    // TODO transformer
    var s = transactions
        .stream()
        .map(Transaction::getTrader)
        .distinct()
        .toList();
    s.forEach(System.out::println);

  }

  //Construire la liste des villes où travaillent les courtiers.
  private void map1() {
    System.out.println("map1");
    // TODO transformer
    var s = transactions
        .stream()
        .map(t -> t.getTrader().getCity())
        .distinct()
        .toList();
    s.forEach(System.out::println);

  }

  //Construire la liste de tous les courtiers de Cambridge
  private void map2() {
    System.out.println("map2");
    // TODO transformer
    var s = transactions
        .stream()
        .map(Transaction::getTrader)
        .distinct()
        .filter(t -> t.getCity().equals("Cambridge"))
        .toList();
    s.forEach(System.out::println);

  }

  //Construire une String contenant tous les noms des traders séparés par une virugle.
  //Comme il s’agit d’une String et non d’une liste, utilisez la méthode joining() dans
  //votre collect().
  private void map3() {
    System.out.println("map3");
    var transcTriees = transactions
        .stream() // TODO trier
        .map(t -> t.getTrader().getName())
        .distinct()
        .collect(Collectors.joining(", "));
    System.out.println(transcTriees);
  }

  //Construire la liste de toutes les transactions triées par ordre décroissant de valeurs.
  private void sort1() {
    System.out.println("sort1");
    var transcTriees = transactions
        .stream() // TODO trier
        .sorted(Comparator.comparingInt(Transaction::getValue))
        .toList();
    transcTriees.forEach(System.out::println);
  }

  //Construire une String contenant tous les noms de traders triés par ordre alphabétique.
  private void sort2() {
    System.out.println("sort2");
    var nomsTries = transactions
        .stream() // TODO trier
        .map(t -> t.getTrader().getName())
        .distinct()
        .sorted()
        .collect(Collectors.joining(", "));
    System.out.println(nomsTries);
  }

  //Afficher la valeur max des transactions
  private void reduce1() {
    System.out.println("reduce1");
    // TODO reduce
    Integer s = transactions
        .stream()
        .map(Transaction::getValue)
        .reduce(0, Integer::max);
    System.out.println(s);
  }

  /*Afficher la transaction dont la valeur est la plus petite. Attention : on demande bien
    d’afficher la transaction et non sa valeur ! Vous ne pouvez pas utiliser la méthode
    min. Au besoin, créer une transaction « neutre » avec une valeur de
    Integer.MAX_VALUE.
   */
  private void reduce2() {
    System.out.println("reduce2");
    // TODO reduce
    //Vous ne pouvez pas utiliser la méthode min.
    //Au besoin, créer une transaction « neutre » avec une valeur de Integer.MAX_VALUE.
    Transaction transNeutre = new Transaction(null, 20000, Integer.MAX_VALUE);
    Transaction minTransaction = transactions
        .stream()
        .reduce(transNeutre, (t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
    System.out.println(minTransaction);
  }

}