package blacklist;

import domaine.Query;

public interface BlacklistService {

  // 4.1.4 : Une méthode d’instance public boolean check(Query query)
  // L'annotation @Override implique que cette classe implémente probablement une interface
  // qui définit la méthode 'check'.
  boolean check(Query query);
}
