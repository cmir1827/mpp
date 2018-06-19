package com.rest;

import com.google.gson.*;
import com.model.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 21/06/2017.
 */
@Controller
public class RestController {


    //-------------------------------------------
    //DATE SERIALIZATION IF NEEDED
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String TIME_FORMAT = "HH:mm:ss";

    private static class DateDeserializer implements JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            try {
                return (Date) new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(jsonElement.getAsString());
            } catch (ParseException e) {
            }

            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + DATE_FORMAT);
        }
    }

    private static class TimeDeserializer implements JsonDeserializer<Time> {

        @Override
        public Time deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            try {

                String s = jsonElement.getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
                sdf.parse(s);
                long ms = sdf.parse(s).getTime();
                Time t = new Time(ms);
                return t;
            } catch (ParseException e) {
            }
            throw new JsonParseException("Unparseable time: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + TIME_FORMAT);
        }
    }

    private Gson gson;

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);


    //-------------------------------------------
    //DATE SERIALIZATION IF NEEDED



    @RequestMapping(value = "/rest/games/{id}", method = RequestMethod.GET)
    public @ResponseBody
    RestResponse[] getExcursion(@PathVariable("id") String username) {

//        UsersService service = new UsersService(new UserRepo(JdbcUtils.getProps()));
//        GamingService gamingService = new GamingService(new GameRepository(JdbcUtils.getProps()));
//
//        TSUser user = service.findByUsername(username);
//
//        List<RestResponse> restResponses = gamingService.getRestResponse(user);
//
//        RestResponse[] restResponsesArray = new RestResponse[restResponses.size()];
//        restResponsesArray = restResponses.toArray(restResponsesArray);

        return new RestResponse[1];
    }
}
