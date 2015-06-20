package activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neon.neonstore.R;

import api.APIBack;
import api.Store;
import api.response.APIError;
import api.response.LoginResponse;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginFragment extends NeonFragment {

    Store store = Store.getInstance();

    @InjectView(R.id.username) TextView username;
    @InjectView(R.id.password) TextView password;

    @Override
    public String getTitle() {
        return "Iniciar sesión";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.login)
    public void onLoginClick() {
        store.login(
            username.getText().toString(),
            password.getText().toString(),

            new APIBack<LoginResponse>() {
                public void onSuccess(LoginResponse res) {
                    System.out.println("LOGIN SUCCESSFUL");
                    System.out.println(store.session.inspect());
                }

                public void onError(APIError err) {
                    System.err.println("login error " + err);
                }
            }
        );
    }
}
