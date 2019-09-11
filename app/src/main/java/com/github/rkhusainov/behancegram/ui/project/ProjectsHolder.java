package com.github.rkhusainov.behancegram.ui.project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.rkhusainov.behancegram.R;
import com.github.rkhusainov.behancegram.data.model.project.Project;
import com.github.rkhusainov.behancegram.utils.DateUtils;
import com.squareup.picasso.Picasso;

public class ProjectsHolder extends RecyclerView.ViewHolder {

    private static final int FIRST_OWNER_INDEX = 0;

    private ImageView mImage;
    private TextView mName;
    private TextView mUsername;
    private TextView mPublishedOn;

    public ProjectsHolder(@NonNull View itemView) {
        super(itemView);

        mImage = itemView.findViewById(R.id.image);
        mName = itemView.findViewById(R.id.tv_name);
        mUsername = itemView.findViewById(R.id.tv_username);
        mPublishedOn = itemView.findViewById(R.id.tv_published);
    }

    public void bind(Project project, ProjectsAdapter.OnItemClickListener onItemClickListener) {
        Picasso.get().load(project.getCover().getPhotoUrl())
                .fit()
                .into(mImage);

        mName.setText(project.getName());
        mUsername.setText(project.getOwners().get(FIRST_OWNER_INDEX).getUsername());
        mPublishedOn.setText(DateUtils.format(project.getPublishedOn()));

        if (onItemClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(project.getOwners()
                            .get(FIRST_OWNER_INDEX)
                            .getUsername());
                }
            });
        }
    }
}
