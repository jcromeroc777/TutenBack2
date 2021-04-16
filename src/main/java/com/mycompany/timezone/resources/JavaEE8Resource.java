package com.mycompany.timezone.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 *
 * @author Romero
 */
@Path("timezone")
@CrossOrigin(origins = "*")
public class JavaEE8Resource {
    
    @POST
    @Path("/obtain")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(JsonObject  requestBody) {
        String dato1 = requestBody.getString("dato1");
        String dato2 = requestBody.getString("dato2");
        
        LocalDate hoy = LocalDate.now();
        LocalTime ahora = LocalTime.parse(dato1);
        LocalDateTime fecha = LocalDateTime.of(hoy, ahora);
        
        ZonedDateTime fechaUTC = ZonedDateTime
        .now(ZoneId.of(dato2)) // fecha y hora actual en la delegación
        .with(fecha) // se asigna la fecha y hora que se recibe
        .withZoneSameInstant(ZoneOffset.UTC); // convertimos a formato UTC
 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> calculate = new LinkedHashMap<>();
        
        calculate.put("time", fechaUTC.format(formatter));
        calculate.put("timezone", "utc");

        response.put("response", calculate);

        return Response.ok() //200
                                    .entity(response)
                                    .header("Access-Control-Allow-Origin", "*")
                                    .header("Access-Control-Allow-Headers", "*")
                                    .header("Access-Control-Allow-Methods", "*")
                                    .allow("OPTIONS").build();
    }
}
