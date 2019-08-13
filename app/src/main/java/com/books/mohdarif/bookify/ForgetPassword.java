package com.books.mohdarif.bookify;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bookify.mohdarif.bookify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.valdesekamdem.library.mdtoast.MDToast;

public class ForgetPassword extends Fragment {
    private Button close;
    private Button sendMail;
    private EditText email;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forget_password, container, false);
        close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.cardView.setVisibility(View.GONE);
                LoginActivity.parentLayout.setAlpha(1f);
            }
        });
        email = view.findViewById(R.id.email);
        sendMail = view.findViewById(R.id.sendMail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user_email = email.getText().toString().trim();
                if(TextUtils.isEmpty(user_email)) {
                    MDToast mdToast = MDToast.makeText(getActivity(),"Email cannot be empty", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                    return;
                }
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                MDToast mdToast = MDToast.makeText(getActivity(),"An email with password reset link has been send to your email. Check it", MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS);
                                mdToast.show();
                                LoginActivity.cardView.setVisibility(View.GONE);
                                LoginActivity.parentLayout.setAlpha(1f);
                            }
                            else {
                                MDToast mdToast = MDToast.makeText(getActivity(),"Invalid Email Address. Try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                                mdToast.show();
                                email.setText("");
                            }
                        }
                    });
                }

            }
        });
        return view;
    }
}
