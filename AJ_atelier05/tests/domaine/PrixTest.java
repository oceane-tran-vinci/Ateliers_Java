package domaine;

import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PrixTest {
  private Prix prixAucune;
  private Prix prixPub;
  private Prix prixSolde;

  @BeforeEach
  void setUp() {
    prixAucune = new Prix();
    prixPub = new Prix(TypePromo.PUB,10);
    prixSolde = new Prix(TypePromo.SOLDE,30);
    prixAucune.definirPrix(1,20);
    prixAucune.definirPrix(10,10);
    prixPub.definirPrix(3,15);
  }

  //Tests du constructeur

  //Vérifiez que le constructeur de la classe Prix lance une IllegalArgumentException
  //si le type de la promo passée en paramètre est null.
  @Test
  @DisplayName("Test du constructeur avec paramètre Promo null")
  void testPrix1(){
    assertThrows(IllegalArgumentException.class,() -> new Prix(null,15));
  }

  //Vérifiez que le constructeur de la classe Prix lance une IllegalArgumentException
  //si la valeur passée en paramètre est <= 0 (faites un test paramétré afin de faire le test
  //avec plusieurs valeurs).
  @ParameterizedTest
  @DisplayName("Test du constructeur avec valeur <0")
  @ValueSource(doubles={-7,-4,0})
  void testPrix2(double valeur){
    assertThrows(IllegalArgumentException.class,() -> new Prix(TypePromo.SOLDE,valeur));
  }

  // Tests des getters

  //1. Vérifiez que la valeur de la promo est initialisée à 0 lors de l’instanciation d’un prix au
  //moyen du constructeur sans paramètre.
  //2. Vérifiez que la valeur de la promo correspond bien à celle passée en paramètre du constructeur.
  @Test
  @DisplayName("Vérification de la valeur renvoyée par getValeurPromo")
  void testGetValeurPromo() {
    assertAll(()->assertEquals(0,prixAucune.getValeurPromo()),
        ()-> assertEquals(10,prixPub.getValeurPromo()),
        () ->assertEquals(30,prixSolde.getValeurPromo()));
  }

  //3. Vérifiez que le type de la promo est null lors de l’instanciation d’un prix au moyen du constructeur sans paramètre.
  //4. Vérifiez que le type de la promo correspond bien à celle passée en paramètre du  constructeur.
  @Test
  @DisplayName("Vérification de la valeur renvoyée par getTypePromo")
  void testGetTypePromo() {
    assertAll(()->assertNull(prixAucune.getTypePromo()),
        ()-> assertSame(TypePromo.PUB,prixPub.getTypePromo()),
        () ->assertSame(TypePromo.SOLDE,prixSolde.getTypePromo()));
  }

  //Tests de définirPrix

  //Vérifiez que la méthode definirPrix lance une IllegalArgumentException si le
  //paramètre quantité est 0 ou négatif.
  @ParameterizedTest
  @ValueSource(ints={-1,0})
  @DisplayName("Test de la méthode getPrix avec une quantité < ou = à 0")
  void definirPrix1(int quantite) {
    assertThrows(IllegalArgumentException.class,()->prixPub.definirPrix(quantite,15));
  }

  //Vérifiez que la méthode definirPrix lance une IllegalArgumentException si le
  //paramètre prix unitaire est 0 ou négatif.
  @ParameterizedTest
  @ValueSource(doubles={-3,0})
  @DisplayName("Test de la méthode getPrix avec une valeur < ou = à 0")
  void definirPrix2(double valeur) {
    assertThrows(IllegalArgumentException.class,()->prixPub.definirPrix(15,valeur));
  }

  //Définissez un prix de 6 euros à partir de 10 unités pour l’attribut prixAucune et vérifiez
  //que l’ancien prix a été remplacé.
  @Test
  @DisplayName("Test du remplacement de la valeur pour une quantité dèjà existante")
  void definirPrix3() {
    prixAucune.definirPrix(10,6);
    assertEquals(6,prixAucune.getPrix(10));
  }

  //Test de getPrix

  //Vérifiez que la méthode lance une IllegalArgumentException si le paramètre
  //quantité est négatif ou nul.
  @ParameterizedTest
  @ValueSource(ints = {-1,0})
  @DisplayName("Test de la méthode getPrix avec une quantité < ou = à 0")
  void testGetPrix1(int quantite) {
    assertThrows(IllegalArgumentException.class,()->prixPub.getPrix(quantite));
  }

  //Pour prixAucune, le prix est de 20 euros pour une quantité comprise entre 1 et 9 (bornes incluses)
  @ParameterizedTest
  @ValueSource(ints = {1,5,9})
  @DisplayName("Test de la méthode getPrix pour prixAucune avec les quantités 1,5,9")
  void testGetPrix2a(int quantite) throws QuantiteNonAutoriseeException {
    assertEquals(20,prixAucune.getPrix(quantite));
  }

  //Pour prixAucune, le prix est de 10 euros pour une quantité supérieure ou égale à 10
  @ParameterizedTest
  @ValueSource(ints = {10,15,20,25})
  @DisplayName("Test de la méthode getPrix pour prixAucune avec des quantités >=10")
  void testGetPrix2b(int quantite)  {
    assertEquals(10,prixAucune.getPrix(quantite));
  }

  //Testez qu’une QuantiteNonAutoriseeException est lancée si vous demandez le prix
  //pour 2 unités pour l’attribut prixPub.
  @Test
  @DisplayName("Test de getPrix avec une quantite < à la plus petite quantité minimale")
  void testGetPrix3() {
    assertThrows(QuantiteNonAutoriseeException.class,()->prixPub.getPrix(2));
  }

  //Testez qu’une QuantiteNonAutoriseeException est lancée si vous demandez le prix
  //pour 1 unité pour l’attribut prixSolde.
  @Test
  @DisplayName("Test de getPrix s'il n'y a pas de prix défini")
  void testGetPrix4() {
    assertThrows(QuantiteNonAutoriseeException.class,()->prixSolde.getPrix(1));
  }

}