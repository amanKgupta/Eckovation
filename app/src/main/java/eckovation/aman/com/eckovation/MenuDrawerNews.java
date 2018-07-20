package eckovation.aman.com.eckovation;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuDrawerNews extends AppCompatActivity {
    private ActionBar actionBar;
    private Toolbar toolbar;
    private View parent_view;
    private View back_drop;
    private boolean rotate = false;
    private View lyt_mic;
    private View lyt_call;
    private FloatingActionButton fab_call,fab_add;
    private GridView gridView;
    ImageAdapter imageAdapter;
    ArrayList<ImageBitmap>bitmapList;
    private static String DATA_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=9f54fd83bd0e18bb5aad38880efb9224&per_page=50&user_id=52540720@N02&format=json&nojsoncallback=1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer_news);

        parent_view = findViewById(android.R.id.content);
        back_drop = findViewById(R.id.back_drop);

        bitmapList =new ArrayList<>();
           gridView =(GridView)findViewById(R.id.gvPhotos);
           imageAdapter= new ImageAdapter(this,bitmapList);
            gridView.setAdapter(imageAdapter);
         fab_call = (FloatingActionButton) findViewById(R.id.fab_call);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
        lyt_call = findViewById(R.id.lyt_call);
        ViewAnimation.initShowOut(lyt_call);
        back_drop.setVisibility(View.GONE);

        gridView =(GridView) findViewById(R.id.gvPhotos);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });

        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(fab_add);
            }
        });

        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Call clicked", Toast.LENGTH_SHORT).show();
            }
        });

        initToolbar();
        initNavigationMenu();
    }
    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle(R.string.app_name);
        setSendMessage();
    }
    public void setSendMessage(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .validateEagerly(true)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        apiInterface.savePost().enqueue(new Callback<GetData>() {
            @Override
            public void onResponse(Call<GetData> call, Response<GetData> response) {
                GetData photos = response.body();

                GetData.Photos photos1 = photos.photos;
                List<GetData.Photos.PhotoList> photoLists =photos1.lists;
                for (int i=0;i<20;i++){
                    String uid = photoLists.get(i).id;
                    String farm = photoLists.get(i).farm;
                    String server =photoLists.get(i).server;
                    String seccret =photoLists.get(i).secret;
                    final String imageurl ="http://farm"+farm+".staticflickr.com/"+server+"/"+uid+"_"+seccret+".jpg";
                    ImageBitmap imageBitmap =new ImageBitmap();
                    imageBitmap.bitmap =imageurl;
                    bitmapList.add(imageBitmap);
                    imageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetData> call, Throwable t) {

            }
        });
    }
    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Toast.makeText(getApplicationContext(), item.getTitle() + " Selected", Toast.LENGTH_SHORT).show();
                actionBar.setTitle(item.getTitle());
                drawer.closeDrawers();
                return true;
            }
        });

        // open drawer at start

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt_call);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt_call);
            back_drop.setVisibility(View.GONE);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();

        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
