package com.example.shopondoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shopondoor.activities.LogInActivity;
<<<<<<< Updated upstream
=======
import com.example.shopondoor.activities.OrderPlacedActivity;
import com.example.shopondoor.adapters.MyCartAdapter;
>>>>>>> Stashed changes
import com.example.shopondoor.databinding.FragmentCatagoryBinding;
import com.example.shopondoor.models.UserModel;
<<<<<<< Updated upstream
=======
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.gms.wallet.callback.OnCompleteListener;
>>>>>>> Stashed changes
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopondoor.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
<<<<<<< Updated upstream

import org.jetbrains.annotations.NotNull;

=======
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

>>>>>>> Stashed changes
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    FragmentManager fragmentManager;
    MyCartFragment myCartFragment=new MyCartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        myCartModelList = new ArrayList<>();


        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_catagory, R.id.nav_profile,R.id.nav_myCart,R.id.nav_newProducts,R.id.nav_myOrders)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FirebaseDatabase database=FirebaseDatabase.getInstance();

        View headerView=navigationView.getHeaderView(0);
        TextView headerName=headerView.findViewById(R.id.nav_name);
        TextView headerEmail=headerView.findViewById(R.id.nav_email);
        CircleImageView headerImage=headerView.findViewById(R.id.nav_profile_img);

        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                        String documentId = documentSnapshot.getId();

                        MyCartModel myCartModel = documentSnapshot.toObject(MyCartModel.class);
                        myCartModel.setDocumentId(documentId);
                        myCartModelList.add(myCartModel);
                    }
                }
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        UserModel userModel=snapshot.getValue(UserModel.class);
                        headerName.setText(userModel.getName());
                        headerEmail.setText(userModel.getEmail());
                        Glide.with(MainActivity.this).load(userModel.getProfileImg()).into(headerImage);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }
                });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout: FirebaseAuth.getInstance().signOut();
                                     startActivity(new Intent(MainActivity.this, LogInActivity.class));
                                     finish();
                                     break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}