package com.univer.public_transport_guide.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.univer.public_transport_guide.R;
import com.univer.public_transport_guide.model.User;


public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister, btnSignGuest;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout registerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnRegister = findViewById(R.id.btnRegister);
        btnSignGuest = findViewById(R.id.btnSignGuest);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://public-transport-guide-5a729-default-rtdb.europe-west1.firebasedatabase.app");
        users = db.getReference("users");



        //registerLayout = findViewById(R.id.register_layout);
        btnRegister.setOnClickListener(view -> showRegisterWindow());
        btnSignIn.setOnClickListener(view -> showSignInWindow());
        btnSignGuest.setOnClickListener(view -> redirectToApp());

    }

    public void showRegisterWindow(){


        Dialog dialog = new Dialog(this);
        //AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setContentView(R.layout.custom_dialog_register);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        MaterialButton btnAdd = dialog.findViewById(R.id.btn_yes);
        MaterialButton btnNo = dialog.findViewById(R.id.btn_no);
        ImageView btnClose = dialog.findViewById(R.id.btn_close);

        View registerWindow = dialog.findViewById(R.id.root_vg);

        final MaterialEditText name = registerWindow.findViewById(R.id.nameField);
        final MaterialEditText password = registerWindow.findViewById(R.id.passField);
        final MaterialEditText email = registerWindow.findViewById(R.id.emailField);

        btnClose.setOnClickListener(view -> dialog.dismiss());
        btnNo.setOnClickListener(view -> dialog.dismiss());

        btnAdd.setOnClickListener(new MaterialButton.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnSuccessListener(authResult -> {
                            User user = new User();
                            user.setEmail(email.getText().toString());
                            user.setName(name.getText().toString());
                            user.setPassword(password.getText().toString());

                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(unused -> {
                                startActivity(new Intent(MainActivity.this, UserActivity.class));
                                finish();
                            });

                        });
            }
        });

        dialog.show();

    }

    public void showSignInWindow(){

       // registerLayout = findViewById(R.id.sign_in_layout);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Sign now");
        dialog.setMessage("Write all now");

        LayoutInflater inflater = LayoutInflater.from(this);
        View signInWindow = inflater.inflate(R.layout.sign_in_window, null);


        dialog.setView(signInWindow);

        final MaterialEditText password = signInWindow.findViewById(R.id.passField);
        final MaterialEditText email = signInWindow.findViewById(R.id.emailField);

        dialog.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        dialog.setPositiveButton("Sign In", (dialogInterface, i) -> {
            if(TextUtils.isEmpty(email.getText().toString())){
                Snackbar.make(registerLayout, "ERROR email", Snackbar.LENGTH_LONG).show();
                return;
            }
            if(password.getText().toString().length() < 6){
                Snackbar.make(registerLayout, "ERROR password", Snackbar.LENGTH_LONG).show();
                return;
            }
            auth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnSuccessListener(authResult -> {
                        startActivity(new Intent(MainActivity.this, UserActivity.class));
                        finish();
                    });
        });
        dialog.show();
    }

    public void redirectToApp(){
        startActivity(new Intent(MainActivity.this, UserActivity.class));
    }

}