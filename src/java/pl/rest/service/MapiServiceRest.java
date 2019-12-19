package pl.rest.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pl.models.JadlospisSkladnikiViewVO;
import pl.models.JadlospisViewVO;

/**
 *
 * @author k.skowronski
 */
@Path("/mapiServiceRest")
@Stateless(name = "MapiServiceRest")
@Produces("application/json")
@LocalBean
public class MapiServiceRest {
   
    @EJB
    public MapiApi mapiApi;
    
    @POST
    @Path("/getInfAboutWartoscOdzywczaForDiet")
    @Produces( MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getInfAboutWartoscOdzywczaForDiet(String request) {
        try{
            Gson gson = new Gson();
            JsonObject req = gson.fromJson (request, JsonElement.class).getAsJsonObject();
            
            Long dietId = req.get("dietId").getAsLong();
            String forDay = req.get("forDay").getAsString();
            
            List<JadlospisViewVO> lJ = mapiApi.getInfWartoscOdzywczaForDiet(dietId, forDay);
                    
             Response response = Response.ok(gson.toJson(lJ)).build();
            return response;

        } catch (Exception e) {
            System.out.print("getInfAboutJadlospisForDiet:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
    
    
    
    @POST
    @Path("/getInfAboutJadlospisForDiet")
    @Produces( MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getInfAboutJadlospisForDiet(String request) {
        try{
            List<JadlospisSkladnikiViewVO> jsL = new ArrayList<>();
            Gson gson = new Gson();
            JsonObject req = gson.fromJson (request, JsonElement.class).getAsJsonObject();
            
            Long dietId = req.get("dietId").getAsLong();
            String forDay = req.get("forDay").getAsString();
            
            List<JadlospisViewVO> jList = mapiApi.getInfWartoscOdzywczaForDiet(dietId, forDay);
            
            
             for (int i = 0; i < jList.size(); i++) {    
                List<JadlospisSkladnikiViewVO> jsList = mapiApi.getInfAboutJadlospisForDiet( jList.get(i).getIdJadlospis() );
                
                for ( JadlospisSkladnikiViewVO js : jsList ){;
                jsL.add(js);
                }
            }
            
            
                    
             Response response = Response.ok(gson.toJson(jsL)).build();
            return response;

        } catch (Exception e) {
            System.out.print("getInfAboutJadlospisForDiet:" + e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
 
    
    
}
