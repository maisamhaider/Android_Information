package com.dragster.android.information.system.my.android.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.dragster.android.information.system.my.android.activities.MainActivity;
import com.dragster.android.information.system.my.android.utilities.ContextBaseGetFunctionsFromHere;
import com.dragster.android.information.system.my.android.R;
import com.dragster.android.information.system.my.android.activities.AllBasicInfo;
import com.dragster.android.information.system.my.android.activities.BatteryInfo;
import com.dragster.android.information.system.my.android.activities.CameraInfo;
import com.dragster.android.information.system.my.android.activities.ConnectedInternet;
import com.dragster.android.information.system.my.android.activities.HardwareTesting;
import com.dragster.android.information.system.my.android.activities.InternetSpeed;
import com.dragster.android.information.system.my.android.activities.InternetUsage;
import com.dragster.android.information.system.my.android.activities.Memory_Act;
import com.dragster.android.information.system.my.android.activities.MyPhoneApps;
import com.dragster.android.information.system.my.android.activities.Ram_Act;
import com.dragster.android.information.system.my.android.activities.ScreenDensity;
import com.dragster.android.information.system.my.android.activities.ScreenSize;
import com.dragster.android.information.system.my.android.activities.Screenshot;
import com.dragster.android.information.system.my.android.activities.CacheAndEmptyFolders;
import com.dragster.android.information.system.my.android.classes.Permissions;
import com.dragster.android.information.system.my.android.utilities.GetSomething;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class MainFragment extends Fragment implements View.OnClickListener, BillingProcessor.IBillingHandler {
    private InterstitialAd interstitialAd;
    private ContextBaseGetFunctionsFromHere getFunctionsFromHere;
    private BillingProcessor billingProcessor;
    private static String value;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;
    private ImageView mainFragMenu_iv;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_main, container, false );
        billingProcessor = new BillingProcessor( getContext(), setFirebasKey(), this );
        billingProcessor.initialize();
        MainActivity mainActivity = new MainActivity();
        sharedPreference = getActivity().getSharedPreferences( "MY_PREFERENCES", Context.MODE_PRIVATE );
        editor = sharedPreference.edit();

        getFunctionsFromHere = new ContextBaseGetFunctionsFromHere( getActivity() );
        interstitialAd = new InterstitialAd( getActivity() );
        interstitialAd.setAdUnitId( getResources().getString( R.string.interstitial ) );
        mainFragMenu_iv = view.findViewById( R.id.mainFragMenu_iv );

        reqNewInterstitial();
        LinearLayout androidTips_LL, androidVersion_LL, cameraInfo_LL, internetSpeed_LL,
                internetUsage_LL, memory_LL, connectedInternet_LL, myPhoneApps_LL, ram_LL, screenDensity_LL,
                screenShot_LL, screenSize_LL, batteryStatus_LL, hardwareTesting_LL, cacheAndEmptyFolders_LL;
        GetSomething getSomething = new GetSomething();
        Permissions permissions = new Permissions( getContext() );
        permissions.permission();
        mAdView = view.findViewById( R.id.mainFrag_adv );
        androidTips_LL = view.findViewById( R.id.androidTips_LL );
        androidVersion_LL = view.findViewById( R.id.AllBasicInfo_LL );
        cameraInfo_LL = view.findViewById( R.id.cameraInfo_LL );
        internetSpeed_LL = view.findViewById( R.id.internetSpeed_LL );
        internetUsage_LL = view.findViewById( R.id.internetUsage_LL );
        memory_LL = view.findViewById( R.id.mainMemoryButton_RL );
        ram_LL = view.findViewById( R.id.ram_LL );
        screenDensity_LL = view.findViewById( R.id.screenDensity_LL );
        screenShot_LL = view.findViewById( R.id.screenshot_LL );
        screenSize_LL = view.findViewById( R.id.screenDisplaySize_LL );
        myPhoneApps_LL = view.findViewById( R.id.myAndroidButtonMain_LL );
        connectedInternet_LL = view.findViewById( R.id.connectedInternet_LL );
        batteryStatus_LL = view.findViewById( R.id.batteryStatus_LL );
        hardwareTesting_LL = view.findViewById( R.id.hardwareTesting_LL );
        cacheAndEmptyFolders_LL = view.findViewById( R.id.cacheAndEmptyFolders_LL );

        androidTips_LL.setOnClickListener( this );
        androidVersion_LL.setOnClickListener( this );
        cameraInfo_LL.setOnClickListener( this );
        internetSpeed_LL.setOnClickListener( this );
        internetUsage_LL.setOnClickListener( this );
        memory_LL.setOnClickListener( this );
        ram_LL.setOnClickListener( this );
        screenDensity_LL.setOnClickListener( this );
        screenShot_LL.setOnClickListener( this );
        screenSize_LL.setOnClickListener( this );
        myPhoneApps_LL.setOnClickListener( this );
        connectedInternet_LL.setOnClickListener( this );
        batteryStatus_LL.setOnClickListener( this );
        hardwareTesting_LL.setOnClickListener( this );
        cacheAndEmptyFolders_LL.setOnClickListener( this );
        //main frag menu
        mainFragMenu_iv.setOnClickListener( this );
        MobileAds.initialize( getContext(), getResources().getString( R.string.banner ) );
        if (mainActivity.isPaid) {
            mAdView.setVisibility( View.GONE );
        } else {

           getSomething.bannerAD(mAdView);
        }
        return view;
    }

    private void reqNewInterstitial() {
        interstitialAd.loadAd( new AdRequest.Builder().build() );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myAndroidButtonMain_LL:
                Intent myPhoneApps = new Intent( getActivity(), MyPhoneApps.class );
                startActivity( myPhoneApps );
                break;
            case R.id.AllBasicInfo_LL:
                Intent allBasicInfo = new Intent( getActivity(), AllBasicInfo.class );
                startActivity( allBasicInfo );
                break;

            case R.id.cameraInfo_LL:
                Intent cameraInfo = new Intent( getActivity(), CameraInfo.class );
                startActivity( cameraInfo );
                break;
            case R.id.internetSpeed_LL:
                if (sharedPreference.getBoolean( "is_purchase", false )) {
                    Intent internetSpeed = new Intent( getActivity(), InternetSpeed.class );
                    startActivity( internetSpeed );
                } else {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent internetSpeed = new Intent( getActivity(), InternetSpeed.class );
                        startActivity( internetSpeed );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent internetSpeed = new Intent( getActivity(), InternetSpeed.class );
                            startActivity( internetSpeed );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.internetUsage_LL:

                if (getFunctionsFromHere.isSomeIssueWithSimCard()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
                    View view = LayoutInflater.from( getContext() ).inflate( R.layout.is_sim_not_inserted_alerdialog_lo, null );
                    builder.setView( view );
                    builder.setCancelable( true );
                    Button ok = view.findViewById( R.id.noSimCardOk_btn );

                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
                    dialog.show();
                    ok.setOnClickListener( v1 -> {
                        dialog.dismiss();
                    } );
                } else {
                    Intent internetUsage = new Intent( getActivity(), InternetUsage.class );
                    startActivity( internetUsage );
                }
                break;
            case R.id.mainMemoryButton_RL:

                Intent memory_Act = new Intent( getActivity(), Memory_Act.class );
                startActivity( memory_Act );
                break;
            case R.id.ram_LL:

                Intent ram_Act = new Intent( getActivity(), Ram_Act.class );
                startActivity( ram_Act );
                break;
            case R.id.screenDensity_LL:

                if (sharedPreference.getBoolean( "is_purchase", false )) {
                    Intent screenDensity = new Intent( getActivity(), ScreenDensity.class );
                    startActivity( screenDensity );
                } else {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent screenDensity = new Intent( getActivity(), ScreenDensity.class );
                        startActivity( screenDensity );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent screenDensity = new Intent( getActivity(), ScreenDensity.class );
                            startActivity( screenDensity );
                            reqNewInterstitial();
                        }
                    } );
                }


                break;
            case R.id.screenshot_LL:

                Intent screenshot = new Intent( getActivity(), Screenshot.class );
                startActivity( screenshot );
                break;
            case R.id.screenDisplaySize_LL:
                Intent screenSize = new Intent( getActivity(), ScreenSize.class );
                startActivity( screenSize );
                break;
            case R.id.connectedInternet_LL:

                Intent connectedInternet = new Intent( getActivity(), ConnectedInternet.class );
                startActivity( connectedInternet );
                break;
            case R.id.batteryStatus_LL:
                if (sharedPreference.getBoolean( "is_purchase", false )) {
                    Intent batteryInfoIntent = new Intent( getActivity(), BatteryInfo.class );
                    startActivity( batteryInfoIntent );
                } else {
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Intent batteryInfoIntent = new Intent( getActivity(), BatteryInfo.class );
                        startActivity( batteryInfoIntent );
                    }
                    interstitialAd.setAdListener( new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Intent batteryInfoIntent = new Intent( getActivity(), BatteryInfo.class );
                            startActivity( batteryInfoIntent );
                            reqNewInterstitial();
                        }
                    } );
                }

                break;
            case R.id.hardwareTesting_LL:
                Intent hardwareTestingIntent = new Intent( getActivity(), HardwareTesting.class );
                startActivity( hardwareTestingIntent );
                break;
            case R.id.cacheAndEmptyFolders_LL:
                Intent cacheAndEmptyFoldersIntent = new Intent( getActivity(), CacheAndEmptyFolders.class );
                startActivity( cacheAndEmptyFoldersIntent );
                break;

            //main Frag Menu view
            case R.id.mainFragMenu_iv:
                PopupWindow popupwindow_obj = popupWindowDisplay();
                popupwindow_obj.showAsDropDown( mainFragMenu_iv );
                break;


            default:
                break;
        }
    }

    private PopupWindow popupWindowDisplay() {
        final PopupWindow popupWindow = new PopupWindow( getContext() );
        // inflate your layout or dynamically add view
        final LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        View view = inflater.inflate( R.layout.main_frag_menu_layout, null );

        LinearLayout noAdd_ll = view.findViewById( R.id.noAdd_ll );
        LinearLayout moreApps_ll = view.findViewById( R.id.moreApps_ll );
        LinearLayout rateUs_ll = view.findViewById( R.id.rateUs_ll );

        popupWindow.setFocusable( true );
        popupWindow.setWidth( WindowManager.LayoutParams.WRAP_CONTENT );
        popupWindow.setHeight( WindowManager.LayoutParams.WRAP_CONTENT );
        popupWindow.setContentView( view );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );

        noAdd_ll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billingProcessor.purchase( getActivity(), Objects.requireNonNull( getContext() ).getPackageName() );
            }
        } );
        moreApps_ll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://play.google.com/store/apps/developer?id=Innovagic+Technologies" ) ) );
            }
        } );
        rateUs_ll.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() ) ) );

            }
        } );

        popupWindow.setOnDismissListener( new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        } );

        return popupWindow;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if (!billingProcessor.handleActivityResult( requestCode, resultCode, data )) {
            super.onActivityResult( requestCode, resultCode, data );
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        editor.putBoolean( "is_purchase", true ).commit();

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

    }

    public static String setFirebasKey() {
//        Dragster_Production
//                MyAndroid
//        License_Key

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Dragster_Production" ).child( "MyAndroid" );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value = dataSnapshot.child( "License_Key" ).getValue( String.class );
//                Log.e("TAG", value);
//                key = value;
                try {
//                    SharedPrefUtils.saveData(getApplicationContext(), "lickey", AESUtils.encrypt(value));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d( "TAG", "loadPost:onCancelled", databaseError.toException() );
            }
        } );
        return value;
    }
}
