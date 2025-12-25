package blacklist;

import domaine.Query;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class BlacklistServiceImpl implements BlacklistService {
  //1.1 : Attribut statique qui stocke tous les domaines interdits
  //Partagé par toutes les instances, initialisé une seule fois au chargement de la classe
  private static Set<String> blacklistedDomains;

  //1.2 : Bloc statique exécuté UNE SEULE FOIS quand la classe est chargée
  static {
    //FileInputStream permet de lire le fichier "blacklist.properties" en tant que flux d'octets
    //+ objet Properties accepte que InputStream dc logique FileInputStream
    try (FileInputStream in = new FileInputStream("blacklist.properties")) {

      // On utilise cet objet parce que Properties.load() accepte directement un InputStream
      // Cela permet de lire facilement le fichier sans avoir besoin de FileReader ou autre
      Properties props = new Properties();
      props.load(in);

      // Récupère la liste des domaines via la clé "blacklistedDomains"
      // Split sur ";" pour obtenir un tableau de domaines
      // Set.of() transforme le tableau en Set<String> pour éviter les doublons
      blacklistedDomains = Set.of(props.getProperty("blacklistedDomains").split(";"));

    } catch (IOException e) {
      // Si le fichier n'existe pas ou n'est pas lisible, on lève une exception
      throw new RuntimeException(e);
    }
  }

  //2 : Méthode d'instance pour vérifier si l'URL est autorisée
  //Renvoie true si l'URL ne contient aucun domaine interdit, false sinon
  public boolean check(Query query){
    // Transforme le Set en stream et cherche si un domaine interdit est présent
    // anyMatch renvoie true si un match est trouvé, on inverse avec ! pour renvoyer false dans ce cas
    return !blacklistedDomains.stream().anyMatch(d -> query.getUrl().contains(d));
  }

}
