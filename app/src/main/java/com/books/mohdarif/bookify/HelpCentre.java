package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
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

public class HelpCentre extends Fragment {
    private EditText subject,description;
    private Button button;
    private AlertDialog.Builder builder;
    private ImageView backButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help_centre, container, false);
        subject = view.findViewById(R.id.subject);
        description = view.findViewById(R.id.description);
        backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        button = view.findViewById(R.id.report);
        builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getSubject = subject.getText().toString().trim();
                final String getDescription = description.getText().toString().trim();
                if(TextUtils.isEmpty(getSubject)) {
                    MDToast.makeText(getActivity(),"Subject Cannot be Empty",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                    return;
                }
                else if(TextUtils.isEmpty(getDescription)) {
                    MDToast.makeText(getActivity(),"Description Cannot be Empty",MDToast.LENGTH_SHORT,MDToast.TYPE_WARNING).show();
                    return;
                }
               else {
                    BugReport bugReport = new BugReport(getSubject,getDescription,FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("Bugs");
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(bugReport);
                    builder.setMessage("Thanks for the report . We will response to it as soon as possible !!")
                            .setCancelable(false)
                            .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id){
                                    dialog.cancel();
                                    subject.setText("");
                                    description.setText("");
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.setTitle("BookiFy");
                    alert.show();
                    TextView textView = alert.findViewById(android.R.id.message);
                    textView.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(12);
                }
            }
        });
        return view;
    }
}
