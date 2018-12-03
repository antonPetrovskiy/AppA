package dig.big.com.appa.sql;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class Link{

    int _id;
    String _link;
    String _status;
    String _time;

    public Link(){
    }

    public Link(int id, String link, String status, String time){
        this._id = id;
        this._link = link;
        this._status = status;
        this._time = time;
    }

    public Link(String link, String status, String time){
        this._link = link;
        this._status = status;
        this._time = time;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_link() {
        return _link;
    }

    public void set_link(String _link) {
        this._link = _link;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public String get_time() {
        return _time;
    }

    public void set_time(String _time) {
        this._time = _time;
    }



    public static Comparator<Link> COMPARE_BY_STATUS = new Comparator<Link>() {
        public int compare(Link one, Link other) {
            return one.get_status().compareTo(other.get_status());
        }
    };
}
