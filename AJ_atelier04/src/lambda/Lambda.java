package lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lambda {

  /**
   * Retourne une liste contenant uniquement les Integer qui correspondent au predicat match
   *
   * @param list  La liste d'Integer originale
   * @param match le predicat à respecter
   * @return une liste contenant les integer qui respectent match
   */
  public static List<Integer> allMatches(List<Integer> list, Predicate<Integer> match) {
    //TODO
    List<Integer> result = new ArrayList<>();
    for (Integer i : list) {
      if (match.test(i)) {
        result.add(i);
      }
    }
    return result;
  }

  /**
   * Retourne une liste contenant tous les éléments de la liste originale, transformés par la
   * fonction transform
   *
   * @param list      La liste d'Integer originale
   * @param transform la fonction à appliquer aux éléments
   * @return une liste contenant les integer transformés par transform
   */
  public static List<Integer> transformAll(List<Integer> list, Function<Integer, Integer> transform) {
    //TODO
    List<Integer> result = new ArrayList<>();
    for (Integer i : list) {
      result.add(transform.apply(i)); //transform.apply(i) signifie : applique la fonction transform à l’élément i.
    }
    return result;
  }

  //2 METHODES A AJOUTER : 

  /**
   * Retourne une liste contenant tous les éléments de la liste originale, transformés par la
   * fonction transform
   *
   * @param list      La liste d'Integer originale
   * @param transform la fonction à appliquer aux éléments
   * @return une liste contenant les integer transformés par transform
   */
  public static <P, R> List<R> map(List<P> list, Function<P, R> transform) {
    return list.stream().map(transform).collect(Collectors.toList());
  }

  /**
   * Retourne une liste contenant uniquement les Integer qui correspondent au predicat match
   *
   * @param list  La liste d'Integer originale
   * @param match le predicat à respecter
   * @return une liste contenant les integer qui respectent match
   */
  public static <T> List<T> filter(List<T> list, Predicate<T> match) {
    return list.stream().filter(match).collect(Collectors.toList());
  }
}
