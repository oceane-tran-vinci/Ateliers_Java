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

  public enum Type{
    ENTREE("Entrée"),PLAT("Plat"),DESSERT("Dessert");

    private String nom;

    Type(String nom) {
      this.nom = nom;
    }

    public String getNom() {
      return this.nom;
    }
  }

  private final String nom;
  private int nbPersonnes;
  private Difficulte niveauDeDifficulte;
  private Cout cout;
  private Type type;
  private Duration dureeEnMinutes = Duration.ofMinutes(0);
  private List<Instruction> recette = new ArrayList<Instruction>();
  private Set<IngredientQuantifie> ingredients = new HashSet<>();

  public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout, Type type) {
    Util.checkString(nom);
    Util.checkStrictlyPositive(nbPersonnes);
    Util.checkObject(niveauDeDifficulte);
    Util.checkObject(cout);
    Util.checkObject(type);
    this.nom = nom;
    this.nbPersonnes = nbPersonnes;
    this.niveauDeDifficulte = niveauDeDifficulte;
    this.cout = cout;
    this.type = type;
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

  public Type getType(){return type;}

  public Duration getDureeEnMinutes() {
    return dureeEnMinutes;
  }

  // gestion de la recette et de la dureeEnMinutes

  /**
   * Cette méthode insère l'instruction à la position précisée (la position commence à 1)
   *
   * @param position    la position à laquelle l'instruction doit être insérée
   * @param instruction l'instruction à insérer
   * @throws IllegalArgumentException en cas de position invalide ou d'instruction null
   */
  public void insererInstruction(int position, Instruction instruction) {
    Util.checkStrictlyPositive(position);
    Util.checkObject(instruction);
    if (position > recette.size() + 1) {
      throw new IllegalArgumentException();
    }
    recette.add(position - 1, instruction);
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
  }

  /**
   * Cette méthode ajoute l'instruction en fin de la liste
   *
   * @param instruction l'instruction à ajouter
   * @throws IllegalArgumentException en cas d'instruction null
   */
  public void ajouterInstruction(Instruction instruction) {
    Util.checkObject(instruction);
    recette.add(instruction);
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
  }

  /**
   * Cette méthode remplace l’instruction de la position précisée par celle en paramètre (la
   * position commence à 1).
   *
   * @param position    la position de l'instruction à remplacer
   * @param instruction la nouvelle instruction
   * @return l'instruction remplacée
   * @throws IllegalArgumentException en cas de position invalide ou d'instruction null
   */
  public Instruction remplacerInstruction(int position, Instruction instruction) {
    Util.checkStrictlyPositive(position);
    Util.checkObject(instruction);
    if (position > recette.size()) {
      throw new IllegalArgumentException();
    }
    Instruction instructionRemplacee = recette.set(position - 1, instruction);
    dureeEnMinutes = dureeEnMinutes.minus(instructionRemplacee.getDureeEnMinutes());
    dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinutes());
    return instructionRemplacee;
  }

  /**
   * Cette méthode supprime l’instruction qui se trouve à la position précisée en paramètre (la
   * position commence à 1).
   *
   * @param position la position de l'instruction à supprimer
   * @return l'instuction supprimée
   * @throws IllegalArgumentException en cas de position invalide
   */
  public Instruction supprimerInstruction(int position) {
    Util.checkStrictlyPositive(position);
    if (position > recette.size()) {
      throw new IllegalArgumentException();
    }
    Instruction instructionSupprimee = recette.remove(position - 1);
    dureeEnMinutes = dureeEnMinutes.minus(instructionSupprimee.getDureeEnMinutes());
    return instructionSupprimee;
  }

  public List<Instruction> instructions() {
    return Collections.unmodifiableList(recette);
  }

  //PART 2 : gestion des ingrédients

  //crée un IngrédientQuantifie et l’ajoute si l’ingrédient n’est pas encore présent. 
  // Cela renvoie false si l’ingrédient est déjà présent.
  public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite) {
    Util.checkObject(unite);
    Util.checkStrictlyPositive(quantite);
    Util.checkObject(unite);
    
    if (trouverIngredientQuantifie(ingredient) != null){
      return false;
    }
    IngredientQuantifie newIngredientQuantifie = new IngredientQuantifie(ingredient, quantite, unite);
    ingredients.add(newIngredientQuantifie);
    return true;
  }

  //idem précédente.
  //l’unité mise par défaut est NEANT
  //fait appel à la méthode au dessus
  public boolean ajouterIngredient(Ingredient ingredient, int quantite) {
    return ajouterIngredient(ingredient, quantite, Unite.NEANT);
  }

  //modifie l’unité et la quantité de l’ingrédient quantifié correpondant
  // à l’ingrédient passé en paramètre.
  //renvoie false si l’ingredient n’est pas présent.
  public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite) {
    Util.checkObject(ingredient);
    Util.checkStrictlyPositive(quantite);
    Util.checkObject(unite);
    
    IngredientQuantifie ingredientQuantifie = trouverIngredientQuantifie(ingredient);
    if (ingredientQuantifie == null){
      return false;
    }
    ingredientQuantifie.setQuantite(quantite);
    ingredientQuantifie.setUnite(unite);
    return true;
  }

  //supprime l’ingrédient quantifié correspondant à l’ingrédient passé en paramètre.
  //renvoie false si l’ingredient n’est pas présent
  public boolean supprimerIngredient(Ingredient ingredient) {
    Util.checkObject(ingredient);
    IngredientQuantifie ingredientQuantifie = trouverIngredientQuantifie(ingredient);
    if (ingredientQuantifie == null){
      return false;
    }
    return ingredients.remove(ingredientQuantifie);
  }

  //renvoie l’ingrédient quantifié correspondant à l’ingrédient
  //renvoie null si l’ingredient n’est pas présent
  public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
    Util.checkObject(ingredient);
    for (IngredientQuantifie iq : ingredients) {
      if (iq.getIngredient().equals(ingredient)){
        return iq;
      }
    }
    return null;
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
    for (IngredientQuantifie ing : this.ingredients) {
      res += ing + "\n";
    }
    int i = 1;
    res += "\n";
    for (Instruction instruction : this.recette) {
      res += i++ + ". " + instruction + "\n";
    }
    return res;
  }

}
