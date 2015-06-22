package api.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Calendar;

import api.API;

public class CalendarDeserializer implements JsonDeserializer<Calendar> {

    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
        .create();

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(API.dateFormat.parse(json.getAsString()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return cal;
    }
}
