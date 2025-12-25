package utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation pour indiquer qu'un champ doit être injecté automatiquement.
 */
@Retention(RetentionPolicy.RUNTIME) // disponible pendant l’exécution
@Target(ElementType.FIELD)          // uniquement applicable aux champs
public @interface Inject {

}
