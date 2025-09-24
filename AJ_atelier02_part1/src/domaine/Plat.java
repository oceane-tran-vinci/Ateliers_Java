package domaine;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import util.Util;

public class Plat {

  public enum Difficulte {
    X, XX, XXX, XXXX, XXXXX;

    @Override
    public String toString() {
      return super.toString().replace("X", "*");
    }
  }

  public enum Cout {
    $, $$, $$$, $$$$, $$$$$;

    @Override
    public String toString() {
      return super.toString().replace("$", "€");
    }
  }

  private String nom;
  private int nbPersonnes;
  private Difficulte niveauDeDifficulte;
  private Cout cout;
  private Duration dureeEnMinutes = Duration.ofMinutes(0);

  private List<Instruction> recetteInstructions = new ArrayList<>();
  private Set<IngredientQuantifie> ingredientQuantifies = new HashSet<>();

  public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout) {
    Util.checkString(nom);
    Util.checkStrictlyPositive(nbPersonnes);
    Util.checkObject(niveauDeDifficulte);
    Util.checkObject(cout);
    this.nom = nom;
    this.nbPersonnes = nbPersonnes;
    this.niveauDeDifficulte = niveauDeDifficulte;
    this.cout = cout;
  }

  public String getNom() {
    return nom;
  }

  public int getNbPersonnes() {
    return nbPersonnes;
  }

  public Difficulte getNiveauDeDifficulte() {
    return niveauDeDifficulte;
  }

  public Cout getCout() {
    return cout;
  }

  public Duration getDureeEnMinutes() {
    return dureeEnMinutes;
  }

  //insère l’instruction à la position précisée, position commençant à 1.
  public void insererInstruction(int position, Instruction instruction) {
    Util.checkStrictlyPositive(position);
    Util.checkObject(instruction);
    //+1 car on prend en compte l'instruction à ajouter
    if (position > recetteInstructions.size()+1){
      throw new IllegalArgumentException();
    }
    recetteInstructions.add(position-1, instruction);

    //pas oublier d'ajouter la duréee
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
  }

  //ajoute l’instruction en dernier.
  public void ajouterInstruction (Instruction instruction) {
    Util.checkObject(instruction);
    recetteInstructions.add(instruction);
    //pas oublier d'ajouter la duréee
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
  }

  //remplace l’instruction à la position précisée par celle en paramètre.
  //renvoie l’instruction qui a été remplacée
  public Instruction remplacerInstruction (int position, Instruction instruction) {
    Util.checkStrictlyPositive(position);
    Util.checkObject(instruction);
    //pas +1 vu qu'on ne rajoute rien
    if (position > recetteInstructions.size()){
      throw new IllegalArgumentException();
    }
    Instruction instructionARemplacer = recetteInstructions.set(position-1, instruction);

    //pas oublier d'enlever la duree de l'instruction a remplace et add la nouvelle
    dureeEnMinutes = dureeEnMinutes.minus(instructionARemplacer.getDureeEnMinutes());
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());

    return instructionARemplacer;
  }

  //supprimer l’instruction à la position précisée
  //renvoie l’instruction qui a été supprimée
  public Instruction supprimerInstruction (int position) {
    Util.checkStrictlyPositive(position);
    if (position > recetteInstructions.size()){
      throw new IllegalArgumentException();
    }
    Instruction instructionASupprimer = recetteInstructions.remove(position-1);
    //pas oublier d'enlever la duree de l'instruction supp
    dureeEnMinutes = dureeEnMinutes.minus(instructionASupprimer.getDureeEnMinutes());
    return instructionASupprimer;
  }

  // fournit une collection non-modifiable contenant les instructions du plat considéré.
  public List<Instruction> instructions() {
    return Collections.unmodifiableList(recetteInstructions);
  }


  @Override
  public String toString() {
    String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
    String res = this.nom + "\n\n";
    res += "Pour " + this.nbPersonnes + " personnes\n";
    res += "Difficulté : " + this.niveauDeDifficulte + "\n";
    res += "Coût : " + this.cout + "\n";
    res += "Durée : " + hms + " \n\n";
    res += "Ingrédients :\n";
//    for (domaine.IngredientQuantifie ing : this.ingredients) {
//      res += ing + "\n";
//    }
    int i = 1;
    res += "\n";
    for (Instruction instruction : this.recetteInstructions) {
      res += i++ + ". " + instruction + "\n";
    }
    return res;
  }

}
