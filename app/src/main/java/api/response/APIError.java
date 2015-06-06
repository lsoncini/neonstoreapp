package api.response;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class APIError {
    public int code;
    public String message;

    public APIError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public APIError(Response res) {
        this(-2, "HTTP " + res.getStatus() + "\n" + res.getBody());
    }

    public APIError(RetrofitError err) {
        this(-1, "Retrofit error");
        err.printStackTrace();
    }

    public String toString() {
        return "<APIError " + code + ": " + message + ">";
    }
}
