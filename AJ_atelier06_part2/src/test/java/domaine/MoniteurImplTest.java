package domaine;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import be.vinci.ipl.domaine.Moniteur;
import be.vinci.ipl.domaine.MoniteurImpl;
import be.vinci.ipl.domaine.Sport;
import be.vinci.ipl.domaine.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class MoniteurImplTest {

  //besoin de ces 3 variables pour les tests
  // => stage valide (sport dans lequel le moniteur est compétent, stage sans moniteur)
  private Sport sportCompetent;
  private Stage stageValide;
  private Moniteur moniteur;

  @BeforeEach
  void setUp() {
    moniteur = new MoniteurImpl("Lagaffe");
    //appel a Mockito pour Sport et Stage (plus de stub)
    //aller voir part1 + 2.1.2 Mockito de la fiche part2 pour savoir quoi écrire pour chaque Mockito

    //sport part1 avec stub : sportCompetent = new SportStub(true);
    //TODO: Mockito
    sportCompetent = Mockito.mock(Sport.class);
    Mockito.when(sportCompetent.contientMoniteur(moniteur)).thenReturn(true);

    //TODO: Mockito
    //stage part1 avec stub: stageValide = new StageStub(8, sportCompetent, null);
    stageValide = Mockito.mock(Stage.class);
    Mockito.when(stageValide.getNumeroDeSemaine()).thenReturn(8);
    Mockito.when(stageValide.getSport()).thenReturn(sportCompetent);
    Mockito.when(stageValide.getMoniteur()).thenReturn(null);
  }

  private void preparerMoniteurAvecNStages(int nombreDeStages) {
    for (int numSemaine = 1; numSemaine <= nombreDeStages; numSemaine++) {
      // Création d'un mock de Stage pour chaque stage à ajouter

      //part1 avec stub: moniteur.ajouterStage(new StageStub(numSemaine, sportCompetent, null));
      /* 1) Créer Mockito stage,
      2) add tt les conditions pour pour le stage Mockito.when,
      3) ajouter le stage au moniteur*/
      Stage stageAjoute = Mockito.mock(Stage.class);
      Mockito.when(stageAjoute.getSport()).thenReturn(sportCompetent);
      Mockito.when(stageAjoute.getMoniteur()).thenReturn(null);
      Mockito.when(stageAjoute.getNumeroDeSemaine()).thenReturn(numSemaine);
      moniteur.ajouterStage(stageAjoute);
    }
  }

  /* Voir tableau plan de tests */

  @Test
  void TestMoniteurTC1() {
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(1, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
        /*Vérifier qu’une méthode a été appelée (avec le bon paramètre) ou non sur un mock (stage) :
          Mockito.verify(stage).enregistrerMoniteur(moniteur);
          > vérifie que la méthode enregistrerMoniteur a bien été invoquée, avec comme paramètre moniteur, une et une seule fois. */
    );
  }

  @Test
  void TestMoniteurTC2() {
    preparerMoniteurAvecNStages(1);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(2, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC3() {
    preparerMoniteurAvecNStages(2);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(3, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC4() {
    preparerMoniteurAvecNStages(3);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC5() {
    preparerMoniteurAvecNStages(3);
    // première ajout -> ok
    moniteur.ajouterStage(stageValide);
    // tentative d'ajout en double
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages()),
        // enregistrerMoniteur ne doit avoir été appelé qu'une seule fois (pour le premier ajout)
        () -> Mockito.verify(stageValide).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC6() {
    preparerMoniteurAvecNStages(4);
    Stage stageMemeSemaine = Mockito.mock(Stage.class);
    Mockito.when(stageMemeSemaine.getSport()).thenReturn(sportCompetent);
    Mockito.when(stageMemeSemaine.getMoniteur()).thenReturn(null);
    Mockito.when(stageMemeSemaine.getNumeroDeSemaine()).thenReturn(1);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageMemeSemaine)),
        () -> assertFalse(moniteur.contientStage(stageMemeSemaine)),
        () -> assertEquals(4, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageMemeSemaine, Mockito.never()).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC7() {
    preparerMoniteurAvecNStages(4);
    Moniteur autreMoniteur = new MoniteurImpl("Snake");
    Stage stageAutreMoniteur = Mockito.mock(Stage.class);
    Mockito.when(stageAutreMoniteur.getSport()).thenReturn(sportCompetent);
    Mockito.when(stageAutreMoniteur.getMoniteur()).thenReturn(autreMoniteur);
    Mockito.when(stageAutreMoniteur.getNumeroDeSemaine()).thenReturn(8);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageAutreMoniteur)),
        () -> assertFalse(moniteur.contientStage(stageAutreMoniteur)),
        () -> assertEquals(4, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageAutreMoniteur, Mockito.never()).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC8() {
    preparerMoniteurAvecNStages(4);
    Stage stageBonMoniteur = Mockito.mock(Stage.class);
    Mockito.when(stageBonMoniteur.getSport()).thenReturn(sportCompetent);
    Mockito.when(stageBonMoniteur.getMoniteur()).thenReturn(moniteur);
    Mockito.when(stageBonMoniteur.getNumeroDeSemaine()).thenReturn(8);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageBonMoniteur)),
        () -> assertTrue(moniteur.contientStage(stageBonMoniteur)),
        () -> assertEquals(5, moniteur.nombreDeStages()),
        // si le stage a déjà le moniteur, on n'appelle pas enregistrerMoniteur depuis ajouterStage
        () -> Mockito.verify(stageBonMoniteur, Mockito.never()).enregistrerMoniteur(moniteur)
    );
  }

  @Test
  void TestMoniteurTC9() {
    preparerMoniteurAvecNStages(4);
    Sport sportHorsCompetence = Mockito.mock(Sport.class);
    Mockito.when(sportHorsCompetence.contientMoniteur(moniteur)).thenReturn(false);
    Stage stageMauvaisSport = Mockito.mock(Stage.class);
    Mockito.when(stageMauvaisSport.getSport()).thenReturn(sportHorsCompetence);
    Mockito.when(stageMauvaisSport.getMoniteur()).thenReturn(null);
    Mockito.when(stageMauvaisSport.getNumeroDeSemaine()).thenReturn(8);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageMauvaisSport)),
        () -> assertFalse(moniteur.contientStage(stageMauvaisSport)),
        () -> assertEquals(4, moniteur.nombreDeStages()),
        () -> Mockito.verify(stageMauvaisSport, Mockito.never()).enregistrerMoniteur(moniteur)
    );
  }


}