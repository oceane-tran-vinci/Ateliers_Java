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
        ClassAnalyzer analyzer = new ClassAnalyzer(User.class);
        return analyzer.getFullInfo();
    }
}
