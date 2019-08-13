package com.books.mohdarif.bookify;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;
public class SignupActivity extends AppCompatActivity {
    private EditText email,password,cpassword;
    private String username,pass_word,c_password;
    private TextView back_to_login;
    private Button signup;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        cpassword = findViewById(R.id.cpassword);
        signup = findViewById(R.id.signup);
        back_to_login = findViewById(R.id.login);
        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress);
        progressBar.setVisibility(View.GONE);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup.setEnabled(false);
                username = email.getText().toString();
                pass_word = password.getText().toString();
                c_password =cpassword.getText().toString();
                if (TextUtils.isEmpty(username)){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(),"Email Cannot be blank", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                    signup.setEnabled(true);
                    return;
                }else if (TextUtils.isEmpty(pass_word)){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(),"Password Cannot be blank", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                    signup.setEnabled(true);
                    return;
                } else if(!pass_word.equals(c_password)) {
                    MDToast mdToast = MDToast.makeText(getApplicationContext(),"Password not matching", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                    signup.setEnabled(true);
                    return;
                }
                else if (pass_word.length()<6){
                    MDToast mdToast = MDToast.makeText(getApplicationContext(),"Password must contains at least 6 characters or numerics", MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING);
                    mdToast.show();
                    signup.setEnabled(true);
                    return;
                }
                signup.setEnabled(false);
                signup.setBackground(getResources().getDrawable(R.drawable.on_click_login));
                signup.setTextColor(Color.WHITE);
                progressBar.setVisibility(View.VISIBLE);
                signup.setText("Signing up...");
                auth.createUserWithEmailAndPassword(username,pass_word).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            User user = new User(username,pass_word);
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            databaseReference = firebaseDatabase.getReference("Users");
                            databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(user);
                            MDToast.makeText(getApplicationContext(),"Account Created Successfully",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            finish();
                            auth.signOut();
                        }else{
                            MDToast mdToast = MDToast.makeText(getApplicationContext(),"Something went wrong, try again", MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                            mdToast.show();
                            signup.setEnabled(true);
                            signup.setBackground(getResources().getDrawable(R.drawable.login_button_shape));
                            signup.setText("Signup");
                            progressBar.setVisibility(View.GONE);
                            return;
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
