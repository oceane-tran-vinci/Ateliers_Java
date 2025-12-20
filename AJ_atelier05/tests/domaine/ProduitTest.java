package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

  private Produit produitSansPrix;
  private Produit produitAvecPrix;
  private Prix prixAucune;
  private Prix prixPub;
  private Prix prixSolde;
  private static final LocalDate DATE_ANNEE_PASSEE = LocalDate.now().minusYears(1);
  private static final LocalDate DATE_MOIS_PASSEE = LocalDate.now().minusMonths(1);
  private static final LocalDate DATE_AUJOURDHUI = LocalDate.now();
  @BeforeEach
  void setUp() {
    produitSansPrix = new Produit("nom1","marque1","rayon1");
    produitAvecPrix = new Produit("nom2","marque2","rayon2");
    prixAucune = new Prix();
    prixPub = new Prix(TypePromo.PUB,10);
    prixSolde = new Prix(TypePromo.SOLDE,30);
    prixAucune.definirPrix(1,20);
    prixAucune.definirPrix(10,10);
    prixPub.definirPrix(3,15);
    produitAvecPrix.ajouterPrix(DATE_ANNEE_PASSEE,prixAucune);
    produitAvecPrix.ajouterPrix(DATE_MOIS_PASSEE,prixPub);
    produitAvecPrix.ajouterPrix(DATE_AUJOURDHUI,prixSolde);
  }

  //Tests des constructeurs et des getters

  @Test
  @DisplayName("Test du constructeur avec un nom null")
  void testProduit1(){
    assertThrows(IllegalArgumentException.class,()->new Produit(null,"marque","rayon"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ","    \n   ","\t\t   "})
  @DisplayName("Test du constructeur avec un nom constitué uniquement de blanc ->IllegalArgumentException attendue")
  void testProduit2(String nom){
    assertThrows(IllegalArgumentException.class,()->new Produit(nom,"marque","rayon"));
  }

  @Test
  @DisplayName("Test du constructeur avec une marque null")
  void testProduit3(){
    assertThrows(IllegalArgumentException.class,()->new Produit("nom",null,"rayon"));
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ","    \n   ","\t\t   "})
  @DisplayName("Test du constructeur avec une marque constitué uniquement de blanc ->IllegalArgumentException attendue")
  void testProduit4(String marque){
    assertThrows(IllegalArgumentException.class,()->new Produit("nom",marque,"rayon"));
  }

  @Test
  @DisplayName("Test du constructeur avec un rayon null")
  void testProduit5(){
    assertThrows(IllegalArgumentException.class,()->new Produit("nom","marque",null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"  ","    \n   ","\t\t   "})
  @DisplayName("Test du constructeur avec un rayon constitué uniquement de blanc ->IllegalArgumentException attendue")
  void testProduit6(String rayon){
    assertThrows(IllegalArgumentException.class,()->new Produit("nom","marque",rayon));
  }

  @Test
  @DisplayName("Vérification de la valeur renvoyée par getNom")
  void getNom() {
    assertEquals("nom1",produitSansPrix.getNom());
  }

  @Test
  @DisplayName("Vérification de la valeur renvoyée par getMarque")
  void getMarque() {
    assertEquals("marque1",produitSansPrix.getMarque());
  }

  @Test
  @DisplayName("Vérification de la valeur renvoyée par getRayon")
  void getRayon() {
    assertEquals("rayon1",produitSansPrix.getRayon());
  }

  //Tests des prix (ajouterPrix et getPrix)

  @Test
  @DisplayName("Test d'ajouterPrix avec une date null")
  void testAjouterPrix1a() {
    assertThrows(IllegalArgumentException.class,()->produitSansPrix.ajouterPrix(null,prixAucune));
  }

  @Test
  @DisplayName("Test d'ajouterPrix avec un prix null")
  void testAjouterPrix1b() {
    assertThrows(IllegalArgumentException.class,()->produitSansPrix.ajouterPrix(DATE_AUJOURDHUI,null));
  }

  @Test
  @DisplayName("Test d'ajouterPrix à une date déjà présente")
  void testAjouterPrix2(){
    assertThrows(DateDejaPresenteException.class,()->produitAvecPrix.ajouterPrix(DATE_MOIS_PASSEE,new Prix()));
  }

  @Test
  @DisplayName("Vérification de l'ajout des prix")
  void testAjouterPrix3()  {
    Prix prixMoisPasse = produitAvecPrix.getPrix(DATE_MOIS_PASSEE);
    Prix prixAujourdhui = produitAvecPrix.getPrix(DATE_AUJOURDHUI);
    assertAll(
        () -> assertEquals(prixPub,produitAvecPrix.getPrix(DATE_MOIS_PASSEE)),
        () -> assertEquals(prixSolde,produitAvecPrix.getPrix(DATE_AUJOURDHUI))
    );
  }

  @Test
  @DisplayName("Test que la méthode getPrix avec une date antérieure aux dates où le prix est défini")
  void testGetPrix4() {
    assertThrows(PrixNonDisponibleException.class,()->produitAvecPrix.getPrix(DATE_ANNEE_PASSEE.minusDays(1)));
  }

  @Test
  @DisplayName("Test que la méthode getPrix sur un produit sans prix défini")
  void testGetPrix5() {
    assertThrows(PrixNonDisponibleException.class,()->produitSansPrix.getPrix(DATE_AUJOURDHUI));
  }

  @Test
  @DisplayName("Test que la méthode getPrix avec une date située entre deux dates de définition de prix")
  public void testGetPrix8() throws PrixNonDisponibleException {
    assertEquals(prixPub, produitAvecPrix.getPrix(DATE_AUJOURDHUI.minusDays(1)));
  }


  //test des méthodes equals et hashCode

  @Test
  @DisplayName("Test que deux produits ayant même nom, marque et rayon sont égaux")
  void testEquals1() {
    Produit produit = new Produit("nom2","marque2","rayon2");
    assertEquals(produitAvecPrix,produit);
  }

  @Test
  @DisplayName("Test que deux produits ayant deux noms différents ne sont pas égaux")
  void testEquals2() {
    Produit produit = new Produit("nom","marque2","rayon2");
    assertNotEquals(produitAvecPrix,produit);
  }

  @Test
  @DisplayName("Test que deux produits ayant deux marques différentes ne sont pas égaux")
  void testEquals3() {
    Produit produit = new Produit("nom2","marque","rayon2");
    assertNotEquals(produitAvecPrix,produit);
  }

  @Test
  @DisplayName("Test que deux produits ayant deux rayons différents ne sont pas égaux")
  void testEquals4() {
    Produit produit = new Produit("nom2","marque2","rayon");
    assertNotEquals(produitAvecPrix,produit);
  }

  @Test
  @DisplayName("Test que deux produits ayant même nom, marque et rayon ont le même hashCode")
  void testHashCode5() {
    Produit produit = new Produit("nom2","marque2","rayon2");
    assertEquals(produitAvecPrix.hashCode(),produit.hashCode());
  }


}