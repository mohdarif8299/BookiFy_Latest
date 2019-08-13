package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.valdesekamdem.library.mdtoast.MDToast;

public class Recommendations extends Fragment {
    private EditText getTitle,getAuther,getLanguage,getEdition;
    private Button button;
    private AlertDialog.Builder builder;
    private ImageView backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_recommendations, container, false);
        getAuther = view.findViewById(R.id.auther);
        getEdition = view.findViewById(R.id.edition);
        builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        getLanguage = view.findViewById(R.id.language);
        getTitle = view.findViewById(R.id.title);
        button = view.findViewById(R.id.recommend);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = getTitle.getText().toString().trim();
                String auther = getAuther.getText().toString().trim();
                String edition = getEdition.getText().toString().trim();
                String language = getLanguage.getText().toString().trim();
                if(TextUtils.isEmpty(title)) {
                    MDToast.makeText(getActivity(),"Title Cannot be Empty",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                    return;
                }
               else {
                    RecommendedBooks books = new RecommendedBooks(language,title,edition,auther,FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Recommendations");
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(books);
                    builder.setMessage("Thanks For Helping Us!!")
                            .setCancelable(false)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id){
                                    dialog.cancel();
                                    getTitle.setText("");
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("BookiFy");
                    alert.show();
                    TextView textView = alert.findViewById(android.R.id.message);
                    textView.setTypeface(getResources().getFont(R.font.comfortaa));
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(12);
                }
            }
        });
        return view;
    }
}
