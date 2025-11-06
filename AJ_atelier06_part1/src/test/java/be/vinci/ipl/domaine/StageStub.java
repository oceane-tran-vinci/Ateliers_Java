package be.vinci.ipl.domaine;

import be.vinci.ipl.util.Util;
import java.util.Collections;
import java.util.Set;

public class StageStub implements Stage {
  //mettre final car la variable ne peut pas être modifiée après son initialisation (constructeur)
  //elle est seulement appelé par des méthodes
  private final int getNumeroDeSemaine;
  private final Sport getSport;
  private final Moniteur getMoniteur;

  public StageStub(int getNumeroDeSemaine, Sport getSport, Moniteur getMoniteur) {
    this.getNumeroDeSemaine = getNumeroDeSemaine;
    this.getSport = getSport;
    this.getMoniteur = getMoniteur;
  }

  @Override
  public String getIntitule() {
    return null;
  }

  @Override
  public String getLieu() {
    return null;
  }

  @Override
  public int getNumeroDeSemaine() {
    return getNumeroDeSemaine;
  }

  @Override
  public Sport getSport() {
    return getSport;
  }

  @Override
  public boolean enregistrerMoniteur(Moniteur moniteur) {
    return false;
  }

  @Override
  public boolean supprimerMoniteur() {
    return false;
  }

  @Override
  public Moniteur getMoniteur() {
    return getMoniteur;
  }

  @Override
  public boolean ajouterEnfant(Enfant enfant) {
    return false;
  }

  @Override
  public boolean supprimerEnfant(Enfant enfant) {
    return false;
  }

  @Override
  public boolean contientEnfant(Enfant enfant) {
    return false;
  }

  @Override
  public int nombreDEnfants() {
    return 0;
  }

  @Override
  public Set<Enfant> enfants() {
    return null;
  }
}
