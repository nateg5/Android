package com.bandit.bandit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         * fragments for each of the sections. We use a
         * {@link FragmentPagerAdapter} derivative, which will keep every
         * loaded fragment in memory. If this becomes too memory intensive, it
         * may be best to switch to a
         * {@link android.support.v4.app.FragmentStatePagerAdapter}.
         */
        SectionsPagerAdapter mSectionsPagerAdapter;

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int i) {
                navigationView.getMenu().findItem(list.get(i).getNav()).setChecked(true);
                setTitle(getString(list.get(i).getString()));
                editor.putInt("currentItem", i);
                editor.apply();
            }
        });

        setTitle(getString(list.get(0).getString()));

        mViewPager.setCurrentItem(sharedPreferences.getInt("currentItem", 0));

        navigationView.getMenu().findItem(list.get(mViewPager.getCurrentItem()).getNav()).setChecked(true);
    }

    @SuppressWarnings("unused")
    public void goToBasicSetup(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_basic));
    }

    @SuppressWarnings("unused")
    public void goToWrestler(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_wrestler));
    }

    @SuppressWarnings("unused")
    public void goToTower(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_tower));
    }

    @SuppressWarnings("unused")
    public void goToFlanked(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_flanked));
    }

    @SuppressWarnings("unused")
    public void goToCrowd(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_crowd));
    }

    @SuppressWarnings("unused")
    public void goToSpider(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_spider));
    }

    @SuppressWarnings("unused")
    public void goToCcuff(View view) {
        NavigationView navigationView = findViewById(R.id.nav_view);

        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_ccuff));
    }

    @SuppressWarnings("unused")
    public void openBandItBlog(View view) {
        String url = "http://banditguide.blogspot.com";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private static class Content {

        final int layout;
        final int string;
        final int nav;

        Content(int layout, int string, int nav) {
            this.layout = layout;
            this.string = string;
            this.nav = nav;
        }

        int getLayout() {
            return layout;
        }

        int getString() {
            return string;
        }

        int getNav() {
            return nav;
        }
    }

    private static final List<Content> list = new ArrayList<Content>() {{
        add(new Content(R.layout.content_main, R.string.content_cover, R.id.nav_cover));
        add(new Content(R.layout.content_copyright, R.string.content_copyright, R.id.nav_copyright));
        add(new Content(R.layout.content_letter, R.string.content_letter, R.id.nav_letter));
        add(new Content(R.layout.content_safety, R.string.content_safety, R.id.nav_safety));
        add(new Content(R.layout.content_basic, R.string.content_basic, R.id.nav_basic));
        add(new Content(R.layout.content_bridge, R.string.content_bridge, R.id.nav_bridge));
        add(new Content(R.layout.content_xmarks, R.string.content_xmarks, R.id.nav_xmarks));
        add(new Content(R.layout.content_wrestler, R.string.content_wrestler, R.id.nav_wrestler));
        add(new Content(R.layout.content_trapped, R.string.content_trapped, R.id.nav_trapped));
        add(new Content(R.layout.content_twisted, R.string.content_twisted, R.id.nav_twisted));
        add(new Content(R.layout.content_ring, R.string.content_ring, R.id.nav_ring));
        add(new Content(R.layout.content_bowtie, R.string.content_bowtie, R.id.nav_bowtie));
        add(new Content(R.layout.content_tower, R.string.content_tower, R.id.nav_tower));
        add(new Content(R.layout.content_thew, R.string.content_thew, R.id.nav_thew));
        add(new Content(R.layout.content_flanked, R.string.content_flanked, R.id.nav_flanked));
        add(new Content(R.layout.content_closing, R.string.content_closing, R.id.nav_closing));
        add(new Content(R.layout.content_two, R.string.content_two, R.id.nav_two));
        add(new Content(R.layout.content_crowd, R.string.content_crowd, R.id.nav_crowd));
        add(new Content(R.layout.content_crowdtwist, R.string.content_crowdtwist, R.id.nav_crowdtwist));
        add(new Content(R.layout.content_spider, R.string.content_spider, R.id.nav_spider));
        add(new Content(R.layout.content_webbed, R.string.content_webbed, R.id.nav_webbed));
        add(new Content(R.layout.content_ccuff, R.string.content_ccuff, R.id.nav_ccuff));
        add(new Content(R.layout.content_tcuff, R.string.content_tcuff, R.id.nav_tcuff));
        add(new Content(R.layout.content_butterfly, R.string.content_butterfly, R.id.nav_butterfly));
    }};

    private Content findContentFromNav(int nav) {
        for(Content content : list) {
            if(content.getNav() == nav) {
                return content;
            }
        }

        return null;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Content content = findContentFromNav(id);
        if(content != null) {
            mViewPager.setCurrentItem(list.indexOf(content));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressWarnings("WeakerAccess")
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int sectionNumber = 0;
            if(getArguments() != null && getArguments().get(ARG_SECTION_NUMBER) != null) {
                sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            }
            return inflater.inflate(list.get(sectionNumber).getLayout(), container, false);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
