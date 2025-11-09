package be.vinci.ipl.api;

import be.vinci.ipl.classes.User;
import be.vinci.ipl.instances.InstanceGraph1;
import be.vinci.ipl.services.ClassAnalyzer;
import be.vinci.ipl.services.InstancesAnalyzer;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Send instances graph data to make object diagrams
 * <p>
 * The instances graphs are initialized by a class containing the "initInstanceGraph" method,
 * building the instance graph, and returning it.
 * <p>
 * The "instance builder class name" must be given and present into the "instances" package
 */
@Path("instances")
public class Instances {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public JsonStructure getInstanceGraphInfo(
      @QueryParam("builderclassname") String builderClassname) {
        /* Avant :
        InstanceGraph1 builder = new InstanceGraph1();    // TODO change this line to use the query parameter, and generate dynamically the builder
        Object instanceGraph = builder.initInstanceGraph();   // TODO change this line to avoid calling initInstanceGraph() directly
        InstancesAnalyzer analyzer = new InstancesAnalyzer(instanceGraph);
        return analyzer.getFullInfo();
         */
    //Après : voir vidéo prof (Introspection - Inspection et instanciation d'objets)
    try {
      // Charger dynamiquement la classe depuis le bon package
      Class<?> builderClass = Class.forName("be.vinci.ipl.instances." + builderClassname);
      //Instancier un objet via le constructeur par défaut
      Object builderObject = builderClass.getConstructor().newInstance();

      // Récupérer la méthode "initInstanceGraph"
      Method initMethdod = builderClass.getMethod("initInstanceGraph");
      // Appeler dynamiquement la méthode et récupérer le graphe d’instances
      Object instanceGraph = initMethdod.invoke(builderObject);
      InstancesAnalyzer analyzer = new InstancesAnalyzer(instanceGraph);
      return analyzer.getFullInfo();

    } catch (ClassNotFoundException e) {
      throw new WebApplicationException(404);
    } catch (InvocationTargetException | InstantiationException |
             IllegalAccessException | NoSuchMethodException e) {
      throw new InternalError(e);
    }

  }
}
