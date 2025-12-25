package utils;

import java.lang.reflect.Field;

public class Injector {

  public static void inject(Object target) throws Exception {
    // Récupère tous les champs de l’objet (même privés)
    Field[] fields = target.getClass().getDeclaredFields();

    for (Field field : fields) {
      // Si le champ est annoté @Inject
      if (field.isAnnotationPresent(Inject.class)) {
        field.setAccessible(true); // permet de modifier un champ privé

        // Crée automatiquement une instance de la classe du champ
        Object dep = field.getType().getDeclaredConstructor().newInstance();

        // Injecte l’instance dans le champ
        field.set(target, dep);
      }
    }
  }
}
