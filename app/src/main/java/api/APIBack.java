package api;

import api.response.APIError;
import api.response.APIResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class APIBack<T extends APIResponse> implements Callback<T> {

    public void onSuccess(T res) {}
    public void onError(APIError err) {}

    @Override
    public final void success(T apiResponse, Response response) {
        if (response.getStatus() != 200) {
            onError(
                new APIError(-2, "HTTP " + response.getStatus() + "\n" + response.getBody())
            );

        } else if (apiResponse.error != null)
            onError(apiResponse.error);

        else
            onSuccess(apiResponse);
    }

    @Override
    public final void failure(RetrofitError rferror) {
        onError(new APIError(-1, rferror.toString()));
    }
}