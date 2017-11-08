package com.daijie.ksyllabusapp.repository.lesson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by daidaijie on 2017/10/7.
 */

@Entity
public class ShareLessonRecord {

    @Id(autoincrement = true)
    public Long id;

    private String objectId;

    @Index
    private String account;

    @Index
    public int semesterYear;

    @Index
    public int semesterSeason;

    public String lessonsJson;

    @Generated(hash = 65195986)
    public ShareLessonRecord(Long id, String objectId, String account,
            int semesterYear, int semesterSeason, String lessonsJson) {
        this.id = id;
        this.objectId = objectId;
        this.account = account;
        this.semesterYear = semesterYear;
        this.semesterSeason = semesterSeason;
        this.lessonsJson = lessonsJson;
    }

    @Generated(hash = 475315027)
    public ShareLessonRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getSemesterYear() {
        return this.semesterYear;
    }

    public void setSemesterYear(int semesterYear) {
        this.semesterYear = semesterYear;
    }

    public int getSemesterSeason() {
        return this.semesterSeason;
    }

    public void setSemesterSeason(int semesterSeason) {
        this.semesterSeason = semesterSeason;
    }

    public String getLessonsJson() {
        return this.lessonsJson;
    }

    public void setLessonsJson(String lessonsJson) {
        this.lessonsJson = lessonsJson;
    }


}
