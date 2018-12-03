package dig.big.com.appa.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "linksManager";

    private static final String TABLE_LINKS = "links";
    private static final String KEY_ID = "id";
    private static final String KEY_LINK = "link";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIME = "time";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LINKS_TABLE = "CREATE TABLE " + TABLE_LINKS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"+ KEY_LINK + " TEXT," + KEY_STATUS + " TEXT,"
                + KEY_TIME + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_LINKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LINKS);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void addLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LINK, link.get_link());
        values.put(KEY_STATUS, link.get_status());
        values.put(KEY_TIME, link.get_time());

        db.insert(TABLE_LINKS, null, values);
        db.close();
    }

    @Override
    public Link getLink(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LINKS, new String[] { KEY_ID, KEY_LINK,
                        KEY_STATUS, KEY_TIME, }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }


        Link link = new Link(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        db.close();
        cursor.close();
        return link;
    }

    @Override
    public List<Link> getAllLinks() {
        List<Link> contactList = new ArrayList<Link>();
        String selectQuery = "SELECT  * FROM " + TABLE_LINKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Link contact = new Link();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_link(cursor.getString(1));
                contact.set_status(cursor.getString(2));
                contact.set_time(cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());

        }
        db.close();
        cursor.close();

        return contactList;
    }

    @Override
    public int getLinksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LINKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        return cursor.getCount();
    }

    @Override
    public int updateLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LINK, link.get_link());
        values.put(KEY_STATUS, link.get_status());
        values.put(KEY_TIME, link.get_time());

        return db.update(TABLE_LINKS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(link.get_id()) });
    }

    @Override
    public void deleteLink(Link link) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LINKS, KEY_ID + " = ?", new String[] { String.valueOf(link.get_id()) });
        db.close();
    }

    @Override
    public Link findLink(String s) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LINKS, new String[] { KEY_ID, KEY_LINK,
                        KEY_STATUS, KEY_TIME, }, KEY_LINK + "=?",
                new String[] { String.valueOf(s) }, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        Link link = new Link(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));

        db.close();
        cursor.close();
        return link;
    }
}
