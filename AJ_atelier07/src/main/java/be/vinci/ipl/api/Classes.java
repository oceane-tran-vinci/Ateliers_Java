package be.vinci.ipl.api;

import be.vinci.ipl.services.ClassAnalyzer;
import be.vinci.ipl.classes.User;
import jakarta.json.JsonStructure;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

/**
 * Send class data to make class diagrams
 * The class name must be given, and present into the "classes" package
 */
@Path("classes")
public class Classes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonStructure getClassInfo(@QueryParam("classname") String classname) {
        //1) modifier ClassAnalyzer(User.class) pr qu'on puisse choisir nous mm la classe
        //2) si classe existe pas => throw new WebApplicationException(404); => try catch
        try {
            //créer variable pour Stocker la classe demandée par l'utilisateur, chargée dynamiquement
            Class<?> targetClass = Class.forName("be.vinci.ipl.classes." + classname);
            ClassAnalyzer analyzer = new ClassAnalyzer(targetClass);
            return analyzer.getFullInfo();
        } catch (ClassNotFoundException e){ //méthode forName a besoin d'un catch ClassNotFoundException
            throw new WebApplicationException(404);
        }
    }
}
