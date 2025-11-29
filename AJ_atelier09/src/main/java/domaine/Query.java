package domaine;

public class Query {

  private String url;
  private QueryMethod method;

  // enum interne
  public enum QueryMethod {
    GET,
    POST
  }

  public Query(String url, QueryMethod method) {
    this.url = url;
    this.method = method;
  }

  public String getUrl() {
    return url;
  }

  public QueryMethod getMethod() {
    return method;
  }
}
