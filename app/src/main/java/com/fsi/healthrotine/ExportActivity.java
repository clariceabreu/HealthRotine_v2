package com.fsi.healthrotine;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Specialist;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;


import java.util.ArrayList;
import java.util.Collections;


public class ExportActivity extends AppCompatActivity {

    private Context context;
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int PICK_FILE_REQUEST = 100;

    static GoogleDriveServiceHelper mDriveServiceHelper;
    static String folderId="";

    DataBase db = new DataBase(this);

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_export);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                handleExport();
            }
        });
        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Exportar");

    }

    /**
     * Starts a sign-in activity using {@link #REQUEST_CODE_SIGN_IN}.
     */
    private void handleExport(){
        requestSignIn();
        System.out.println("signed in");
        createFolder();
        System.out.println("created folder");
        uploadFiles();
        System.out.println("uploaded files");
    }
    private void requestSignIn() {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestEmail()
                        .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        // The result of the sign-in Intent is handled in onActivityResult.
        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    /**
     * Handles the {@code result} of a completed sign-in activity initiated from {@link
     * #requestSignIn()}.
     */
    private void handleSignInResult(Intent result) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    mDriveServiceHelper = new GoogleDriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "Unable to sign in.", exception);
                        showMessage("Unable to sign in.");
                    }
                });
    }

    // This method will get call when user click on create folder button
    public void createFolder() {
        if (mDriveServiceHelper != null) {

            // check folder present or not
            mDriveServiceHelper.isFolderPresent()
                    .addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String id) {
                            if (id.isEmpty()){
                                mDriveServiceHelper.createFolder()
                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                            @Override
                                            public void onSuccess(String fileId) {
                                                Log.e(TAG, "folder id: "+fileId );
                                                folderId=fileId;
                                                showMessage("Folder Created with id: "+fileId);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                showMessage("Couldn't create file.");
                                                Log.e(TAG, "Couldn't create file.", exception);
                                            }
                                        });
                            }else {
                                folderId=id;
                                showMessage("Folder already present");
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showMessage("Couldn't create file..");
                            Log.e(TAG, "Couldn't create file..", exception);
                        }
                    });
        }
    }

    // This method will get call when user click on folder data button
    public void getFolderData(View view) {
        if (mDriveServiceHelper != null) {
            Intent intent = new Intent(this, ListActivity.class);

            mDriveServiceHelper.getFolderFileList()
                    .addOnSuccessListener(new OnSuccessListener<ArrayList<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(ArrayList<GoogleDriveFileHolder> result) {
                            Log.e(TAG, "onSuccess: result: "+result.size() );
                            intent.putParcelableArrayListExtra("fileList", result);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            showMessage("Not able to access Folder data.");
                            Log.e(TAG, "Not able to access Folder data.", e);
                        }
                    });
        }
    }

    // This method will get call when user click on upload file button
    public void uploadFiles() {

        Cursor cursorFilePaths = db.getExamsFilePaths();

        if(cursorFilePaths.moveToFirst()){
            do {
                String selectedFilePath = cursorFilePaths.getString(0);
                System.out.println(selectedFilePath);
                if(selectedFilePath != null && !selectedFilePath.equals("")){
                    if (mDriveServiceHelper != null) {
                        mDriveServiceHelper.uploadFileToGoogleDrive(selectedFilePath)
                                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                                    @Override
                                    public void onSuccess(Boolean result) {

                                        showMessage("File uploaded ...!!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        showMessage("Couldn't able to upload file, error: "+e);
                                    }
                                });
                    }
                }else{
                    Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
                }
            } while (cursorFilePaths.moveToNext());
        }
    }

    // This method will get call when user click on sign-out button
    public void signOut(View view) {
        if (googleSignInClient != null){
            googleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            showMessage("Sign-Out is done...!!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showMessage("Unable to sign out.");
                            Log.e(TAG, "Unable to sign out.", exception);
                        }
                    });
        }
    }

    public void showMessage(String message) {
        Log.i(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
