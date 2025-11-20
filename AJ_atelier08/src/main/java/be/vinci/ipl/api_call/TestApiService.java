package be.vinci.ipl.api_call;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class TestApiService {

  private static ApiService apiService = new ApiService();
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static void main(String[] args) {
    printAllPosts();
    printAllPostsWithCount();
    dealWithUnexistedApi();
    printAllPostsWithUserAndComments();
    printAllPostsWithUserAndCommentsAnd2Threads(); // Challenge optionnel
  }


  public static void printAllPosts() {
    System.out.println("1. Imprimer tous les posts");
    long start = System.currentTimeMillis();

    // TODO 1 : Affichez tous les posts dans le terminal (utiliser la méthode fetchPosts de ApiService)
    apiService.fetchPosts().thenAccept(System.out::println).join();


    long end = System.currentTimeMillis();
    System.out.println("1. Total execution time: " + (end - start) + " ms");

  }

  public static void printAllPostsWithCount() {
    System.out.println("2. Imprimer tous les posts suivi du nombre de posts");
    long start = System.currentTimeMillis();

    // TODO 2 : Affichez tous les posts, retourner les posts sous forme d'un JsonNode
    //  puis, afficher le nombre de posts
    apiService.fetchPosts().thenApply(posts -> {
      try {
        JsonNode postsJson = objectMapper.readTree(posts);
        System.out.println(postsJson);
        return postsJson;
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }).thenAccept(postsJson -> {
      System.out.println("Nombre de posts: " + postsJson.size());
    }).join();


    long end = System.currentTimeMillis();
    System.out.println("2. Total execution time: " + (end - start) + " ms");

  }

  public static void dealWithUnexistedApi() {
    System.out.println("3. Gérer une erreur lorsqu'une API n'existe pas");
    long start = System.currentTimeMillis();

    // TODO 3 : Tentez d'afficher le résultat d'une API qui n'existe pas
    //  en utilisant la méthode fetchData de ApiService.
    //  Gérez l'exception en affichant le code d'erreur retourné
    //  par la méthode fetchData.
    apiService.fetchData("http://unexistingapi/things").thenAccept(System.out::println)
        .exceptionally(throwable -> {
          System.out.println("Message d'erreur : " + throwable.getMessage());
          return null;
        })
        .join();


    long end = System.currentTimeMillis();
    System.out.println("3. Total execution time: " + (end - start) + " ms");
  }

  public static void printAllPostsWithUserAndComments() {
    System.out.println(
        "4. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur");
    long start = System.currentTimeMillis();

    // TODO 4 : Affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
    //  genre : "Post (postId:1) : {post details}
    //           Comments: (postId:1) : [{comments details}]
    //           User: (postId:1) : {user details}"
    //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
    //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
    //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
    //  toutes les infos pour un post donné.
    apiService.fetchPosts()
        .thenCompose(postsJson -> {
          try {
            JsonNode posts = objectMapper.readTree(postsJson);
            int postLimit = posts.size();
            CompletableFuture<Void>[] futures = new CompletableFuture[postLimit];

            IntStream.range(0, postLimit).forEach(index -> {
              JsonNode post = posts.get(index);
              int postId = post.get("id").asInt();
              int userId = post.get("userId").asInt();

              CompletableFuture<String> commentsFuture = apiService.fetchCommentsForPost(postId);
              CompletableFuture<String> userFuture = apiService.fetchUser(userId);

              futures[index] = commentsFuture.thenCombine(userFuture, (commentsJson, userJson) -> {
                try {
                  JsonNode comments = objectMapper.readTree(commentsJson);
                  JsonNode user = objectMapper.readTree(userJson);
                  synchronized (System.out) {
                    System.out.println("Post (postId:" + postId + ") : " + post);
                    System.out.println("Comments: (postId:" + postId + ") : " + comments);
                    System.out.println("User: (postId:" + postId + ") : " + user + "\n");
                  }

                } catch (Exception e) {
                  e.printStackTrace();
                }
                return null;
              });
            });

            return CompletableFuture.allOf(futures);
          } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(null);
          }
        }).join();


    long end = System.currentTimeMillis();
    System.out.println("4. Total execution time: " + (end - start) + " ms");
  }

  public static void printAllPostsWithUserAndCommentsAnd2Threads() {
    System.out.println(
        "5. Imprimer tous les posts avec les commentaires et les détails de l'utilisateur en utilisant 2 threads uniquement");
    long start = System.currentTimeMillis();

    // TODO 5 (challenge optionnel) : Même exercice que précédemment, mais en utilisant que deux Threads pour ApiService.
    //  Nous vous conseillons de créer une nouvelle classe ApiServiceWithExecutor qui contiendra un ExecutorService.
    //  Affichez tous les posts, les commentaires et les détails de l'utilisateur sous un format du
    //  genre : "Post (postId:1) : {post details}
    //           Comments: (postId:1) : [{comments details}]
    //           User: (postId:1) : {user details}"
    //  Pour chaque "post", vous devez lancer en parallèle toute les requêtes pour
    //  récupérer les commentaires et les détails de l'utilisateur. De plus, vous devez faire attention à
    //  attendre que tant les commentaires que les détails de l'utilisateur soient récupérés avant d'afficher
    //  toutes les infos pour un post donné.

    long end = System.currentTimeMillis();
    System.out.println("5. Total execution time: " + (end - start) + " ms");
  }
}
