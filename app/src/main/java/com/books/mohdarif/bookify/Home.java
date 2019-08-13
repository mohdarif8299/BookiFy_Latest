package com.books.mohdarif.bookify;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class Home extends Fragment {
    private LinearLayout mainLayout;
    private Spinner spin;
    private String setTitle, setImage;
    private LinearLayout.LayoutParams layoutParams;
    private String getTitle, setReference;
    private LinearLayout.LayoutParams cardlayoutParams;
    private String[] languages = {"All Programming Languages","Data Structures","Java","CSS","Android","Bootstrap"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        spin = view.findViewById(R.id.spinner);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cardlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardlayoutParams.setMargins(10, 10, 10, 10);
        spin.setSelection(0);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mainLayout.removeAllViews();
                setReference = languages[i];
                if (setReference.equals("All Programming Languages"))
                    setReference = "Home";
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference(setReference);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                LinearLayout linearLayout = new LinearLayout(getActivity());
                                linearLayout.setLayoutParams(layoutParams);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);
                                setImage = (String) dataSnapshot1.child("image").getValue();
                                getTitle = (String) dataSnapshot1.child("title").getValue();
                                final TextView textView = new TextView(getActivity());
                                textView.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                                textView.setTextColor(Color.BLACK);
                                textView.setLayoutParams(cardlayoutParams);
                                textView.setTextSize(10);
                                textView.setText(getTitle);
                                final ImageView imageView = new ImageView(getActivity());
                                imageView.setLayoutParams(cardlayoutParams);
                                Glide.with(getActivity()).load(setImage).into(imageView);
                                linearLayout.addView(imageView);
                                linearLayout.addView(textView);
                                CardView cardView = new CardView(getActivity());
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
                                        setTitle = textView.getText().toString();
                                        Intent intent = new Intent(getActivity(), ShowBookDetailsActivity.class);
                                        intent.putExtra("titleFromHome", setTitle);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                                cardView.addView(linearLayout);
                                    mainLayout.addView(cardView);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter aa = new ArrayAdapter(getActivity(), R.layout.spinner_item, languages);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        return view;
    }
}