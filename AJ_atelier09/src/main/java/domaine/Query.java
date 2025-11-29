package domaine;

public interface Query {

  String getUrl();

  void setUrl(String url);

  QueryMethod getMethod();

  void setMethod(QueryMethod method);

  // enum interne
  public enum QueryMethod {
    GET,
    POST
  }
}
