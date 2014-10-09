package com.example.artur.translater;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SecondFragment extends Fragment implements View.OnClickListener{

    private static final String LOG_TAG = SecondFragment.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    /*// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/
    private String mTranslatedText = "";

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    public SecondFragment() {
        // Required empty public constructor
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        Button translateButton = (Button) v.findViewById(R.id.translateButton);
        translateButton.setOnClickListener(this);

        setTranslatedText(mTranslatedText);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setTranslatedText(String translatedText) {
        View v = getView();
        mTranslatedText = translatedText;
        if (v != null) {
            TextView translatedTextWidget = (TextView) v.findViewById(R.id.translatedTextView);
            translatedTextWidget.setText(translatedText);
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            mListener = (OnFragmentInteractionListener) getActivity();
        }
        mListener.onTranslateButtonClick();
    }

    public interface OnFragmentInteractionListener {
        public void onTranslateButtonClick();
    }
}
