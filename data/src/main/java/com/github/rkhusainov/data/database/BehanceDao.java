package com.github.rkhusainov.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.github.rkhusainov.domain.model.project.Cover;
import com.github.rkhusainov.domain.model.project.Owner;
import com.github.rkhusainov.domain.model.project.Project;
import com.github.rkhusainov.domain.model.user.Image;
import com.github.rkhusainov.domain.model.user.User;

import java.util.List;

@Dao
public interface BehanceDao {

    // Inserts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProjects(List<Project> projects);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCovers(List<Cover> covers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOwners(List<Owner> owners);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertImage(Image image);


    //Gets
    @Query("select * from project order by published_on desc")
    List<Project> getProjects();

    @Query("select * from cover where project_id = :projectId")
    Cover getCoverFromProject(int projectId);

    @Query("select * from owner where project_id = :projectId")
    List<Owner> getOwnersFromProject(int projectId);

    @Query("select * from user where username = :userName")
    User getUserByName(String userName);

    @Query("select * from image where user_id = :userId")
    Image getImageFromUser(int userId);

    @Query("select * from user")
    List<User> getUsers();

    @Query("select * from image")
    List<Image> getImages();

    @Query("delete from owner")
    void clearOwnerTable();

    @Query("delete from cover")
    void clearCoverTable();
}
