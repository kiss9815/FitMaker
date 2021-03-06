package com.juntcompany.fitmaker.Main.YoutubeThumbnail;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.juntcompany.fitmaker.Data.Project;
import com.juntcompany.fitmaker.R;
import com.juntcompany.fitmaker.StartActivity;
import com.juntcompany.fitmaker.util.OnItemClickListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectYoutubeDialogFragment extends DialogFragment {


    public static final String PROJECT_ID_MESSAGE = "project_id_message";

    public ProjectYoutubeDialogFragment() {
        // Required empty public constructor
    }

    List<Project> projects;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.MyDialog);

        Bundle args = getArguments();
        projects =  (List<Project>)args.getSerializable(PROJECT_ID_MESSAGE);


    }

    ProjectAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project_youtube_dialog, container, false);

        Button btn = (Button)view.findViewById(R.id.btn_dismiss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mAdapter = new ProjectAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Project project = mAdapter.getItem(position);

                Intent intent = new Intent(getContext(), YoutubeThumbnailActivity.class);
                intent.putExtra(YoutubeThumbnailActivity.PROJECT_MESSAGE, project);
                startActivity(intent);
                dismiss();
            }
        });
        mAdapter.addAll(projects);
        recyclerView.setAdapter(mAdapter);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

}
