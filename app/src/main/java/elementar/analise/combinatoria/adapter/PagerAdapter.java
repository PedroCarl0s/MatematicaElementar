package elementar.analise.combinatoria.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import elementar.analise.combinatoria.fragments.Anagrama;
import elementar.analise.combinatoria.fragments.Arranjo;
import elementar.analise.combinatoria.fragments.Combinacao;
import elementar.analise.combinatoria.fragments.Permutacao;

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
                return new Arranjo();

            case 1:
                return new Combinacao();

            case 2:
                return new Permutacao();

            case 3:
                return new Anagrama();

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return this.numberOfTabs;
    }
}