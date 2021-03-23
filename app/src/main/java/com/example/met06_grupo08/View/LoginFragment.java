package com.example.met06_grupo08.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.met06_grupo08.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

/**
 * Fragment for the login
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private View v;
    private TextInputEditText et_mail,et_password;
    private Button btn_create,btn_init_sesion;
    NavController nav;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_menu_login, container, false);
        context = getContext();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        initializeView();
    }

    private void initializeView() {
        et_mail = (TextInputEditText) v.findViewById(R.id.et_login_mail);
        et_password = (TextInputEditText) v.findViewById(R.id.et_login_password);
        btn_create = (Button) v.findViewById(R.id.btn_create_account_login);
        // Here we specify that the button calls an action or a fragment
        btn_create.setOnClickListener(this);
        btn_init_sesion = (Button) v.findViewById(R.id.btn_login);
        btn_init_sesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_create_account_login:
                nav.navigate(R.id.action_registerLoginFragment);
                break;
            case R.id.btn_login:
                errorControl();
                break;
        }
    }
    private void errorControl(){
        String password = et_password.getText().toString();
        String mail = et_mail.getText().toString();
        if (mail.matches("")){
            Toast.makeText(context, "You need to complete email fields", Toast.LENGTH_SHORT).show();
        }else if (password.matches("")){
            Toast.makeText(context, "You need to complete password field", Toast.LENGTH_SHORT).show();
        }else if (!isValidEmail(mail)) {
            Toast.makeText(context, "No Valid Email Address", Toast.LENGTH_SHORT).show();
        }else{
            ((LoginActivity)getActivity()).checkUserFirebase(mail,password);
        }
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}
