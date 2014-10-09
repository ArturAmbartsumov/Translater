package com.example.artur.translater;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstFragment extends Fragment implements View.OnClickListener {

    private static final String LOG_TAG = FirstFragment.class.getName();
    public static final String LANG_NOT_CHOOSE = "langNotChoose";


    // TODO: Rename parameter arguments, choose names that match
    /*// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;*/
    private  String mOriginText;
    private  String mLanguage1 = LANG_NOT_CHOOSE;
    private  String mLanguage2 = LANG_NOT_CHOOSE;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    public FirstFragment() {
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
        View v = inflater.inflate(R.layout.fragment_first, container, false);

        Button language1Button = (Button) v.findViewById(R.id.language1Button);
        Button language2Button = (Button) v.findViewById(R.id.language2Button);

        language1Button.setOnClickListener(this);
        language2Button.setOnClickListener(this);

        if (mLanguage1.equals(LANG_NOT_CHOOSE))
            language1Button.setText("Язык");
        else
            language1Button.setText(mLanguage1);
        if (mLanguage2.equals(LANG_NOT_CHOOSE))
            language2Button.setText("Язык");
        else
            language2Button.setText(mLanguage2);



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

    public String getOriginText() {
        View v = getView();
        if (v != null) {
            EditText originTextWidget = (EditText) v.findViewById(R.id.originText);
            mOriginText = originTextWidget.getText().toString();
            return mOriginText;
        }
        return "Введите текст";
    }

    public String getLanguage1() {

        return mLanguage1;
    }

    public String getLanguage2() {

        return mLanguage2;
    }

    public void setLanguage(String language, int buttonIndex) {
        View v = getView();
        if (v != null) {
            Button languageButtom = null;
            switch (buttonIndex) {
                case R.id.language1Button:
                    languageButtom = (Button) v.findViewById(R.id.language1Button);
                    mLanguage1 = language;
                    break;
                case R.id.language2Button:
                    mLanguage2 = language;
                    languageButtom = (Button) v.findViewById(R.id.language2Button);
                    break;
            }
            if (languageButtom != null)
                languageButtom.setText(language);
        }
    }

    @Override
    public void onClick(View view) {
        if (mListener == null) {
            mListener = (OnFragmentInteractionListener) getActivity();
        }
        mListener.OnShowLanguageList(view.getId());
    }

    public interface OnFragmentInteractionListener {
        public void OnShowLanguageList(int buttonId);
    }
}
