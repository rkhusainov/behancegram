package com.github.rkhusainov.behancegram.data.model.project;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FullProject {

    @Embedded
    public Project mProject;

    @Relation(entity = Owner.class, entityColumn = "project_id", parentColumn = "id")
    public List<Owner> mOwners;

}
