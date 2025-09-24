import java.util.Objects;

public class Ingredient {
  private String nom;
  private double prix;

  public Ingredient(String nom, double prix) {

    this.nom = nom;
    setPrix(prix); //utiliser la méthode setPrix()
  }

  public String getNom() {
    return nom;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Ingredient that = (Ingredient) o;
    return Double.compare(prix, that.prix) == 0 && Objects.equals(nom, that.nom);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nom, prix);
  }
}
