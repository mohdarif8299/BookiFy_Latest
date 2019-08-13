package com.books.mohdarif.bookify;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bookify.mohdarif.bookify.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
public class HomeActivity extends AppCompatActivity implements  View.OnClickListener {
    private RelativeLayout home,books,notifications,setting;
    private ImageView home_icon,book_icon,notifications_icon,setting_icon;
  //  private TextView home_text,book_text,notifications_text,setting_text;
    private   Fragment currentFragment;
    private TextView searchView;
    private InterstitialAd mInterstitialAd;
    private ProgressBar progressBar;
    private FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        MobileAds.initialize(this, "ca-app-pub-2316198930014395/2217645284");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2316198930014395/2217645284");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        progressBar = findViewById(R.id.progress);
        home = findViewById(R.id.home);
        books = findViewById(R.id.yourBooks_);
        notifications = findViewById(R.id.notifications);
        setting = findViewById(R.id.setting);
        home_icon = findViewById(R.id.home_icon);
       // home_text = findViewById(R.id.home_text);
        book_icon = findViewById(R.id.books_icon);
       // book_text  = findViewById(R.id.books_text);
        notifications_icon = findViewById(R.id.notifications_icon);
       // notifications_text = findViewById(R.id.notifications_text);
        setting_icon = findViewById(R.id.setting_icon);
       // setting_text = findViewById(R.id.setting_text);
        searchView = findViewById(R.id.search);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),SearchActivity.class));
              finish();
            }
        });
        home.setOnClickListener(this);
        books.setOnClickListener(this);
        notifications.setOnClickListener(this);
        setting.setOnClickListener(this);
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed(){
//                home_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_home));
//                home_text.setTextColor(getResources().getColor(R.color.colorPrimary));
//                book_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_yourbooks));
//                book_text.setTextColor(Color.BLACK);
//                notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_notification));
//                notifications_text.setTextColor(Color.BLACK);
//                setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_setting));
//                setting_text.setTextColor(Color.BLACK);
//                fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.mainLayout, new Home());
//                fragmentTransaction.commit();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        switch (view.getId()) {
            case R.id.home:
                         loadHome();
                break;
            case R.id.yourBooks_:
               loadBooks();
                break;
            case R.id.notifications:
                loadNotification();
                break;
            case R.id.setting:
              loadSetting();
                break;
                default:
                   loadHome();
                    break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        home_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_home));
   //     home_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        book_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_yourbooks));
      //  book_text.setTextColor(Color.BLACK);
        notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_notification));
       // notifications_text.setTextColor(Color.BLACK);
        setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_setting));
       // setting_text.setTextColor(Color.BLACK);
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, new Home());
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        currentFragment = getFragmentManager().findFragmentById(R.id.mainLayout);
       if(currentFragment instanceof Home||currentFragment instanceof FavourateBooks||currentFragment instanceof Notifications||currentFragment instanceof Settings) {
           finish();
       }
      else if (currentFragment instanceof Profile||currentFragment instanceof Recommendations||currentFragment instanceof HelpCentre||currentFragment instanceof PrivacyPolicy||currentFragment instanceof AboutBookiFy) {
           loadSetting();
       }
       else {
           finish();
       }
    }
    public void loadHome() {
        currentFragment = getFragmentManager().findFragmentById(R.id.mainLayout);
        if(currentFragment instanceof Home) {

        }
        else {
            home_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_home));
        //    home_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            book_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_yourbooks));
           // book_text.setTextColor(Color.BLACK);
            notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_notification));
          //  notifications_text.setTextColor(Color.BLACK);
            setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_setting));
           // setting_text.setTextColor(Color.BLACK);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainLayout, new Home());
            fragmentTransaction.commit();
        }
    }
    public void loadBooks(){
        currentFragment = getFragmentManager().findFragmentById(R.id.mainLayout);
        if(currentFragment instanceof FavourateBooks) {

        }
        else {
            home_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_home));
       //     home_text.setTextColor(Color.BLACK);
            book_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_yourbooks));
          //  book_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_notification));
           // notifications_text.setTextColor(Color.BLACK);
            setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_setting));
           // setting_text.setTextColor(Color.BLACK);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainLayout, new FavourateBooks());
            fragmentTransaction.commit();
        }
    }
    public void loadSetting(){
        home_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_home));
       // home_text.setTextColor(Color.BLACK);
        book_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_yourbooks));
       // book_text.setTextColor(Color.BLACK);
        notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_notification));
       // notifications_text.setTextColor(Color.BLACK);
        setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_setting));
        //setting_text.setTextColor(getResources().getColor(R.color.colorPrimary));
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout,new Settings());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    public void loadNotification(){
        currentFragment = getFragmentManager().findFragmentById(R.id.mainLayout);
        if(currentFragment instanceof Notifications) {
        }
        else {
            home_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_home));
           // home_text.setTextColor(Color.BLACK);
            book_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_yourbooks));
           // book_text.setTextColor(Color.BLACK);
            notifications_icon.setImageDrawable(getResources().getDrawable(R.drawable.clicked_notification));
           // notifications_text.setTextColor(getResources().getColor(R.color.colorPrimary));
            setting_icon.setImageDrawable(getResources().getDrawable(R.drawable.unclicked_setting));
           // setting_text.setTextColor(Color.BLACK);
            fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainLayout,new Notifications());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
