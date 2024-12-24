package com.example.fusmobilni.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import com.example.fusmobilni.clients.ClientUtils;
import com.example.fusmobilni.core.CustomSharedPrefs;
import com.example.fusmobilni.databinding.ActivityHomeBinding;
import com.example.fusmobilni.model.enums.UserType;
import com.example.fusmobilni.responses.auth.LoginResponse;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.OnBackPressedCallback;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding _binding;
    private AppBarConfiguration _topAppBarConfiguration;
    private DrawerLayout _drawer;
    private NavigationView _navigationView;
    private NavController _navController;
    private Toolbar _topToolbar;
    private ActionBar _actionBar;
    private ActionBarDrawerToggle _actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        ClientUtils.initalize(CustomSharedPrefs.getInstance(getApplicationContext()));

        _binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(_binding.getRoot());
        _drawer = _binding.drawerLayout;
        _navigationView = _binding.navView;
        _topToolbar = _binding.activityHomeBase.topAppBar;

        // get logged in user
        CustomSharedPrefs sharedPrefs = CustomSharedPrefs.getInstance();
        LoginResponse user = sharedPrefs.getUser();

        setupDrawerMenu(user != null && user.getRole() != null ? user.getRole() : UserType.UNAUTHENTICATED_USER);
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

        //    when the drawer is opened and user clicks the back btn we want to close
        //    the drawer not to go back to main activity
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(_drawer.isDrawerOpen(GravityCompat.START)){
                    _drawer.closeDrawer(GravityCompat.START);
                    return;
                }
                // If the NavController has fragments in the back stack, pop the back stack
                if (Objects.requireNonNull(_navController.getCurrentDestination()).getId() != R.id.home_fragment) {
                    _navController.popBackStack();
                    return;
                }
                finish();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        CustomSharedPrefs sharedPrefs = CustomSharedPrefs.getInstance();
        LoginResponse user = sharedPrefs.getUser();
        setupDrawerMenu(user != null && user.getRole() != null ? user.getRole() : UserType.UNAUTHENTICATED_USER);
    }

    @Override
    protected void onStart() {
        super.onStart();
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        _navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            int id = navDestination.getId();
            _drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            if (id == R.id.home_fragment) {
                // Show drawer toggle only on the home fragment
                _actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                _drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            } else {
                // Show back button on other fragments
                _actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                _drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                // Set a click listener on the navigation icon to handle the back action
                _actionBarDrawerToggle.setToolbarNavigationClickListener(v -> {
                    // Trigger the NavController's back stack
                    if (_navController.getCurrentBackStackEntry() != null) {
                        _navController.popBackStack();
                    }
                });
            }
        });
        // add a list of top level fragments - on which you want hamburger menu
        _topAppBarConfiguration = new AppBarConfiguration.Builder(R.id.home_fragment).setOpenableLayout(_drawer).build();
        NavigationUI.setupWithNavController(_navigationView, _navController);
        NavigationUI.setupActionBarWithNavController(this, _navController, _topAppBarConfiguration);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem profileItem = menu.findItem(R.id.login_activity);
        CustomSharedPrefs sharedPrefs = CustomSharedPrefs.getInstance();
        LoginResponse user = sharedPrefs.getUser();

        if (user != null) {
            // If the user is logged in, load their profile image
//            TODO
//            if (user.getAvatar() != null) {
//                File imgFile = new File(user.getAvatar());
//                if (imgFile.exists()) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                    Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//                    profileItem.setIcon(drawable); // Set the user's profile image
//                }
//            }
        } else {
            // If not logged in, use a default icon
            profileItem.setIcon(R.drawable.ic_person_white);
        }

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        int id = item.getItemId();

        if (id == R.id.login_activity) {// Retrieve the login status from SharedPreferences
            CustomSharedPrefs sharedPrefs = CustomSharedPrefs.getInstance();
            LoginResponse user = sharedPrefs.getUser();
            if (user != null) {
                // If logged in, navigate to the profile fragment
                _navController.navigate(R.id.viewProfileFragment);
            } else {
                // If not logged in, navigate to the login page
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
            }
            return true;
        }
        return NavigationUI.onNavDestinationSelected(item, _navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        _navController = Navigation.findNavController(this, R.id.fragment_nav_content_main);
        return NavigationUI.navigateUp(_navController, _topAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void setupDrawerMenu(UserType userRole) {
        Menu menu = _navigationView.getMenu();
        menu.clear();
        switch (userRole){
            case ADMIN:
                _navigationView.inflateMenu(R.menu.nav_menu_admin);
                break;
            case EVENT_ORGANIZER:
                _navigationView.inflateMenu(R.menu.nav_menu_event_organizer);
                break;
            case SERVICE_PROVIDER:
                _navigationView.inflateMenu(R.menu.nav_menu_service_provider);
                break;
            case AUTHENTICATED_USER:
                _navigationView.inflateMenu(R.menu.nav_menu_auth_user);
                break;
            default:
                _navigationView.inflateMenu(R.menu.nav_menu_unauth_user);
        }
    }
}