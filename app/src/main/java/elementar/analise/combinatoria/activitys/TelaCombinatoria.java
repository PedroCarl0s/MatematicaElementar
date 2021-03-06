package elementar.analise.combinatoria.activitys;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import elementar.analise.combinatoria.Dialog.MyAlertDialog;
import elementar.analise.combinatoria.adapter.PagerAdapter;
import elementar.matematica.pedrock.matemticaelementar.R;

public class TelaCombinatoria extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_combinatoria);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //tabLayout.addTab(tabLayout.newTab().setText("Permutacao"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewAnalise);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MainActivity.hideKeyboard(TelaCombinatoria.this);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

    }


    @Override
    public void onBackPressed() {

        MyAlertDialog myAlertDialog = new MyAlertDialog(this,"Voltar ao início","Deseja voltar para a tela inicial?","Sim","Não");
        myAlertDialog.backHome(this);
    }

}