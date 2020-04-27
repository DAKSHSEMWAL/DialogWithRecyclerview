package com.daksh.dialogwithrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;

import com.daksh.dialogwithrecyclerview.databinding.ActivityMainBinding;
import com.daksh.dialogwithrecyclerview.databinding.DialogSelectPropertyBinding;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding mainBinding;
    private DialogSelectPropertyBinding binding;
    private Dialog dial;
    private PropertiesAdapter propertiesAdapter;
    private ArrayList<PropertiesData> modelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mainBinding.button) {
            showPropertiesdialog();
        }
    }

    private void showPropertiesdialog() {

        dial = new Dialog(this, R.style.MyAlertDialogStyle);
        dial.requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.inflate(LayoutInflater.from(dial.getContext()), R.layout.dialog_select_property, null, false);
        dial.setContentView(binding.getRoot());
        setEmptyAdapter();
        getProperties();
        binding.closeDialogImg.setOnClickListener(v -> revealShowProperties(binding.getRoot(), false, dial));

        dial.setOnShowListener(dialogInterface -> revealShowProperties(binding.getRoot(), true, null));

        dial.setOnKeyListener((dialogInterface, i, keyEvent) -> {
            if (i == KeyEvent.KEYCODE_BACK) {

                revealShowProperties(binding.getRoot(), false, dial);
                return true;
            }

            return false;
        });


        Objects.requireNonNull(dial.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dial.show();
    }
    //Not Mandatory
    private void revealShowProperties(@NotNull View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (mainBinding.button.getX() + (mainBinding.button.getWidth() / 2));
        int cy = (int) (mainBinding.button.getY()) + mainBinding.button.getHeight() + 56;


        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(500);
            revealAnimator.start();

        } else {

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(500);
            anim.start();
        }

    }

    private void getProperties() {
        //Dummy Data
        modelList.clear();
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));
        modelList.add(new PropertiesData(getString(R.string.northroad),"https://images.pexels.com/photos/443383/pexels-photo-443383.jpeg?cs=srgb&dl=architectural-design-architecture-building-business-443383.jpg&fm=jpg"));

        setAdapter();

    }

    private void setEmptyAdapter() {

        propertiesAdapter = new PropertiesAdapter(this, modelList);

        binding.listproperties.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.listproperties.setLayoutManager(layoutManager);


        binding.listproperties.setAdapter(propertiesAdapter);


        propertiesAdapter.SetOnItemClickListener((view, position, model) -> {

            revealShowProperties(binding.getRoot(), false, dial);

        });

        setAdapter();


    }

    private void setVisibilities(int noData, int recyclerView) {
        /*binding.contentStocks.emptyview.setVisibility(noInternet);*/
        binding.nodata.setVisibility(noData);
        binding.listproperties.setVisibility(recyclerView);
    }

    private void setAdapter() {
        if (modelList.size() > 0) {
            setVisibilities(View.GONE, View.VISIBLE);
            propertiesAdapter.notifyDataSetChanged();
        } else {
            setVisibilities(View.VISIBLE, View.GONE);
        }
    }
}
