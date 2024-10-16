package com.towhid.sqlitecrud.databaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.towhid.sqlitecrud.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Db_NAME = "jee59";
    public static final String VERSION = "1";
    public static final String TABLE_STUDENT = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "create table " + TABLE_STUDENT + "( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," + COLUMN_EMAIL + " TEXT );";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);

    }

    public boolean insertStudent(Student student) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, student.getName());
        contentValues.put(COLUMN_EMAIL, student.getEmail());
        long status = database.insert(TABLE_STUDENT, null, contentValues);

        return status != -1;
    }


    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_STUDENT, null);



        if (cursor.moveToFirst()){

            do {

                int id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String email=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));


                Student student=new Student(id,name,email);
                studentList.add(student);
            }
            while (cursor.moveToNext());

        }


        cursor.close();
        return studentList;


    }

    public boolean updateStudent(Student student){

       SQLiteDatabase db=this.getWritableDatabase();
       ContentValues contentValues=new ContentValues();
       contentValues.put(COLUMN_NAME,student.getName());
       contentValues.put(COLUMN_EMAIL,student.getEmail());

       int result=db.update(TABLE_STUDENT,contentValues,COLUMN_ID+"=?",new String[]{String.valueOf(student.getId())});

       return result>0;

    }

    public boolean deleteStudent(int id){
        SQLiteDatabase db=this.getWritableDatabase();
       int result= db.delete(TABLE_STUDENT,COLUMN_ID+"=?",new String[]{String.valueOf(id)});
       return result>0;

    }

}
