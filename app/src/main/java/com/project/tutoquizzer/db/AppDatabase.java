package com.project.tutoquizzer.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.project.tutoquizzer.AppValues;
import com.project.tutoquizzer.dao.*;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.entities.Scoreboard;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.typeconverters.Converter;

@Database(entities = {Courses.class, Quarters.class, SchoolYear.class, Topics.class, Scoreboard.class}, version = 1, exportSchema = true) // if more than 1, {.class, .class, .class}
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract CourseDao courseDao();
    public abstract QuarterDao quarterDao();
    public abstract SchoolYearDao schoolYearDao();
    public abstract TopicDao topicDao();
    public abstract ScoreboardDao scoreboardDao();

    public static synchronized AppDatabase getInstance(Context ctx){
        if (instance == null){
            instance = Room.databaseBuilder(ctx.getApplicationContext(),
                    AppDatabase.class,
                    "TutoQuizzerDb").
                    fallbackToDestructiveMigration().
                    addCallback(roomCallback).
                    build();

        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsync(instance).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{
        private SchoolYearDao schoolYearDao;
        private QuarterDao quarterDao;

        public PopulateDbAsync(AppDatabase db) {
            this.schoolYearDao = db.schoolYearDao();
            this.quarterDao = db.quarterDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            this.schoolYearDao.insert( new SchoolYear(1, 1) );
            this.schoolYearDao.insert( new SchoolYear(1, 2) );
            this.schoolYearDao.insert( new SchoolYear(1, 3) );
            this.schoolYearDao.insert( new SchoolYear(1, 4) );

            this.schoolYearDao.insert( new SchoolYear(2, 1) );
            this.schoolYearDao.insert( new SchoolYear(2, 2) );
            this.schoolYearDao.insert( new SchoolYear(2, 3) );
            this.schoolYearDao.insert( new SchoolYear(2, 4) );

            this.schoolYearDao.insert( new SchoolYear(3, 1) );
            this.schoolYearDao.insert( new SchoolYear(3, 2) );
            this.schoolYearDao.insert( new SchoolYear(3, 3) );
            this.schoolYearDao.insert( new SchoolYear(3, 4) );

            this.schoolYearDao.insert( new SchoolYear(4, 1) );
            this.schoolYearDao.insert( new SchoolYear(4, 2) );
            this.schoolYearDao.insert( new SchoolYear(4, 3) );
            this.schoolYearDao.insert( new SchoolYear(4, 4) );

            this.schoolYearDao.insert( new SchoolYear(5, 1) );
            this.schoolYearDao.insert( new SchoolYear(5, 2) );
            this.schoolYearDao.insert( new SchoolYear(5, 3) );
            this.schoolYearDao.insert( new SchoolYear(5, 4) );

            this.quarterDao.insert( new Quarters("Prelims") );
            this.quarterDao.insert( new Quarters("Midterms") );
            this.quarterDao.insert( new Quarters("Finals") );

            return null;
        }
    }

}
