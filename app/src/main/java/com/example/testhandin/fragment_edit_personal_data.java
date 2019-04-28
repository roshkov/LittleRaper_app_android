package com.example.testhandin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class fragment_edit_personal_data extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;
    private DatabaseReference userRef;
    EditText nickET;
    EditText aboutET;
    Button saveBtn;;
    String TAG = "mylog========";
    Button changeImg;
    ImageView avatarPic;
    Uri selectedImage;
    Switch mySwitch;
    MainActivity activityReferenceVarMain;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_personal_data, container, false);
        ImageView avatar = view.findViewById(R.id.avatarProfile);
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.sample_prof_pic));


        activityReferenceVarMain = (MainActivity) getActivity();



        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
        Log.i(TAG,"null");}
        else
        {Log.i(TAG,"not null");}
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference("users/"+firebaseUser.getUid().toString());


        avatarPic = view.findViewById(R.id.avatarProfile);

        changeImg = view.findViewById(R.id.changeImgBtn);
        saveBtn = view.findViewById(R.id.saveChangesBtn);
        TextView myemail = view.findViewById(R.id.actualEmail);
        myemail.setText(firebaseUser.getEmail());

         nickET = view.findViewById(R.id.nicknameEditText);
         aboutET = view.findViewById(R.id.aboutEditText);
        mySwitch = view.findViewById(R.id.switchMode);

        if (((ColorDrawable) activityReferenceVarMain.toolbar.getBackground()).getColor()==Color.parseColor(getResources().getString(R.color.AIESECRed)))
        {
            mySwitch.setChecked(true);
        }


            userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                nickET.setText(user.nickname);
                aboutET.setText(user.about);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });





        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //String themeName="";
//                PackageInfo packageInfo;
//                try
//                {
//                    packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
//                    int themeResId = packageInfo.applicationInfo.theme;
//                    themeName = getResources().getResourceEntryName(themeResId);
//                }
//                catch (PackageManager.NameNotFoundException e) { }


                String colorStrRed = getResources().getString(R.color.AIESECRed); //hex of color
                Log.i(TAG, colorStrRed);
                String colorDef =  getResources().getString(R.color.colorPrimaryDark);


                int color = ((ColorDrawable) activityReferenceVarMain.toolbar.getBackground()).getColor();  //color of toolbar in ColorInt
                Log.i(TAG,Integer.toString(color));


//                String defaultValue = String.valueOf(getResources().getColor(R.color.colorPrimary)); //default color if there s nothing at shared preferences
//                Log.i(TAG, defaultValue);
//                String colorT = sharedPreferences.getString("ToolbarTheme", defaultValue);  //retrive current color
//                Log.i(TAG, colorT);
//
//                toolbar = findViewById(R.id.toolbar);
//                setSupportActionBar(toolbar);
//                progressBarMain = findViewById(R.id.slProgress);
//                toolbar.setBackgroundColor(Integer.parseInt(colorT));



                if (((ColorDrawable) activityReferenceVarMain.toolbar.getBackground()).getColor()==Color.parseColor(colorStrRed))  //if equals
                {
                    activityReferenceVarMain.toolbar.setBackgroundColor(Color.parseColor(colorDef));
                    SharedPreferences.Editor editor = activityReferenceVarMain.sharedPreferences.edit();
                    editor.putInt("ToolbarTheme",Color.parseColor(colorDef) );
                    editor.commit();

                }
                else{
                    activityReferenceVarMain.toolbar.setBackgroundColor(Color.parseColor(colorStrRed));
                    SharedPreferences.Editor editor = activityReferenceVarMain.sharedPreferences.edit();
                    editor.putInt("ToolbarTheme",Color.parseColor(colorStrRed) );
                    editor.commit();


                }


//                activityReferenceVarMain.toolbar.setBackgroundColor(Color.parseColor("#000000"));
//                activityReferenceVarMain.toolbar.setBackgroundColor(color);


//                    if(strColor.equals (ResourcesCompat.getColor(getResources(), R.color.AIESECBlue, null) ))
//                    {
//                        activityReferenceVarMain.toolbar.setBackgroundColor(Color.parseColor("#f85a40"));
//                    }
//                    else {activityReferenceVarMain.toolbar.setBackgroundColor(Color.parseColor("#037ef3"));}
//                Log.i(TAG, String.valueOf(activityReferenceVarMain.toolbar.getSolidColor()));

//                if ( themeName.equals("AppTheme")) {
//                    getActivity().getApplication().setTheme(R.style.AIESECTheme);
//                } else {
//                    getActivity().getApplication().setTheme(R.style.AppTheme);
//                }
//                try
//                {
//                    packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
//                    int themeResId = packageInfo.applicationInfo.theme;
//                    themeName = getResources().getResourceEntryName(themeResId);
//                }
//                catch (PackageManager.NameNotFoundException e) { }
//                Log.i(TAG, themeName);
            }
        });


        changeImg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){

                //Create an Intent with action as ACTION_PICK
                Intent intent=new Intent(Intent.ACTION_PICK);
                // Sets the type as image/*. This ensures only components of type image are selected
                intent.setType("image/*");
                //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                // Launching the Intent
                startActivityForResult(intent,10);

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference userRef = mDatabase.child("users");

                Map<String, Object> userUpdates = new HashMap<>();
                String nickname = firebaseUser.getUid().toString()+"/nickname";
                String about = firebaseUser.getUid().toString()+"/about";
//                String imagep = firebaseUser.getUid().toString()+"/userPic";    //not developed yet

                userUpdates.put(nickname, nickET.getText().toString());
                userUpdates.put(about, aboutET.getText().toString());
//                userUpdates.put(imagep,selectedImage.toString());

                userRef.updateChildren(userUpdates);
                Toast.makeText(getActivity(), "Data changed", Toast.LENGTH_LONG).show();

                getActivity().recreate();  //update theme
            }
        });



                return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 10:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    avatarPic.setImageURI(selectedImage);

                    break;
            }
    }

//

}
