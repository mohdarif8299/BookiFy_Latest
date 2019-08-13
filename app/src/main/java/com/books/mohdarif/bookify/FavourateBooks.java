package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valdesekamdem.library.mdtoast.MDToast;

public class FavourateBooks extends Fragment{
   private LinearLayout mainLayout;
   private FirebaseDatabase getFirebaseReference;
   private DatabaseReference getDatabseReference;
    private AlertDialog.Builder builder;
   private   LinearLayout.LayoutParams layoutParams,cardlayoutParams,buttonParams;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_favourate_books, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        builder = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cardlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardlayoutParams.setMargins(10,10,10,10);
        buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80);
        getFirebaseReference = FirebaseDatabase.getInstance();
        getDatabseReference= getFirebaseReference.getReference("Favourates");
        getDatabseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()!=null) {
                    for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       final LinearLayout linearLayout = new LinearLayout(getActivity());
                       linearLayout.setLayoutParams(layoutParams);
                       linearLayout.setOrientation(LinearLayout.VERTICAL);
                       linearLayout.setPadding(10,10,10,10);
                       FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                       DatabaseReference databaseReference = firebaseDatabase.getReference("Book1");
                       databaseReference.child(dataSnapshot1.getValue(String.class)).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                              if (getActivity()!=null){
                                  final Book b = dataSnapshot.getValue(Book.class);
                                  final TextView title = new TextView(getActivity());
                                  title.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                                  title.setTextColor(Color.BLACK);
                                  title.setLayoutParams(cardlayoutParams);
                                  title.setTextSize(10);
                                  title.setText(b.getTitle());
                                  ImageView imageView = new ImageView(getActivity());
                                  imageView.setLayoutParams(cardlayoutParams);
                                  Glide.with(getActivity()).load(b.getImage()).into(imageView);
                                  Button button = new Button(getActivity());
                                  button.setStateListAnimator(null);
                                  button.setTextColor(Color.WHITE);
                                  button.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                                  button.setTextSize(10);
                                  button.setBackgroundColor(Color.parseColor("#ff7800"));
                                  button.setText("OPEN");
                                  button.setLayoutParams(buttonParams);
                                  button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                         startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(b.getDownloadURL())));
                                      }
                                  });
                                  linearLayout.addView(imageView);
                                  linearLayout.addView(title);
                                  linearLayout.addView(button);
                                  CardView cardView = new CardView(getActivity());
                                  cardView.setLayoutParams(cardlayoutParams);
                                  cardView.setCardBackgroundColor(Color.WHITE);
                                  cardView.setElevation(5);
                                  cardView.setPadding(10, 10, 10, 10);
                                  cardView.setCardElevation(5);
                                  cardView.setRadius(5);
                                  cardView.setUseCompatPadding(true);
                                  cardView.requestLayout();
                                  cardView.addView(linearLayout);
                                  cardView.setOnLongClickListener(new View.OnLongClickListener() {
                                      @Override
                                      public boolean onLongClick(View view) {
                                          builder.setMessage("Remove this book from favourates ? ")
                                                  .setCancelable(false)
                                                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int id) {
                                                          FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                          DatabaseReference databaseReference = firebaseDatabase.getReference("Favourates");
                                                          databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title.getText().toString()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                              @Override
                                                              public void onSuccess(Void aVoid) {
                                                                  MDToast.makeText(getActivity(),"Book Removed From Your Favourates",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                                                                  FragmentTransaction   fragmentTransaction = getFragmentManager().beginTransaction();
                                                                  fragmentTransaction.replace(R.id.mainLayout, new FavourateBooks());
                                                                  fragmentTransaction.commit();
                                                              }
                                                          }).addOnFailureListener(new OnFailureListener() {
                                                              @Override
                                                              public void onFailure(@NonNull Exception e) {
                                                                  MDToast.makeText(getActivity(),"Something Went Wrong",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                                                              }
                                                          });
                                                      }
                                                  })
                                                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                      public void onClick(DialogInterface dialog, int id) {
                                                          dialog.cancel();
                                                      }
                                                  });
                                          AlertDialog alert = builder.create();
                                          alert.setTitle("BookiFy");
                                          alert.show();
                                          TextView textView = alert.findViewById(android.R.id.message);
                                          textView.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                                          textView.setTextColor(Color.BLACK);
                                          textView.setTextSize(12);
                                          return true;
                                      }
                                  });
                                  mainLayout.addView(cardView);
                              }
                           }
                           @Override
                           public void onCancelled(DatabaseError databaseError) {
                           }
                       });
                   }
               }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                MDToast.makeText(getActivity(),databaseError.toString(),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
            }
        });
        return view;
    }
}
