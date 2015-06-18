package api.deserialize;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import api.response.APIAttribute;
import model.Product;
import model.Product.Age;
import model.Product.Color;
import model.Product.Gender;

public class ProductDeserializer implements JsonDeserializer<Product> {

    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        Product product = gson.fromJson(json, typeOfT);

        for (APIAttribute attr: product.attributes) {
            String name  = attr.name;
            String value = attr.values.get(0);

            if (name.equals("Edad"))
                product.age = Age.valueOf(value);
            else
            if (name.equals("Genero"))
                product.gender = Gender.valueOf(value);
            else
            if (name.equals("Marca"))
                product.brand = value;
            else
            if (name.equals("Color"))
                product.color = Color.valueOf(value);
            else
            if (name.startsWith("Material"))
                product.material = value;
            else
            if (name.startsWith("Talle"))
                product.sizes = attr.values;
            else
            if (name.equals("Nuevo") && value.equals("Nuevo"))
                product.is_new = true;
            else
            if (name.equals("Oferta") && value.equals("Oferta"))
                product.is_offer = true;
        }

        return product;
    }

}
