package com.juntcompany.fitmaker.play;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juntcompany.fitmaker.Data.Content;
import com.juntcompany.fitmaker.Data.Image;
import com.juntcompany.fitmaker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayStatusPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayStatusPagerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONTENTS = "contents";
    private static final String CURRENT_COUNT = "current_count";

    // TODO: Rename and change types of parameters
    private Content mContent;
 //   private String mParam2;


    public PlayStatusPagerFragment() {
        // Required empty public constructor
    }

    public void setSelected(boolean isSelected) {
        if (getActivity() != null) {
            if(isSelected == true){
                mContent.isSelectable = true;
                imageStatus.setImageResource(Image.imageRedButtonPlay[mContent.content_status -1]);
            }
        }
    }

    public void setFinished(boolean isFinished) {
        if (getActivity() != null) {
            if(isFinished == true){
                mContent.isFinished = true;
                imageStatus.setImageResource(Image.imagePuppleButtonPlay[mContent.content_status-1]);
            }
        }
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
  //   * @param contents Parameter 1.
  //   * @param param2 Parameter 2.
     * @return A new instance of fragment PlayStatusPagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayStatusPagerFragment newInstance(Content content) {
        PlayStatusPagerFragment fragment = new PlayStatusPagerFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTENTS, content);
     //   args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = (Content)getArguments().getSerializable(CONTENTS);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    ImageView imageStatus;
    TextView textContentName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_status_pager, container, false);
         imageStatus = (ImageView)view.findViewById(R.id.image_status);
         textContentName = (TextView)view.findViewById(R.id.text_content_name);

        if(mContent.isSelectable){
            imageStatus.setImageResource(Image.imageRedButtonPlay[mContent.content_status -1]);
        }else if(mContent.isFinished){
            imageStatus.setImageResource(Image.imagePuppleButtonPlay[mContent.content_status-1]);
        }else {
            imageStatus.setImageResource(Image.imageWhiteButtonPlay[mContent.content_status - 1]);
        }
        textContentName.setText(mContent.contentName);


        return view;
    }

}
