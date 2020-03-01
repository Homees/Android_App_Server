package sk.mtmp.zadanie1;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
 
@Path("/projectile_motion_service")
public class RestService {
	
	@GET
	@Produces("application/json")
	public Response calculateProjectileMotion(@QueryParam("angle") int angle, @QueryParam("speed") int speed) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Angle Value", angle);
		jsonObject.put("Speed Value", speed);
		
        double deltaTime = 0.01;
        double x = 0.0;
        double y = 0.0;
        double ay = -9.8;
        double ax = 0;
        double vx = speed * Math.cos(angle * (Math.PI/180.0));
        double vy = speed * Math.sin(angle * (Math.PI/180.0));
        double maxHeight = 0;
        double time = 0.0;
        
        final ArrayList<Coordinates> suradnice = new ArrayList<>();
        suradnice.add(new Coordinates(Math.round(x * 1000.0) / 1000.0, Math.round(y * 1000.0) / 1000.0, 
        		Math.round(time * 1000.0) / 1000.0));
        
        while (y >= 0){
            time += deltaTime;
            x += vx * deltaTime;
            y += vy * deltaTime;

            vx += ax * deltaTime;
            vy += ay * deltaTime;
            
            if (y > maxHeight) {
                maxHeight = Math.round(y * 1000.0) / 1000.0;
            }

            suradnice.add(new Coordinates(Math.round(x * 1000.0) / 1000.0, Math.round(y * 1000.0) / 1000.0, 
            		Math.round(time * 1000.0) / 1000.0));
        }
        suradnice.add(new Coordinates(0, maxHeight, 0));
        System.out.println("Výsledok: " + suradnice);
 
		return Response.status(200).entity(new Gson().toJson(suradnice)).build();
	}
}
