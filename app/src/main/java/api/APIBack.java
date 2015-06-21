package api;

import api.response.APIError;
import api.response.APIResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class APIBack<T extends APIResponse> implements Callback<T> {

    public void onSuccess(T res) {}
    public void onError(APIError err) {}

    @Override
    public final void success(T apiResponse, Response response) {
        if (response.getStatus() != 200) {
            onError(new APIError(response));

        } else if (apiResponse.error != null)
            onError(apiResponse.error);

        else
            onSuccess(apiResponse);
    }

    @Override
    public final void failure(RetrofitError error) {
        onError(new APIError(error));
    }
}