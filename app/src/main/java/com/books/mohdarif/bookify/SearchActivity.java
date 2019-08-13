package com.books.mohdarif.bookify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private AutoCompleteTextView autoCompleteTextView;
    private String languages[] = new String[11];
    private ImageView backButton;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int i =0;
    private ImageView searchBar;
    private LinearLayout mainLayout;
    private KProgressHUD kProgressHUD;
    private String setImage;
    private   LinearLayout.LayoutParams layoutParams;
    private String getTitle;
    private String item;
    private ArrayList<String> arrayList;
    private boolean isItemAvailable = false;
    private LinearLayout.LayoutParams cardlayoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        arrayList= new ArrayList<>();
        autoCompleteTextView =findViewById(R.id.autoComplete);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onBackPressed();
            }
        });
        mainLayout = findViewById(R.id.mainLayout);
        kProgressHUD = new KProgressHUD(this);
        kProgressHUD.setStyle(KProgressHUD.Style.PIE_DETERMINATE);
        kProgressHUD.setCancellable(false);
        kProgressHUD.setAnimationSpeed(2);
        kProgressHUD.setDimAmount(0.3f);
        searchBar = findViewById(R.id.search);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cardlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardlayoutParams.setMargins(10,10,10,10);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Languages");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    arrayList.add(dataSnapshot1.getValue(String.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.auto_complete,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               item = adapterView.getItemAtPosition(i).toString();
               searchResult();
               autoCompleteTextView.setText("");
            }
        });
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item = autoCompleteTextView.getText().toString();
                searchResult();
                autoCompleteTextView.setText("");
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        finish();
    }
    public void searchResult() {
        kProgressHUD.show();
         for(String s:arrayList) {
             if (item.equalsIgnoreCase(s)) {
                 item = s;
                 isItemAvailable = true;
                 break;
             }
         }
             if(isItemAvailable) {
                 kProgressHUD.dismiss();
                 mainLayout.removeAllViews();
                 FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                 DatabaseReference databaseReference = firebaseDatabase.getReference("Book1");
                 databaseReference.child(item).addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         try{
                             LinearLayout linearLayout = new LinearLayout(SearchActivity.this);
                             linearLayout.setLayoutParams(layoutParams);
                             linearLayout.setOrientation(LinearLayout.VERTICAL);
                             Book b = dataSnapshot.getValue(Book.class);
                             setImage = b.getImage();
                             getTitle = b.getTitle();
                             final TextView textView = new TextView(SearchActivity.this);
                             textView.setTypeface(ResourcesCompat.getFont(getApplicationContext(),R.font.comfortaa));
                             textView.setTextColor(Color.BLACK);
                             textView.setLayoutParams(cardlayoutParams);
                             textView.setTextSize(10);
                             textView.setText(getTitle);
                             final ImageView imageView = new ImageView(SearchActivity.this);
                             imageView.setLayoutParams(cardlayoutParams);
                             Glide.with(SearchActivity.this).load(setImage).into(imageView);
                             linearLayout.addView(imageView);
                             linearLayout.addView(textView);
                             CardView cardView = new CardView(SearchActivity.this);
                             cardView.setLayoutParams(cardlayoutParams);
                             cardView.setCardBackgroundColor(Color.WHITE);
                             cardView.setElevation(5);
                             cardView.setPadding(10, 10, 10, 10);
                             cardView.setCardElevation(5);
                             cardView.setRadius(5);
                             cardView.setUseCompatPadding(true);
                             cardView.requestLayout();
                             cardView.setOnClickListener(new View.OnClickListener() {
                                 @Override
                                 public void onClick(View view) {
                                     Intent intent = new Intent(SearchActivity.this,ShowBookDetailsActivity.class);
                                     intent.putExtra("title",textView.getText().toString().trim());
                                     startActivity(intent);
                                 }
                             });
                             cardView.addView(linearLayout);
                             mainLayout.addView(cardView);
                         }
                         catch(Exception e){}
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }
             else {
                 kProgressHUD.dismiss();
                 MDToast.makeText(getApplicationContext(), "Book Not Available", MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
             }
         }
}
