package api.deserialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarDeserializer implements JsonDeserializer<Calendar> {

    static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public Calendar deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm")
        .create();

        Calendar cal = Calendar.getInstance();

        try {
            cal.setTime(dateFormat.parse(json.getAsString()));

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return cal;
    }
}
