package com.yumu.appinfo.floatbtn;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fablibrary.FloatingActionButton;
import com.example.fablibrary.FloatingActionMenu;
import com.example.fablibrary.SubActionButton;
import com.yumu.appinfo.R;


public class MenuWithFABActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_with_fab);

        // Set up the white button on the lower right corner
        // more or less with default parameter
        final ImageView fabIconNew = new ImageView(this);
        fabIconNew.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_new_light));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .build();

        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView rlIcon1 = new ImageView(this);
        ImageView rlIcon2 = new ImageView(this);
        ImageView rlIcon3 = new ImageView(this);
        ImageView rlIcon4 = new ImageView(this);

        rlIcon1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_chat_light));
        rlIcon2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_camera_light));
        rlIcon3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_video_light));
        rlIcon4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_place_light));

        // Build the menu with default options: light theme, 90 degrees, 72dp radius.
        // Set 4 default SubActionButtons
        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(rlIcon1).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon2).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon3).build())
                .addSubActionView(rLSubBuilder.setContentView(rlIcon4).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view


        // Set up the large red button on the center right side
        // With custom button and content sizes and margins
//        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
//        int redActionButtonMargin = getResources().getDimensionPixelOffset(R.dimen.action_button_margin);
//        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
//        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
//        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
//        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
//        int blueSubActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_content_margin);
        int redActionButtonSize = 160;
        int redActionButtonMargin = 60;
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int redActionButtonContentMargin = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_margin);
        int redActionMenuRadius = getResources().getDimensionPixelSize(R.dimen.red_action_menu_radius);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);
        int blueSubActionButtonContentMargin = 40;

        ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_important));


        //悬浮按钮的在屏幕中的位置 默认是
        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        starParams.setMargins(redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin,
                redActionButtonMargin);
//        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
//        fabIconStarParams.setMargins(redActionButtonContentSize,
//                redActionButtonContentSize,
//                redActionButtonContentSize,
//                redActionButtonContentSize);

        final FloatingActionButton leftCenterButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconStar, fabIconStarParams)
//                .setBackgroundDrawable(R.drawable.button_action_red_selector)
                .setPosition(FloatingActionButton.POSITION_RIGHT_CENTER)
                .setLayoutParams(starParams)
                .build();

        // Set up customized SubActionButtons for the right center menu
        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
//        lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(redActionButtonSize, redActionButtonSize);
//        blueContentParams.setMargins(1,
//                1,
//                1,
//                1);
//        lCSubBuilder.setLayoutParams(blueContentParams);
        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        ImageView lcIcon1 = new ImageView(this);
        ImageView lcIcon2 = new ImageView(this);
        ImageView lcIcon3 = new ImageView(this);
        ImageView lcIcon4 = new ImageView(this);
        ImageView lcIcon5 = new ImageView(this);

        lcIcon1.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_camera));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_picture));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_video));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_location_found));
        lcIcon5.setImageDrawable(getResources().getDrawable(R.mipmap.ic_action_headphones));

        // Build another menu with custom options
        final FloatingActionMenu leftCenterMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(lCSubBuilder.setTag("lcIcon1").setContentView(lcIcon1, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setTag("lcIcon2").setContentView(lcIcon2, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setTag("lcIcon3").setContentView(lcIcon3, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setTag("lcIcon4").setContentView(lcIcon4, blueContentParams).build())
                .addSubActionView(lCSubBuilder.setTag("lcIcon5").setContentView(lcIcon5, blueContentParams).build())
                .setRadius(redActionMenuRadius)
                .setStartAngle(270)
                .setEndAngle(90)
                .setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
                    @Override
                    public void onMenuOpened(FloatingActionMenu menu) {
                        Toast.makeText(getApplicationContext(), "Menu opened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMenuClosed(FloatingActionMenu menu) {
                        Toast.makeText(getApplicationContext(), "Menu Closed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onMenuClick(View childView) {
                        Toast.makeText(getApplicationContext(), "Menu click " + childView.getTag(), Toast.LENGTH_SHORT).show();
                    }
                })
                .attachTo(leftCenterButton)
                .build();


        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();

                leftCenterMenu.close(true);
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
                animation.start();
                leftCenterMenu.open(true);

            }
        });
    }
}
