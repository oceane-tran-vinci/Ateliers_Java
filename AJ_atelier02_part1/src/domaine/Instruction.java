package domaine;

import java.time.Duration;
import util.Util;

public class Instruction {

  private String description;
  private Duration dureeEnMinutes = Duration.ofMinutes(0);

  public Instruction(String description, int duree) {
    Util.checkString(description);
    Util.checkPositiveOrNul(duree);
    this.description = description;
    this.dureeEnMinutes = Duration.ofMinutes(duree);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    Util.checkString(description);
    this.description = description;
  }

  public Duration getDureeEnMinutes() {
    return dureeEnMinutes;
  }

  public void setDureeEnMinutes(Duration dureeEnMinutes) {
    Util.checkObject(dureeEnMinutes);
    Util.checkPositiveOrNul(dureeEnMinutes.toMinutes());
    //En plus des util il faut lancer un IllegalArgumentException
    // si la dureeEnMinutes (Duration) != dureeEnMinutes EN MINUTES (int)
    if (!dureeEnMinutes.equals(Duration.ofMinutes(dureeEnMinutes.toMinutes()))){
      throw new IllegalArgumentException();
    }
    this.dureeEnMinutes = dureeEnMinutes;
  }

  /*2 → largeur minimale de 2 caractères.
    0 → compléter avec des zéros à gauche si l’entier a moins de 2 chiffres.
    Donc : 5 → "05" OU 12 → "12"
    %02d:%02d formate deux entiers (heures et minutes) en style horloge HH:MM avec zéro en tête si besoin.
  */
  @Override
  public String toString() {
    return "(" + String.format("%02d:%02d", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutesPart()) + ") " + description;
  }
}
