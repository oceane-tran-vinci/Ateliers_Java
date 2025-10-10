
package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class ExercicesPanaches {
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

        ExercicesPanaches main = new ExercicesPanaches(transactions);
        main.run();
    }

    private List<Transaction> transactions;

    public ExercicesPanaches(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void run() {
        // Complete the methods below based on the exercise descriptions
        exercice1();
        exercice2();
        exercice3();
        exercice4();
        exercice5();
        exercice6();
    }

    //1. Récupérez la valeur de la transaction avec la plus grande valeur parmi celles effectuées à Cambridge.
    //Si aucune transaction n'a été effectuée à Cambridge, gérez ce cas en conséquence.
    private void exercice1() {
        // TODO: Filter transactions of Cambridge, map to their values, and find max.
        System.out.println("Exercice 1");
        Optional<Integer> s = transactions
            .stream()
            .filter(t -> t.getTrader().getCity().equals("Cambridge"))
            .map(Transaction::getValue)
            .reduce(Integer::max);

        if (s.isPresent()){
            System.out.println(s.get());
        } else {
            System.out.println("Aucune transaction trouvée");
        }
    }

    //2. Déterminez combien de transactions chaque trader basé à Cambridge a effectuées.
    //Affichez les résultats sous la forme du nom de chaque trader suivi du nombre de transactions.
    private void exercice2() {
        // TODO: Filter transactions for traders in Cambridge, group by trader, and count their transactions.
        System.out.println("Exercice 2");
        Map<Trader, Long> s = transactions
            .stream()
            .filter(t -> t.getTrader().getCity().equals("Cambridge"))
            .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));

        s.forEach(
            ((trader, count) -> System.out.println(trader.getName() + ": "+ count + " transactions") )
        );
    }

    //3. Parmi les transactions dont la valeur est supérieure à un certain seuil,
    //identifiez le trader ayant le nom le plus long.
    //Affichez le nom de ce trader. En cas d’exæquo, sélectionnez le premier trader dans la liste.
    private void exercice3() {
        // TODO: Filter transactions over 500, map trader names, sort by name length, find the longest.
        System.out.println("Exercice 3");
        Optional<String> s = transactions
            .stream()
            .filter(t -> t.getValue() > 500)
            .map(t -> t.getTrader().getName())
            .distinct()
            .sorted((n1, n2) -> Integer.compare(n2.length(), n1.length()))
            .reduce((n1, n2) -> n1);

        s.ifPresent(name -> System.out.println("Longest trader name: " + name));
    }

    //4. Calculez la moyenne des valeurs des transactions pour chaque ville où sont basés les traders.
    //Affichez le nom de chaque ville avec la moyenne des valeurs des transactions.
    private void exercice4() {
        // TODO: Group transactions by city, map to transaction values, and compute the average.
        System.out.println("Exercice 4");
        Map<String, Double> s = transactions
            .stream()
            .collect(Collectors.groupingBy(
                t -> t.getTrader().getCity(),
                Collectors.averagingInt(Transaction::getValue)
            ));

        s.forEach((city, avgValue) ->
            System.out.println("Average value in " + city + ": " + avgValue)
        );
    }

    //5. Trouvez la transaction de plus faible valeur parmi celles effectuées à Milan.
    //Si aucune transaction n'a été effectuée à Milan, gérez ce cas de manière appropriée.
    private void exercice5() {
        // TODO: Filter transactions in Milan, map to values, find the min, and handle empty results.
        System.out.println("Exercice 5");
        Optional<Transaction> s = transactions
            .stream()
            .filter(t -> t.getTrader().getCity().equals("Milan"))
            .reduce((t1,t2) -> t1.getValue() < t2.getValue() ? t1 : t2);

        s.ifPresentOrElse(
            transaction -> System.out.println("Min transaction in Milan: "
                + transaction),
            () -> System.out.println("No transactions in Milan")
        );
    }

    //6. Récupérez les transactions et regroupez-les en fonction de leur année.
    //Le résultat doit être une structure qui, pour chaque année, associe une liste de transactions correspondant à cette année.
    private void exercice6() {
        // TODO: group transaction by year
        System.out.println("Exercice 6");
        Map<Integer, List<Transaction>> s = transactions
            .stream()
            .collect(Collectors.groupingBy(Transaction::getYear));

        s.forEach((year, transactionsList) -> {
            System.out.println("Year: " + year);
            transactionsList.forEach(transaction ->
                System.out.println("  " + transaction)
            );
        });
    }
}
