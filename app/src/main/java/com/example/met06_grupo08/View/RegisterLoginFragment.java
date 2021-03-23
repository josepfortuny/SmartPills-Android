package com.example.met06_grupo08.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.met06_grupo08.R;

import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterLoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "FRAGMENTLOGIN ACTIVITY";
    private View v;
    private TextInputEditText et_name,et_mail,et_password,et_hardware;
    private Button btn_create,btn_init_sesion;
    NavController nav;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment
        context = getContext();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nav = Navigation.findNavController(view);
        InitializeView();
    }

    private void InitializeView() {
        et_name = (TextInputEditText) v.findViewById(R.id.et_name_user);
        et_mail = (TextInputEditText) v.findViewById(R.id.et_email);
        et_password = (TextInputEditText) v.findViewById(R.id.et_password);
        et_hardware = (TextInputEditText) v.findViewById(R.id.et_hardware_number);
        et_hardware.setText("Hardware_Grupo08");
        btn_create = (Button) v.findViewById(R.id.btn_create_account);
        btn_create.setOnClickListener(this);
        btn_init_sesion = (Button) v.findViewById(R.id.btn_go_initsesion);
        btn_init_sesion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_create_account:
                errorControl();
                break;
            case R.id.btn_go_initsesion:
                nav.navigate(R.id.action_menuLoginFragment);
                break;
        }
    }
    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private void errorControl(){
        String name = et_name.getText().toString().trim();
        String mail = et_mail.getText().toString();
        String password = et_password.getText().toString();
        String hardware = et_hardware.getText().toString();
        if (name.matches("")){
            Toast.makeText(context, "You need to complete name field", Toast.LENGTH_SHORT).show();
        }else if (mail.matches("")){
            Toast.makeText(context, "You need to complete mail field", Toast.LENGTH_SHORT).show();
        }else if (password.matches("")) {
            Toast.makeText(context, "You need to complete password field", Toast.LENGTH_SHORT).show();
        }else if (password.matches("")){
            Toast.makeText(context, "You need to complete hardware field", Toast.LENGTH_SHORT).show();
        }else if (!isValidEmail(mail)){
            Toast.makeText(context, "No Valid Email Address", Toast.LENGTH_SHORT).show();
        }else{
            ((LoginActivity)getActivity()).addUserIfDontExistsFirebase(name, mail, password,hardware,nav);
        }
    }

}