package com.example.kos;

import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;


public class SpravkaFragment extends Fragment {
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spravka, container, false);
        try {
        SharedPreferences Current_Theme = Objects.requireNonNull(getContext()).getSharedPreferences("Current_Theme", MODE_PRIVATE);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar5);
        Drawable menuToolbar = getResources().getDrawable(R.drawable.ic_menu_24px);
        menuToolbar.setColorFilter(Current_Theme.getInt("custom_toolbar_text", ContextCompat.getColor(getContext(), R.color.custom_toolbar_text)), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationIcon(menuToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {@Override public void onClick(View view) { try{ ((MainActivity) Objects.requireNonNull(getActivity())).openDrawer(); }catch (Exception error){((MainActivity) getActivity()).errorStack(error);} }});
        toolbar.setTitleTextColor(Current_Theme.getInt("custom_toolbar_text", ContextCompat.getColor(getContext(), R.color.custom_toolbar_text)));
        toolbar.setBackgroundColor(Current_Theme.getInt("custom_toolbar", ContextCompat.getColor(getContext(), R.color.custom_toolbar)));

        TextView textViewObz = view.findViewById(R.id.obz_one);
        textViewObz.setTextColor(Current_Theme.getInt("custom_text_light", ContextCompat.getColor(getContext(), R.color.custom_text_light)));
        textViewObz = view.findViewById(R.id.obz_two);
        textViewObz.setTextColor(Current_Theme.getInt("custom_text_light", ContextCompat.getColor(getContext(), R.color.custom_text_light)));
        textViewObz = view.findViewById(R.id.obz_three);
        textViewObz.setTextColor(Current_Theme.getInt("custom_text_light", ContextCompat.getColor(getContext(), R.color.custom_text_light)));
        textViewObz = view.findViewById(R.id.obz_four);
        textViewObz.setTextColor(Current_Theme.getInt("custom_text_light", ContextCompat.getColor(getContext(), R.color.custom_text_light)));
        textViewObz = view.findViewById(R.id.button_error_act);
        textViewObz.setTextColor(Current_Theme.getInt("custom_button_act", ContextCompat.getColor(getContext(), R.color.custom_button_act)));

        LinearLayout linearLayout = view.findViewById(R.id.LinerErrors);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

            linearLayout.findViewById(R.id.LinerErrors).setBackgroundColor(Current_Theme.getInt("custom_background", ContextCompat.getColor(getContext(), R.color.custom_background)));
            ImageButton imageButtonOne = linearLayout.findViewById(R.id.imageButtonErrorLeft);
            imageButtonOne.setColorFilter(Current_Theme.getInt("custom_button_arrow", ContextCompat.getColor(getContext(), R.color.custom_button_arrow)), PorterDuff.Mode.SRC_ATOP);
            ImageButton imageButtonTwo = linearLayout.findViewById(R.id.imageButtonErrorRight);
            imageButtonTwo.setColorFilter(Current_Theme.getInt("custom_button_arrow", ContextCompat.getColor(getContext(), R.color.custom_button_arrow)), PorterDuff.Mode.SRC_ATOP);

            CardView cardView = linearLayout.findViewById(R.id.card_error);
            cardView.setCardBackgroundColor(Current_Theme.getInt("custom_card", ContextCompat.getColor(getContext(), R.color.custom_card)));
            TextView textView = linearLayout.findViewById(R.id.textError);
            textView.setTextColor(Current_Theme.getInt("custom_text_light", ContextCompat.getColor(getContext(), R.color.custom_text_light)));
            textView = linearLayout.findViewById(R.id.textViewDate);
            textView.setTextColor(Current_Theme.getInt("custom_text_dark", ContextCompat.getColor(getContext(), R.color.custom_text_dark)));

        }catch (Exception error){((MainActivity) getActivity()).errorStack(error);
        }

        return view;
    }

    public void notifyClear(){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

}