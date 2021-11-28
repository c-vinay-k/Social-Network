package com.example.socialize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UnknownUserProfile extends AppCompatActivity {

    String uid;
    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avatartv,covertv;
    TextView nam,email,phone,aget;
    RecyclerView postrecycle;
    StorageReference storageReference;
    String storagepath="Users_Profile_Cover_image/";
    FloatingActionButton chat;
    List<ModelPost> posts;
    AdapterPosts adapterPosts;
    ProgressDialog pd;

    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknown_user_profile);

        uid=getIntent().getStringExtra("uid");

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");
        avatartv=findViewById(R.id.ukavatartv);
        nam=findViewById(R.id.uknametv);
        email=findViewById(R.id.ukemailtv);
        aget=findViewById(R.id.ukagetv);
        chat=findViewById(R.id.ukchat);
        postrecycle=findViewById(R.id.ukrecyclerposts);
        posts=new ArrayList<>();
        pd=new ProgressDialog(UnknownUserProfile.this);
        loadPosts();
        pd.setCanceledOnTouchOutside(false);

        users=firebaseDatabase.getReference("Users");

        Query query=users.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    String name=""+dataSnapshot1.child("name").getValue();
                    String emaill=""+dataSnapshot1.child("email").getValue();
                    String image=""+dataSnapshot1.child("image").getValue();
                    String agey=""+dataSnapshot1.child("bio").getValue();
                    nam.setText(name);
                    email.setText(emaill);
                    aget.setText(agey);
                    try {

                        if(image.isEmpty()){
                            Glide.with(UnknownUserProfile.this).load(R.drawable.profile_image).into(avatartv);
                        }else {
                            Glide.with(UnknownUserProfile.this).load(image).into(avatartv);
                        }

                        //Glide.with(getActivity()).load(image).into(avatartv);
                    }catch (Exception e){

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UnknownUserProfile.this,ChatActivity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
            }
        });

        /*private void loadPosts() {
            LinearLayoutManager layoutManager=new LinearLayoutManager(UnknownUserProfile.this);
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
            postrecycle.setLayoutManager(layoutManager);

            DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Posts");
            Query query=databaseReference.orderByChild("uid").equalTo(uid);
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    posts.clear();
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        ModelPost modelPost=dataSnapshot1.getValue(ModelPost.class);
                        posts.add(modelPost);
                        adapterPosts=new AdapterPosts(UnknownUserProfile.this,posts);
                        postrecycle.setAdapter(adapterPosts);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(UnknownUserProfile.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        } */



    }

    private void loadPosts() {
        LinearLayoutManager layoutManager=new LinearLayoutManager(UnknownUserProfile.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        postrecycle.setLayoutManager(layoutManager);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Posts");
        Query query=databaseReference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    ModelPost modelPost=dataSnapshot1.getValue(ModelPost.class);
                    posts.add(modelPost);
                    adapterPosts=new AdapterPosts(UnknownUserProfile.this,posts);
                    postrecycle.setAdapter(adapterPosts);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(UnknownUserProfile.this,databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}