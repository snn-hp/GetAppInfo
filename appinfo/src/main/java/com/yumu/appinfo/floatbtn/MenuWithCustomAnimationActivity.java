package com.yumu.appinfo.floatbtn;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fablibrary.FloatingActionButton;
import com.example.fablibrary.FloatingActionMenu;
import com.example.fablibrary.SubActionButton;
import com.yumu.appinfo.R;


public class MenuWithCustomAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_with_custom_animation);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new CustomAnimationDemoFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_with_custom_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CustomAnimationDemoFragment extends Fragment {

        public CustomAnimationDemoFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_menu_with_custom_animation, container, false);

            ImageView fabContent = new ImageView(getContext());
            fabContent.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.ic_action_settings));

            FloatingActionButton darkButton = new FloatingActionButton.Builder(getContext())
                                                  .setTheme(FloatingActionButton.THEME_DARK)
                                                  .setContentView(fabContent)
                                                  .setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER)
                                                  .build();

            SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(getActivity())
                                                   .setTheme(SubActionButton.THEME_DARK);
            ImageView rlIcon1 = new ImageView(getContext());
            ImageView rlIcon2 = new ImageView(getContext());
            ImageView rlIcon3 = new ImageView(getContext());
            ImageView rlIcon4 = new ImageView(getContext());
            ImageView rlIcon5 = new ImageView(getContext());

            rlIcon1.setImageDrawable(getContext().getResources().getDrawable(R.mipmap.ic_action_chat));
            rlIcon2.setImageDrawable(getContext().getDrawable(R.mipmap.ic_action_camera));
            rlIcon3.setImageDrawable(getContext().getDrawable(R.mipmap.ic_action_video));
            rlIcon4.setImageDrawable(getContext().getDrawable(R.mipmap.ic_action_place));
            rlIcon5.setImageDrawable(getContext().getDrawable(R.mipmap.ic_action_headphones));

            // Set 4 SubActionButtons
            FloatingActionMenu centerBottomMenu = new FloatingActionMenu.Builder(getActivity())
                    .setStartAngle(0)
                    .setEndAngle(-180)
                    .setAnimationHandler(new SlideInAnimationHandler())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                    .addSubActionView(rLSubBuilder.setContentView(rlIcon5).build())
                    .attachTo(darkButton)
                    .build();

            return rootView;
        }
    }
}
