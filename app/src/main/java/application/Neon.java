package application;

import api.API;
import retrofit.RestAdapter;

public class Neon {

    private static API api;

    public static API getAPI() {

        if (api == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API.root)
            .build();

            api = restAdapter.create(API.class);
        }

        return api;
    }

}
