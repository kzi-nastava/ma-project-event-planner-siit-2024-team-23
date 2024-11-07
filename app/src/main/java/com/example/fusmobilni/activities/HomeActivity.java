package com.example.fusmobilni.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.fusmobilni.R;
import com.example.fusmobilni.databinding.ActivityHomeBinding;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.OnBackPressedCallback;

import java.util.HashSet;
import java.util.Set;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding _binding;
    private AppBarConfiguration _topAppBarConfiguration;
    private DrawerLayout _drawer;
    private NavigationView _navigationView;
    private NavController _navController;
    private Toolbar _topToolbar;
    private ActionBar _actionBar;
    private ActionBarDrawerToggle _actionBarDrawerToggle;

    private Set<Integer> topLevelDestinations = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());

        _drawer = _binding.drawerLayout;
        _navigationView = _binding.navView;
        _topToolbar = _binding.activityHomeBase.topAppBar;

        setSupportActionBar(_topToolbar);
        if (_actionBar != null) {
            _actionBar.setDisplayHomeAsUpEnabled(false);
            _actionBar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
            _actionBar.setHomeButtonEnabled(false);
        }

        _actionBarDrawerToggle = new ActionBarDrawerToggle(this, _drawer, _topToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        _drawer.addDrawerListener(_actionBarDrawerToggle);
        _actionBarDrawerToggle.syncState();


        // This line will now correctly reference the NavHostFragment

        // Hide or show items based on the user login status and role
//        Menu menu = _navigationView.getMenu();
//        menu.findItem(R.id.third_dummy_fragment).setVisible(false);


        //    when the drawer is opened and user clicks the back btn we want to close
        //    the drawer not to go back to main activity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(_drawer.isDrawerOpen(GravityCompat.START)){
                    _drawer.closeDrawer(GravityCompat.START);
                    return;
                }
                finish();
            }
        });


    }
    @Override
    protected void onStart() {
        super.onStart();
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        _navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            int id = navDestination.getId();
            boolean isTopLevelDestination = topLevelDestinations.contains(id);
            if (!isTopLevelDestination) {
                if(id == R.id.dummy_fragment){

                    Toast.makeText(HomeActivity.this, "Dummy 1", Toast.LENGTH_LONG).show();
                }else if(id == R.id.second_dummy_fragment){
                    Toast.makeText(HomeActivity.this,"Dummy 2",Toast.LENGTH_LONG).show();
                }
                _drawer.closeDrawers();
            }
        });

        _topAppBarConfiguration = new AppBarConfiguration.Builder(R.id.home_fragment, R.id.dummy_fragment,R.id.second_dummy_fragment).setOpenableLayout(_drawer).build();
        NavigationUI.setupWithNavController(_navigationView, _navController);
        NavigationUI.setupActionBarWithNavController(this, _navController, _topAppBarConfiguration);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.onNavDestinationSelected(item, _navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.navigateUp(_navController, _topAppBarConfiguration) || super.onSupportNavigateUp();
    }



}