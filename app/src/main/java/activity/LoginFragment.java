package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neon.neonstore.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import store.Store;

public class LoginFragment extends NeonFragment {

    Store store = Store.getInstance();

    @InjectView(R.id.username) TextView username;
    @InjectView(R.id.password) TextView password;

    @Override
    public String getTitle() {
        return getResources().getString(R.string.title_log_in);
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
            password.getText().toString()
        );
    }
}
