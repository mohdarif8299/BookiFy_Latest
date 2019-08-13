package com.books.mohdarif.bookify;

import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText username,password;
    private String user_name,pass_word;
    private FirebaseAuth auth;
    private TextView signup;
    private TextView forget;
    private KProgressHUD kProgressHUD;
    static ScrollView parentLayout;
     static CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parentLayout = findViewById(R.id.parentLayout);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null) {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }
        cardView = findViewById(R.id.cardView);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
                finish();
            }
        });
        forget = findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parentLayout.setAlpha(0.2F);
                cardView.setVisibility(View.VISIBLE);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.forgetLayout,new ForgetPassword());
                fragmentTransaction.commit();

            }
        });
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        login.setEnabled(false);
                        user_name = username.getText().toString().trim();
                        pass_word = password.getText().toString().trim();
                        if (TextUtils.isEmpty(user_name.trim())) {
                            MDToast mdToast = MDToast.makeText(getApplicationContext(),"Username Cannot be blank", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                            mdToast.show();
                            username.setText("");
                            password.setText("");
                            login.setEnabled(true);
                            return;
                        } else if (TextUtils.isEmpty(pass_word.trim())) {
                            MDToast mdToast = MDToast.makeText(getApplicationContext(),"Password Cannot be blank", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                            mdToast.show();
                            username.setText("");
                            password.setText("");
                            login.setEnabled(true);
                            return;
                        }
                        login.setEnabled(false);
                        login.setBackground(getResources().getDrawable(R.drawable.on_click_login));
                        login.setTextColor(Color.WHITE);
                            kProgressHUD = new KProgressHUD(LoginActivity.this);
                            kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
                            kProgressHUD.setCancellable(false);
                            kProgressHUD.setAnimationSpeed(2);
                            kProgressHUD.setDimAmount(0.5f);
                            kProgressHUD.show();
                        login.setText("Logging in...");
                        auth.signInWithEmailAndPassword(user_name, pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    login.setEnabled(true);
                                    login.setText("Login");
                                    login.setBackground(getResources().getDrawable(R.drawable.login_button_shape));
                                    login.setTextColor(Color.WHITE);
                                    kProgressHUD.dismiss();
                                    username.setText("");
                                    password.setText("");
                                    MDToast mdToast = MDToast.makeText(getApplicationContext(),"Username or Password Incorrect Try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                                    mdToast.show();
                                } else {
                                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
