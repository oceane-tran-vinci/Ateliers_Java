package utils;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Injector {
  private static final Map<Class<?>, Class<?>> interfaceToImpl = new HashMap<>();

  // Bloc static : charge di.properties au démarrage et construit la map interface → implémentation
  static {
    try (FileInputStream is = new FileInputStream("AJ_atelier10/di.properties")) {

      Properties props = new Properties();
      props.load(is);

      // Pour chaque clé dans le fichier di.properties (chaque interface)
      //interfaceKey = Fully Qualified Name
      for (String interfaceKey : props.stringPropertyNames()) {
        // Transforme le nom de l'interface en objet Class
        Class<?> interfaceClass = Class.forName(interfaceKey);
        // Récupère le nom complet de la classe concrète correspondante et transforme aussi en Class
        Class<?> implementationClass = Class.forName(props.getProperty(interfaceKey));
        // Ajoute l'association interface → implémentation dans la map
        interfaceToImpl.put(interfaceClass, implementationClass);
      }
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors du chargement de di.properties", e);
    }
  }

  public static void inject(Object target) throws Exception {
    // Récupère tous les champs de l’objet (même privés)
    Field[] fields = target.getClass().getDeclaredFields();

    for (Field field : fields) {
      // Si le champ est annoté @Inject
      if (field.isAnnotationPresent(Inject.class)) {
        field.setAccessible(true); // permet de modifier un champ privé

        // On récupère la classe à instancier :
        // - si le type du champ est une interface et qu'une implémentation est configurée dans di.properties, on utilise cette implémentation
        // - sinon, on utilise le type du champ directement
        Class<?> implClass = interfaceToImpl.getOrDefault(field.getType(), field.getType());

        // Instancie la classe via réflexion (appel du constructeur par défaut)
        Object dep = implClass.getDeclaredConstructor().newInstance();

        // Injecte l’instance dans le champ
        field.set(target, dep);
      }
    }
  }
}
