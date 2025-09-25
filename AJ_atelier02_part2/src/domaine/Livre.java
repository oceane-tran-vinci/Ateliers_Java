package domaine;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import util.Util;

public class Livre {

  private Map<Plat.Type, SortedSet<Plat>> plats = new HashMap<>();

  /**
   * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans. Il faut ajouter correctement le
   * plat en fonction de son type.
   *
   * @param plat le plat à ajouter
   * @return true si le plat a été ajouté, false sinon.
   */
  public boolean ajouterPlat(Plat plat) {
    Util.checkObject(plat);
    SortedSet<Plat> plats = this.plats.get(plat.getType());
    // Si c'est le premier plat de ce type, on crée le SortedSet pour ce type dans la Map.
    if (plats == null) {
      plats = new TreeSet<Plat>(new Comparator<Plat>() {
        @Override
        public int compare(Plat o1, Plat o2) {
          int comp = o1.getNiveauDeDifficulte().compareTo(o2.getNiveauDeDifficulte());
          if (comp == 0) return o1.getNom().compareTo(o2.getNom());
          return comp;
        }
      });
      this.plats.put(plat.getType(), plats);
    }
    // On ajoute dans le SortedSet
    return plats.add(plat);
  }

  /**
   * Supprime un plat du livre, s'il est dedans. Si le plat supprimé est le dernier de ce type de
   * plat, il faut supprimer ce type de plat de la Map.
   *
   * @param plat le plat à supprimer
   * @return true si le plat a été supprimé, false sinon.
   */
  public boolean supprimerPlat(Plat plat) {
    Util.checkObject(plat);
    SortedSet<Plat> plats = this.plats.get(plat.getType());
    if (plats == null) return false;
    boolean deleted = plats.remove(plat);
    if (deleted && plats.isEmpty()) {
      this.plats.remove(plat.getType());
    }
    return deleted;

  }

  /**
   * Renvoie un ensemble contenant tous les plats d'un certain type.
   * L'ensemble n'est pas modifable.
   * @param type le type de plats souhaité
   * @return l'ensemble des plats
   */
  public SortedSet<Plat> getPlatsParType(Plat.Type type) {
    // L'ensemble renvoyé ne doit pas être modifiable !
    Util.checkObject(type);
    SortedSet<Plat> platsDuType = this.plats.get(type);
    if (platsDuType == null) return null;
    return Collections.unmodifiableSortedSet(platsDuType);
  }

  /**
   * Renvoie true si le livre contient le plat passé en paramètre. False sinon.
   * Pour cette recherche, un plat est identique à un autre si son type, son niveau de
   * difficulté et son nom sont identiques.
   * @param plat le plat à rechercher
   * @return true si le livre contient le plat, false sinon.
   */
  public boolean contient(Plat plat) {
    // Ne pas utiliser 2 fois la méthode get() de la map, et ne pas déclarer de variable locale !
    Util.checkObject(plat);
    if (this.plats.containsKey(plat.getType())) {
      return this.plats.get(plat.getType()).contains(plat);
    }
    return false;
  }

  /**
   * Renvoie un ensemble contenant tous les plats du livre. Ils ne doivent pas être triés.
   * @return l'ensemble de tous les plats du livre.
   */
  public Set<Plat> tousLesPlats() {
    Set<Plat> plats = new HashSet<Plat>();
    // Ne pas utiliser la méthode keySet() ou entrySet() ici !
    for (Set<Plat> platsDunType : this.plats.values()) {
      plats.addAll(platsDunType);
    }
    return plats;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (Map.Entry<Plat.Type, SortedSet<Plat>> entry : this.plats.entrySet()) {
      str.append(entry.getKey().getNom() + "\n=====\n");
      for (Plat plat : entry.getValue()) {
        str.append(plat.getNom() + "\n");
      }
    }
    return str.toString();
  }
}