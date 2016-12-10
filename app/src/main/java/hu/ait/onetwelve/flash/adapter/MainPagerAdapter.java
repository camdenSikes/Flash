package hu.ait.onetwelve.flash.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hu.ait.onetwelve.flash.fragments.MyDecksFragment;
import hu.ait.onetwelve.flash.fragments.OtherFragment;
import hu.ait.onetwelve.flash.fragments.SharedDecksFragment;
import hu.ait.onetwelve.flash.model.MainActivity;

/**
 * Created by Camden Sikes on 12/10/2016.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyDecksFragment();
            case 1:
                return new SharedDecksFragment();
            case 2:
                return new OtherFragment();
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
                return "My Decks";
            case 1:
                return "Shared Decks";
            case 2:
                return "SECTION 3";
            default:
                return "My Decks";
        }
    }
}
