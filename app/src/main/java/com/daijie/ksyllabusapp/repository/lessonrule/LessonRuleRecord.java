package com.daijie.ksyllabusapp.repository.lessonrule;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by daidaijie on 17-10-17.
 */
@Entity
public class LessonRuleRecord {

    @Id(autoincrement = true)
    public Long id;

    public String lessonRecordsJson;

    @Index
    public String account;

    @Index
    public int semesterYear;

    @Index
    public int semesterSeason;

    @Generated(hash = 690351369)
    public LessonRuleRecord(Long id, String lessonRecordsJson, String account,
            int semesterYear, int semesterSeason) {
        this.id = id;
        this.lessonRecordsJson = lessonRecordsJson;
        this.account = account;
        this.semesterYear = semesterYear;
        this.semesterSeason = semesterSeason;
    }

    @Generated(hash = 105963607)
    public LessonRuleRecord() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLessonRecordsJson() {
        return this.lessonRecordsJson;
    }

    public void setLessonRecordsJson(String lessonRecordsJson) {
        this.lessonRecordsJson = lessonRecordsJson;
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
