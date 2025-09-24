import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class Commande implements Iterable<LigneDeCommande> {
  private static int numeroSuivant = 1;
  private int numero;
  private Client client;
  private LocalDateTime date;
  private ArrayList<LigneDeCommande> lignesCommandes = new ArrayList<LigneDeCommande>();

  public Commande(Client client) {
    this.client = client;
    if (!client.enregistrer(this))
      throw new IllegalArgumentException("impossible de créer une commande pour un client ayant encore une commande en cours");
    this.numero = numeroSuivant++;
    this.date = LocalDateTime.now();
  }

  public Client getClient() {
    return client;
  }

  public boolean estVide(){
    return lignesCommandes.isEmpty();
  }

  public LocalDateTime getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Commande commande = (Commande) o;
    return numero == commande.numero;
  }

  @Override
  public int hashCode() {
    return Objects.hash(numero);
  }

  public boolean ajouter(Pizza pizza, int quantite) {
    if (client.getCommandeEnCours() != this) return false;
    for (LigneDeCommande l :
        lignesCommandes) {
      if (l.getPizza().equals(pizza)) {
        l.setQuantite(l.getQuantite()+quantite);
        return true;
      }
    }
    return lignesCommandes.add(new LigneDeCommande(pizza,quantite));
  }

  public boolean ajouter(Pizza pizza) {
    return this.ajouter(pizza,1);
  }

  public boolean retirer(Pizza pizza, int quantite) {
    if (client.getCommandeEnCours() != this) return false;
    for (LigneDeCommande l :
        lignesCommandes) {
      if (l.getPizza().equals(pizza)) {
        if (l.getQuantite() < quantite) return false;
        if (l.getQuantite() == quantite)
          lignesCommandes.remove(l);
        else l.setQuantite(l.getQuantite()-quantite);
        return true;
      }
    }
    return false;
  }

  public boolean retirer(Pizza pizza) {
    return this.retirer(pizza,1);
  }

  public boolean supprimer(Pizza pizza) {
    if (client.getCommandeEnCours() != this) return false;
    for (LigneDeCommande l : lignesCommandes) {
      if (l.getPizza().equals(pizza)) {
        //on peut supprimer directement car on arrête le parcours juste après la suppression
        lignesCommandes.remove(l);
        return true;
      }
    }
    return false;
  }

  public double calculerMontantTotal(){
    double total = 0;
    for (LigneDeCommande l : lignesCommandes){
      total += l.calculerPrixTotal();
    }
    return total;
  }

  public String detailler(){
    String lignes = "";
    for (LigneDeCommande l : lignesCommandes){
      lignes += l.toString() + "\n";
    }
    if (lignes.equals("")) return lignes;
    return lignes.substring(0,lignes.length()-1);
  }

  @Override
  public Iterator<LigneDeCommande> iterator() {
    return lignesCommandes.iterator();
  }

  @Override
  public String toString() {
    DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    String encours = "";
    if (client.getCommandeEnCours() == this)
      encours = " (en cours)";
    return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
  }
}
