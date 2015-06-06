package api.deserialize;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import api.response.APIAttribute;
import model.Product;

public class ProductDeserializer implements JsonDeserializer<Product> {

    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        Product product = gson.fromJson(json, typeOfT);

        for (APIAttribute attr: product.attributes) {
            String value = attr.values.get(0);

            if (attr.name.equals("Marca"))
                product.brand = value;
            else
            if (attr.name.equals("Color"))
                product.color = value;
            else
            if (attr.name.startsWith("Material"))
                product.material = value;
            else
            if (attr.name.startsWith("Talle"))
                product.sizes = attr.values;
            else
            if (attr.name.equals("Nuevo") && value.equals("Nuevo"))
                product.is_new = true;
            else
            if (attr.name.equals("Oferta") && value.equals("Oferta"))
                product.is_offer = true;
        }

        return product;
    }

}
