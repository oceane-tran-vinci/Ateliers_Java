package be.vinci.ipl.services;

import jakarta.json.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.logging.Logger;

/**
 * Class analyzer. It saves a class into attribute, from a constructor, and gives a lot of
 * convenient methods to transform this into a JSON object to print the UML diagram.
 */
public class ClassAnalyzer {

  private Class aClass;

  public ClassAnalyzer(Class aClass) {
    this.aClass = aClass;
  }

  /**
   * Create a JSON Object with all the info of the class.
   *
   * @return
   */
  public JsonObject getFullInfo() {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    objectBuilder.add("name", aClass.getSimpleName());
    objectBuilder.add("fields", getFields());
    //4.2) ajouter methods pour l'afficher sur le site
    objectBuilder.add("methods", getMethods());
    return objectBuilder.build();
  }


  /**
   * From the field descriptor f, create a Json Object with all field data. Example : { name:
   * "firstname", type: "String", visibility : "private"  // public, private, protected, package
   * isStatic: false, }
   *
   * @param f filed descriptor - describe an attribute
   * @return the generated JSON
   */
  public JsonObject getField(Field f) {
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    // TODO add missing info
        /* Utilisesr getSimpleName() => + propre
        "type : getName()": "java.lang.String",
        "type : getSimpleName()": "String",
        "type : getTypeName()": "java.lang.String",
         */
    objectBuilder.add("name", f.getName());
    objectBuilder.add("type", f.getType().getSimpleName());

    //déjà donné
    objectBuilder.add("visibility", getFieldVisibility(f));
    objectBuilder.add("isStatic", isFieldStatic(f));
    return objectBuilder.build();
  }

  /**
   * Get fields, and create a Json Array with all fields data. Example : [ {}, {} ] This method rely
   * on the getField() method to handle each field one by one.
   */
  public JsonArray getFields() {
    //ici ça crée un tableau json qu'on va utiliser et ajouter les éléments dedans
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    // TODO Add all fields descriptions to array (use the getField() method above)
    // Parcourt tous les attributs déclarés dans la classe analysée et ajoute leur description JSON au tableau
    // use getDeclaredFields() car ça inclus les classes privés
    for (Field f : aClass.getDeclaredFields()) {
      arrayBuilder.add(getField(f));
    }
    return arrayBuilder.build();
  }

  /**
   * Return whether a field is static or not
   *
   * @param f the field to check
   * @return true if the field is static, false else
   */
  private boolean isFieldStatic(Field f) {
    // TODO
        /* Modifier sert à vérifier les déclarations (modificateurs) => public, private, static, final,...
        f.getModifiers() → renvoie les modificateurs du champ (public, private, static, etc.)
        Modifier.isStatic(...) → renvoie true si le modificateur static est présent.
        */
    return Modifier.isStatic(f.getModifiers());
  }

  /**
   * Get field visibility in a string form
   *
   * @param f the field to check
   * @return the visibility (public, private, protected, package)
   */
  private String getFieldVisibility(Field f) {
    // TODO
    //Utiliser Modifier
    if (Modifier.isPublic(f.getModifiers())) {
      return "public";
    } else if (Modifier.isPrivate(f.getModifiers())) {
      return "private";
    } else if (Modifier.isProtected(f.getModifiers())) {
      return "protected";
    } else {
      return "package";
    }
  }

  //4.2) Methods => casi la mm que Fields (4.1)
  /**
   * {
   * name: "setFirstName",
   * returnType: null,
   * parameters: ["String"]
   * visibility : "public" // public, private, protected, package
   * isStatic: false,
   * isAbstract: false
   * }
  **/
  public JsonObject getMethod(Method m){
    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
    objectBuilder.add("name", m.getName());
    objectBuilder.add("returnType", m.getReturnType().getSimpleName());
    //4 methodes à créer => static et visibility comme fields mais y'a 2 autres
    objectBuilder.add("parameters", getParameters(m));
    objectBuilder.add("visibility", getMethodVisibility(m));
    objectBuilder.add("isStatic", isMethodStatic(m));
    objectBuilder.add("isAbstract", isMethodAbstract(m));
    return objectBuilder.build();
  }

  /**
   * methodds: [] // Set methods here, same as Fields
   */
  public JsonArray getMethods(){
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (Method m : aClass.getDeclaredMethods()) {
      arrayBuilder.add(getMethod(m));
    }
    return arrayBuilder.build();
  }

  /**
   * Get method parameters, and create a Json Array with the method parameters types.
   * Example :
   * [ {}, {} ]
   */
  //si on regarder la javadoc getMethod => on comprend qu'on veut un tableau avec les paramètres
  public JsonArray getParameters(Method m) {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
    for (Parameter p : m.getParameters()) {
      arrayBuilder.add(p.getType().getSimpleName());
    }
    return arrayBuilder.build();
  }

  /**
   * Get method visibility in a string form
   *
   * @param m the method to check
   * @return the visibility (public, private, protected, package)
   */
  private String getMethodVisibility(Method m) {
    if (Modifier.isPublic(m.getModifiers())) {
      return "public";
    } else if (Modifier.isPrivate(m.getModifiers())) {
      return "private";
    } else if (Modifier.isProtected(m.getModifiers())) {
      return "protected";
    } else {
      return "package";
    }
  }

  /**
   * Return whether a method is static or not
   *
   * @param m the method to check
   * @return true if the method is static, false else
   */
  private boolean isMethodStatic(Method m) {
    return Modifier.isStatic(m.getModifiers());
  }

  /**
   * Return whether a method is abstract or not
   *
   * @param m the method to check
   * @return true if the method is abstract, false else
   */
  private boolean isMethodAbstract(Method m) {
    return Modifier.isAbstract(m.getModifiers());
  }

}
