public class Client {
  private static int numeroSuivant = 1;
  private int numero;
  private String nom, prenom, telephone;

  public Client(String nom, String prenom, String telephone) {
    this.nom = nom;
    this.prenom = prenom;
    this.telephone = telephone;

    this.numero = numeroSuivant++;
  }

  public int getNumero() {
    return numero;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }

  public String getTelephone() {
    return telephone;
  }

  @Override
  public String toString() {
    return "client n° " + numero + " (" + prenom  + " " + nom + ", telephone : " + telephone +")";
  }
}
