package com.tassioauad.moviecheck.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tassioauad.moviecheck.MovieCheckApplication;
import com.tassioauad.moviecheck.R;
import com.tassioauad.moviecheck.dagger.MovieProfileViewModule;
import com.tassioauad.moviecheck.dagger.PersonProfileViewModule;
import com.tassioauad.moviecheck.model.entity.Movie;
import com.tassioauad.moviecheck.model.entity.Person;
import com.tassioauad.moviecheck.presenter.PersonProfilePresenter;
import com.tassioauad.moviecheck.view.PersonProfileView;
import com.tassioauad.moviecheck.view.fragment.CastCrewFragment;
import com.tassioauad.moviecheck.view.fragment.ListMediaFragment;
import com.tassioauad.moviecheck.view.fragment.ListReviewFragment;
import com.tassioauad.moviecheck.view.fragment.MovieDetailFragment;
import com.tassioauad.moviecheck.view.fragment.PersonDetailFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PersonProfileActivity extends AppCompatActivity implements PersonProfileView {

    private static final String KEY_PERSON = "PERSON";

    @Inject
    PersonProfilePresenter presenter;

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personprofile);
        ButterKnife.bind(this);
        ((MovieCheckApplication) getApplication()).getObjectGraph().plus(new PersonProfileViewModule(this)).inject(this);

        setSupportActionBar(toolbar);

        final Person person = getIntent().getParcelableExtra(KEY_PERSON);

        presenter.init(person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new PersonDetailFragment().newInstance(person);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.personprofileactivity_general);
                    default:
                        return null;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, Person person) {
        Intent intent = new Intent(context, PersonProfileActivity.class);
        intent.putExtra(KEY_PERSON, person);
        return intent;
    }

    @Override
    public void showPersonName(String title) {
        getSupportActionBar().setTitle(title);
    }
}