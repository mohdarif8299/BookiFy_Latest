package com.books.mohdarif.bookify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

public class ShowBookDetailsActivity extends AppCompatActivity{
    private TextView getTitle,getHeader,getPublisher,getLanguage,getEdition,getDescription,getPages,getAuther;
    private ImageView getImage,back,favourate;
    private KProgressHUD kProgressHUD;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseDatabase    getFirebaseReference;
    private DatabaseReference getDatabseReference;
    private String downloadUrl;
    private Button button;
    private String titleToShow;
   private String title;
   private String message,message2;
    private InterstitialAd mInterstitialAd;
    private static boolean isFavourate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_book_details);
        MobileAds.initialize(this, "ca-app-pub-2316198930014395/2217645284");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2316198930014395/2217645284");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //ca-app-pub-2316198930014395/2217645284
        Intent intent = getIntent();
         message = intent.getStringExtra("title");
         message2 = intent.getStringExtra("titleFromHome");
        back = findViewById(R.id.back);
        favourate = findViewById(R.id.favourate);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });
        kProgressHUD = new KProgressHUD(this);
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        kProgressHUD.setCancellable(false);
        kProgressHUD.setAnimationSpeed(2);
        kProgressHUD.setDimAmount(0.5f);
        kProgressHUD.show();
        getTitle = findViewById(R.id.title);
        getHeader = findViewById(R.id.heading);
        getPublisher = findViewById(R.id.publisher);
        getDescription = findViewById(R.id.description);
        getPages = findViewById(R.id.pages);
        getEdition = findViewById(R.id.edition);
        getLanguage = findViewById(R.id.language);
        getImage = findViewById(R.id.image);
        getAuther = findViewById(R.id.auther);
       if (message==null) {
           titleToShow = message2;
        }
        else {
           titleToShow = message;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              try {
                  firebaseDatabase = FirebaseDatabase.getInstance();
                  reference = firebaseDatabase.getReference("Book1");
                  reference.child(titleToShow).addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {
                        if(getApplicationContext()!=null) {
                            Book b = dataSnapshot.getValue(Book.class);
                            getHeader.setText(b.getTitle());
                            Glide.with(getApplicationContext()).load(b.getImage()).into(getImage);
                            getTitle.setText(b.getTitle());
                            title = b.getTitle();
                            getAuther.setText("Auther : "+b.getAuther());
                            getLanguage.setText("Language :"+b.getLanguage());
                            getDescription.setText(b.getDescription());
                            getEdition.setText("Edition : "+b.getEdition());
                            getPages.setText("Pages : "+b.getPages());
                            getPublisher.setText("Publisher : "+b.getPublisher());
                            downloadUrl = b.getDownloadURL();
                            kProgressHUD.dismiss();
                        }
                      }
                      @Override
                      public void onCancelled(DatabaseError databaseError) {
                      }
                  });
              }
              catch(Exception e) {
                  kProgressHUD.dismiss();
                  MDToast.makeText(getApplicationContext(),"Something went wrong 1 ! Try Again",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
              }
            }
        },2000);
       //checking book that is it favourate or unfavourate
        getFirebaseReference = FirebaseDatabase.getInstance();
        getDatabseReference= getFirebaseReference.getReference("Favourates");
        getDatabseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(titleToShow.equals(dataSnapshot1.getValue(String.class))) {
                        favourate.setImageDrawable(getResources().getDrawable(R.drawable.like_heart));
                        isFavourate = true;
                        return;
                    }
                    else {
                        favourate.setImageDrawable(getResources().getDrawable(R.drawable.unlike_heart));
                        isFavourate = false;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ///handling click on favourate books
        favourate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (!isFavourate) {
                     favourate.setImageDrawable(getResources().getDrawable(R.drawable.like_heart));
                     isFavourate = true;
                     FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                     DatabaseReference databaseReference = firebaseDatabase.getReference("Favourates");
                     databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title).setValue(title).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             MDToast.makeText(getApplicationContext(),"Book Added To Your Favourates",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             kProgressHUD.dismiss();
                             MDToast.makeText(getApplicationContext(),"Something Went Wrong",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                         }
                     });
                 }
                 else {
                     favourate.setImageDrawable(getResources().getDrawable(R.drawable.unlike_heart));
                     isFavourate = false;
                     FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                     DatabaseReference databaseReference = firebaseDatabase.getReference("Favourates");
                     databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(title).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             MDToast.makeText(getApplicationContext(),"Book Removed From Your Favourates",MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
                         }
                     }).addOnFailureListener(new OnFailureListener() {
                         @Override
                         public void onFailure(@NonNull Exception e) {
                             kProgressHUD.dismiss();
                             MDToast.makeText(getApplicationContext(),"Something Went Wrong",MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                         }
                     });;
                 }
            }
        });
        button = findViewById(R.id.download);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(downloadUrl)));
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (message==null) {
          startActivity(new Intent(getApplicationContext(),HomeActivity.class));
          finish();
        }
        else {
         super.onBackPressed();
            finish();
        }
    }
}
