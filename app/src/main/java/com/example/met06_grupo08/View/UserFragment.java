package com.example.met06_grupo08.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.met06_grupo08.R;

/**
 * Fragment for the user account
 */
public class UserFragment extends Fragment {
    private View v;
    private EditText et_email, et_name;
    private Button btn_close;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.fragment_user, container, false);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitializeView();
        //get_info User();
    }
    private void InitializeView() {
        btn_close = (Button) v.findViewById(R.id.btn_close_sesion); // Is this necessary??
        et_email = (EditText) v.findViewById(R.id.et_email_user);
        et_name = (EditText) v.findViewById(R.id.et_username);
        et_name.setText(((MenuActivityNavigation)getActivity()).userapp.getName());
        et_email.setText(((MenuActivityNavigation)getActivity()).userapp.getEmail());
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivityNavigation)getActivity()).closeSession();
            }
        });
    }

}

