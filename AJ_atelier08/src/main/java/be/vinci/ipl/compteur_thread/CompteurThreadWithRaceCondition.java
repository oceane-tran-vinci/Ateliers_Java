package be.vinci.ipl.compteur_thread;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class CompteurThreadWithRaceCondition extends Thread {

    private final String nom;
    private final int max;
    // TODO : Veuillez ajouter une variable de classe permettant de retenir quel CompteurThread
    //   a fini de compter le premier.
    private static CompteurThreadWithRaceCondition gagnant;

    public CompteurThreadWithRaceCondition(String nom, int max) {
        this.nom = nom;
        this.max = max;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public void run() {
        count();
    }

    public void count() {
        //TODO: Modifier ce code pour déterminer le gagnant (le 1er qui a fini de compter)
        //      et lors de l’enregistrement du gagnant, veuillez attendre 10 ms avant de l’enregistrer et afficher
        //      le nom du gagnant sous cette forme : "Le compteur gagnant est XXX à 2024-10-25T15:20:16.109588".

        // Boucle de 1 à max pour compter
        // Affiche le compteur et met une pause de 10ms à chaque étape
        IntStream.rangeClosed(1, max).forEach(i -> {
            System.out.println(nom + " : " + i);
            try {
                Thread.sleep(10);
                // Bloc synchronized : un seul thread peut entrer pour déterminer le gagnant
                synchronized (CompteurThreadWithRaceCondition.class) {
                    // Si le compteur vient de finir et qu'aucun gagnant n'a été enregistré
                    if (i == max && gagnant == null) {
                        Thread.sleep(10); // Pause supplémentaire pour le rendu visuel
                        gagnant = this; // Ce thread (this) devient le gagnant
                        //ou System.currentTimeMillis() peut importe car ds test on changera en ms
                        System.out.println("Le compteur gagnant est " + gagnant.getNom() + " à " + LocalDateTime.now());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(nom + " a finit de compter jusqu'à " + max + " à " + LocalDateTime.now());
    }

    public static CompteurThreadWithRaceCondition getGagnant() {
        return gagnant;
    }
}
