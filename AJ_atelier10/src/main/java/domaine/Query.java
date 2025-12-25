package domaine;


/**
 * This interface represents a basic HTTP query.
 * It contains also a QueryMethod Enum to represent the Method (GET, POST...)
 */
public interface Query {

  /**
   * Get the url of the query.
   *
   * @return the url of the Query
   */
  String getUrl();

  /**
   * Set the url of the query.
   *
   * @param url the url to set
   */
  void setUrl(String url);

  /**
   * Get the method of the query.
   *
   * @return the method of the Query
   */
  QueryMethod getMethod();

  /**
   * Set the method of the query.
   *
   * @param method the method to set
   */
  void setMethod(QueryMethod method);

  /**
   * Set the method, in string format, of the query.
   *
   * @param method the method to set, in string format
   * @throws IllegalArgumentException if the method does not exist
   * @deprecated
   */
  void setMethod(String method) throws IllegalArgumentException;

  /**
   * Enum that represents the possible Query Methods that can be used.
   */
  // enum interne
  public enum QueryMethod {
    /**
     * Get method
     */
    GET,
    /**
     * Post method
     */
    POST
  }
}
