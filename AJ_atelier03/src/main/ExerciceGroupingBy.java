package main;


import domaine.Trader;
import domaine.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExerciceGroupingBy {
    enum TransactionsLevel {
        VERY_HI, HI, LO, ME;
    }

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
        ExerciceGroupingBy main = new ExerciceGroupingBy(transactions);
        main.run();
    }

    /**
     * La liste de base de toutes les transactions.
     */
    private List<Transaction> transactions;
    // Pour tester le vide
    private List<Transaction> pasDeTransaction = new ArrayList<>();
    /**
     * Crée un objet comprenant toutes les transactions afin de faciliter leur usage pour chaque point de l'énoncé
     *
     * @param transactions la liste des transactions
     */
    public ExerciceGroupingBy(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void run() {
        this.groupBy1();
        this.groupBy2();
        this.groupBy3();
    }

    //Map<Trader, List<Transaction>> Transactions du trader
    private void groupBy1() {
        System.out.println("GroupBy1");
        Map<Trader, List<Transaction>> s = transactions
            .stream()
            .collect(Collectors.groupingBy(Transaction::getTrader));
        System.out.println(s);
    }

    //Map<Trader,Long> (nombre de transactions de ce trader). Pour ceci il faudra
    //utiliser la méthode counting() dans le collector.
    private void groupBy2() {
        System.out.println("GroupBy2");
        Map<Trader,Long> s = transactions
            .stream()
            .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
        System.out.println(s);
    }

    //Map<TransactionsLevel,List<Transaction>>
    /*Considérant l’enum TransactionsLevel {VERY_HI, HI, LO, ME}, les transactions
    sont réparties selon les critères suivants :
    • si valeur >= 1000, elle est VERY_HI ;
    • si 800 <= valeur <1000, elle est HI ;
    • si 600 <= valeur < 800, elle est ME ;
    • sinon elle est LO.
    La map ne doit pas être triée.
    */
    private void groupBy3() {
        System.out.println("GroupBy3");
        Map<TransactionsLevel,List<Transaction>> s = transactions
            .stream()
            .collect(
                Collectors.groupingBy((Transaction tr) -> {
                    if (tr.getValue() >= 1000){
                        return TransactionsLevel.VERY_HI;
                    } else if (tr.getValue() >= 800){
                        return TransactionsLevel.HI;
                    } else if (tr.getValue() >= 600) {
                        return TransactionsLevel.ME;
                    }
                    return TransactionsLevel.LO;
                })
            );
        System.out.println(s);
    }
}
