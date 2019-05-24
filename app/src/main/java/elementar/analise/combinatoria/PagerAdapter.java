package elementar.analise.combinatoria;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import elementar.analise.combinatoria.Fragments.Arranjo;
import elementar.analise.combinatoria.Fragments.Combinacao;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Arranjo arranjo = new Arranjo();
                return arranjo;

            case 1:
                Combinacao combinacao = new Combinacao();
                return combinacao;

            case 2:
                Combinacao c1 = new Combinacao();
                return c1;

            case 3:
                Combinacao c2 = new Combinacao();
                return c2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}