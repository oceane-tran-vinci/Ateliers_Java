package be.vinci.ipl.domaine;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MoniteurImplTest {
  //besoin de ces 3 variables pour les tests
  // => stage valide (sport dans lequel le moniteur est compétent, stage sans moniteur)
  private Sport sportCompetent;
  private Stage stageValide;
  private Moniteur moniteur;

  //donc ne pas oublier de faire le beforeach avec dedans les 3 variables
  @BeforeEach
  void setUp() {
    sportCompetent = new SportStub(true);//ce qu'on a besoin pr tester
    stageValide = new StageStub(8, sportCompetent, null);//ce qu'on a besoin pr tester
    moniteur = new MoniteurImpl("Lagaffe"); //Ce qu'on test
  }

  //ajout de preparerMoniteurAvecNStages pour refactor test 2-4
  private void preparerMoniteurAvecNStages(int nombreDeStages) {
    for (int numSemaine = 1; numSemaine <= nombreDeStages; numSemaine++) {
      moniteur.ajouterStage(new StageStub(numSemaine, sportCompetent, null));
    }
  }

  /* Voir tableau plan de tests */

  @Test
  void TestMoniteurTC1() {
    // état 0 : aucun stage
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(1, moniteur.nombreDeStages())
    );
  }

  /*2-4 Sans refactor */
  @Test
  void TestMoniteurTC2NonRefactor() {
    // état 1 : moniteur a déjà un stage
    StageStub stage1 = new StageStub(1, sportCompetent, null);
    moniteur.ajouterStage(stage1);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(2, moniteur.nombreDeStages())
    );
  }
  @Test
  void TestMoniteurTC3NonRefactor() {
    // état 2 : moniteur a déjà 2 stages
    StageStub stage1 = new StageStub(1, sportCompetent, null);
    StageStub stage2 = new StageStub(2, sportCompetent, null);
    moniteur.ajouterStage(stage1);
    moniteur.ajouterStage(stage2);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(3, moniteur.nombreDeStages())
    );
  }
  @Test
  void TestMoniteurTC4NonRefactor() {
    // état 3 : moniteur a déjà 3 stages
    StageStub stage1 = new StageStub(1, sportCompetent, null);
    StageStub stage2 = new StageStub(2, sportCompetent, null);
    StageStub stage3 = new StageStub(3, sportCompetent, null);
    moniteur.ajouterStage(stage1);
    moniteur.ajouterStage(stage2);
    moniteur.ajouterStage(stage3);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }


  /*Test 2-4 : refactorisé*/
  @Test
  void TestMoniteurTC2Refactor() {
    preparerMoniteurAvecNStages(1); // moniteur a déjà 1 stage
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(2, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC3Refactor() {
    preparerMoniteurAvecNStages(2); // moniteur a déjà 2 stages
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(3, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC4Refactor() {
    preparerMoniteurAvecNStages(3); // moniteur a déjà 3 stages
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageValide)),
        () -> assertTrue(moniteur.contientStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }




  @Test
  void TestMoniteurTC5() {
    preparerMoniteurAvecNStages(3);
    moniteur.ajouterStage(stageValide);
    assertAll(
        () -> assertTrue(moniteur.supprimerStage(stageValide)),
        () -> assertFalse(moniteur.contientStage(stageValide)),
        () -> assertEquals(3, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC6() {
    preparerMoniteurAvecNStages(2);
    moniteur.ajouterStage(stageValide);
    assertAll(
        () -> assertTrue(moniteur.supprimerStage(stageValide)),
        () -> assertFalse(moniteur.contientStage(stageValide)),
        () -> assertEquals(2, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC7() {
    preparerMoniteurAvecNStages(1);
    moniteur.ajouterStage(stageValide);
    assertAll(
        () -> assertTrue(moniteur.supprimerStage(stageValide)),
        () -> assertFalse(moniteur.contientStage(stageValide)),
        () -> assertEquals(1, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC8() {
    moniteur.ajouterStage(stageValide);
    assertAll(
        () -> assertTrue(moniteur.supprimerStage(stageValide)),
        () -> assertFalse(moniteur.contientStage(stageValide)),
        () -> assertEquals(0, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC9() {
    preparerMoniteurAvecNStages(4);
    moniteur.ajouterStage(stageValide);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC10() {
    preparerMoniteurAvecNStages(4);
    Stage stageMemeSemaine = new StageStub(1, sportCompetent, null);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageMemeSemaine)),
        () -> assertFalse(moniteur.contientStage(stageMemeSemaine)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC11() {
    preparerMoniteurAvecNStages(4);
    assertAll(
        () -> assertFalse(moniteur.supprimerStage(stageValide)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC12() {
    preparerMoniteurAvecNStages(4);
    Moniteur autreMoniteur = new MoniteurImpl("Snake");
    Stage stageAutreMoniteur = new StageStub(8, sportCompetent, autreMoniteur);
    assertAll(
        () -> assertFalse(moniteur.ajouterStage(stageAutreMoniteur)),
        () -> assertFalse(moniteur.contientStage(stageAutreMoniteur)),
        () -> assertEquals(4, moniteur.nombreDeStages())
    );
  }

  @Test
  void TestMoniteurTC13() {
    preparerMoniteurAvecNStages(4);
    Stage stageBonMoniteur = new StageStub(8, sportCompetent, moniteur);
    assertAll(
        () -> assertTrue(moniteur.ajouterStage(stageBonMoniteur)),
        () -> assertTrue(moniteur.contientStage(stageBonMoniteur)),
        () -> assertEquals(5, moniteur.nombreDeStages())
    );
  }

}