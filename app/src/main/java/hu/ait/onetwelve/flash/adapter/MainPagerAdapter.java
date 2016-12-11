package hu.ait.onetwelve.flash.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.onetwelve.flash.R;
import hu.ait.onetwelve.flash.fragments.MyDecksFragment;
import hu.ait.onetwelve.flash.fragments.SearchFragment;
import hu.ait.onetwelve.flash.fragments.SharedDecksFragment;

/**
 * Created by Camden Sikes on 12/10/2016.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyDecksFragment();
            case 1:
                return new SharedDecksFragment();
            case 2:
                return new SearchFragment();
            default:
                return new MyDecksFragment();
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.my_decks);
            case 1:
                return context.getString(R.string.shared_decks);
            case 2:
                return context.getString(R.string.search_decks);
            default:
                return context.getString(R.string.my_decks);
        }
    }
}
