package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

public class Profile extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private TextView setEmail;
    private KProgressHUD kProgressHUD;
    private String firstName,lastName,mNumber,email,password;
    private EditText getFname,getLname,getNumber;
    private Button saveData;
    private ImageView backButton;
  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_profile, container, false);
       getFname = view.findViewById(R.id.fname);
       getLname = view.findViewById(R.id.lname);
       getNumber = view.findViewById(R.id.mobile);
       setEmail = view.findViewById(R.id.email);
       saveData = view.findViewById(R.id.save);
       backButton = view.findViewById(R.id.back_button);
      backButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              getActivity().onBackPressed();
          }
      });
       firebaseDatabase = FirebaseDatabase.getInstance();
       databaseReference = firebaseDatabase.getReference("Users");
       databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               User user = dataSnapshot.getValue(User.class);
               setEmail.setText(user.getEmail());
               password = user.getPassword();
              try {
                  getFname.setText(user.getFname());
                  getLname.setText(user.getLname());
                  getNumber.setText(user.getNumber());
              }
              catch(Exception e){

              }
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });
       saveData.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               email = setEmail.getText().toString();
               firstName = getFname.getText().toString();
               lastName = getLname.getText().toString();
               mNumber = getNumber.getText().toString();
               if (TextUtils.isEmpty(firstName)) {
                   MDToast.makeText(getActivity(), "First name cannot be blank", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                   return;
               } else if (TextUtils.isEmpty(lastName)) {
                   MDToast.makeText(getActivity(), "Last name cannot be blank", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                   return;
               } else if (TextUtils.isEmpty(mNumber)) {
                   MDToast.makeText(getActivity(), "Number cannot be blank", MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
                   return;
               } else {
                   kProgressHUD = new KProgressHUD(getActivity());
                   kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
                   kProgressHUD.setCancellable(false);
                   kProgressHUD.setAnimationSpeed(2);
                   kProgressHUD.setDimAmount(0.5f);
                   kProgressHUD.show();
                   new Handler().postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           final User user = new User(firstName, lastName, email,password,mNumber);
                           FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                           DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
                           databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).push().setValue(user);
                           kProgressHUD.dismiss();
                           MDToast.makeText(getActivity(), "Updated Successfully", MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
                           getFname.setText("");
                           getLname.setText("");
                           getNumber.setText("");
                           return;
                       }
                   }, 2000);
               }
           }
       });
       return view;
    }
}
