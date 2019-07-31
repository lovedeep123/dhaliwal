package com.example.wil;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_info")
public class Info {

    @PrimaryKey
    int id;
    String name;
    String number;
    String email;
    byte[] profilePic;
    byte[] coverPic;

    public Info(int id, String name, String number, String email, byte[] profilePic, byte[] coverPic) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
        this.profilePic = profilePic;
        this.coverPic = coverPic;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public byte[] getCoverPic() {
        return coverPic;
    }
}
