package com.daijie.ksyllabusapp.repository.lesson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by daidaijie on 17-9-30.
 */

@Entity
public class LessonRecord {

    @Id(autoincrement = true)
    public Long id;

    public String lessonsJson;

    @Index
    public String account;

    @Index
    public int semesterYear;

    @Index
    public int semesterSeason;


    @Generated(hash = 1076363810)
    public LessonRecord(Long id, String lessonsJson, String account,
            int semesterYear, int semesterSeason) {
        this.id = id;
        this.lessonsJson = lessonsJson;
        this.account = account;
        this.semesterYear = semesterYear;
        this.semesterSeason = semesterSeason;
    }

    @Generated(hash = 163461874)
    public LessonRecord() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonsJson() {
        return this.lessonsJson;
    }

    public void setLessonsJson(String lessonsJson) {
        this.lessonsJson = lessonsJson;
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

}
