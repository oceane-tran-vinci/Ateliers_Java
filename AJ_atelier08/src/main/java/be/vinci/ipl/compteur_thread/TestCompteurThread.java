package be.vinci.ipl.compteur_thread;

import java.time.LocalDateTime;

public class TestCompteurThread {

    public static void main(String[] args) {
        CompteurThread[] compteurs = {new CompteurThread("Bolt", 10), new CompteurThread("Jakson", 10), new CompteurThread("Robert", 10), new CompteurThread("Stéphanie", 10)};
        //Changer ce start : LocalDateTime start = LocalDateTime.now(); par ça
        //pour obtenir directement le temps en millisecondes avec des long
        long start = System.currentTimeMillis();

        for (int i = 0; i < compteurs.length; i++) {
            //TODO: lancer les compteurs
            compteurs[i].start(); // démarre chaque compteur en parallèle
        }


        for (int i = 0; i < compteurs.length; i++) {
            //TODO: attendre la fin de l'exécution de tous les compteurs
            //		pour attendre un thread t, utiliser t.join();
            try {
                compteurs[i].join(); // attend la fin de ce thread
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // si le thread principal est interrompu
            }
        }

        //Changer aussi le end LocalDateTime end = LocalDateTime.now(); par
        long end = System.currentTimeMillis();

        //Donc long duration = java.time.Duration.between(start, end).toMillis(); devient
        long duration = end - start;
        System.out.println("Tout le monde a fini de compter !");
        System.out.println("Durée avant d'atteindre cette instruction de fin du programme principal : " + duration + " ms");

    }

}
