package info.nightscout.androidaps.plugins.TreatmentsFromHistory;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import info.nightscout.androidaps.R;
import info.nightscout.androidaps.plugins.Treatments.TreatmentsPlugin;
import info.nightscout.androidaps.plugins.Treatments.fragments.TreatmentsBolusFragment;
import info.nightscout.androidaps.plugins.Treatments.fragments.TreatmentsTempBasalsFragment;

public class TreatmentsFromHistoryFragment extends Fragment {
    private static Logger log = LoggerFactory.getLogger(TreatmentsFromHistoryFragment.class);

    private static TreatmentsFromHistoryPlugin treatmentsPlugin = new TreatmentsFromHistoryPlugin();

    public static TreatmentsFromHistoryPlugin getPlugin() {
        return treatmentsPlugin;
    }

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    Context context;

    Fragment bolusFragment;
    Fragment tempBasalsFragment;

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position){
                case 0:
                    return bolusFragment;
                case 1:
                    return tempBasalsFragment;
/*
                case 2:
                    return iobcobActiveFragmentObject;
                case 3:
                    return basalvsTempBasalObject;
*/
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 1 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.bolus);
                case 1:
                    return getString(R.string.tempbasals);
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.treatments_fragment, container, false);

        bolusFragment = new TreatmentsBolusFragment();
        tempBasalsFragment = new TreatmentsTempBasalsFragment();

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.treatments_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        context = getContext();

        return view;
    }
}
