package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Notifications extends Fragment {
   private FirebaseDatabase firebaseDatabase;
   private DatabaseReference databaseReference;
    private LinearLayout mainLayout;
    private String title,message;
    private LinearLayout.LayoutParams layoutParams,cardlayoutParams;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        mainLayout = view.findViewById(R.id.mainLayout);
        layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        cardlayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardlayoutParams.setMargins(10, 10, 10, 10);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Notifications");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity(),"BookiFy")
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("BookiFy ")
                                .setContentText("See the notifications");
                NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(001, mBuilder.build());
                if (getActivity() != null) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        LinearLayout linearLayout = new LinearLayout(getActivity());
                        linearLayout.setLayoutParams(layoutParams);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        title= (String) dataSnapshot1.child("title").getValue();
                        message = (String) dataSnapshot1.child("message").getValue();
                        final TextView textView = new TextView(getActivity());
                        textView.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                        textView.setTextColor(Color.BLACK);
                        textView.setLayoutParams(cardlayoutParams);
                        textView.setPadding(10,10,10,10);
                        textView.setTextSize(12);
                        textView.setText(title);
                        final TextView textView1 = new TextView(getActivity());
                        textView1.setTypeface(ResourcesCompat.getFont(getActivity(),R.font.comfortaa));
                        textView1.setTextColor(Color.parseColor("#666666"));
                        textView1.setPadding(10,10,10,10);
                        textView1.setLayoutParams(cardlayoutParams);
                        textView1.setTextSize(10);
                        textView1.setText(message);
                        linearLayout.addView(textView);
                        linearLayout.addView(textView1);
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
                        mainLayout.addView(cardView);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }
}
