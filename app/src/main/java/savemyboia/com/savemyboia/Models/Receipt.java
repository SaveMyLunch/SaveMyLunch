package savemyboia.com.savemyboia.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by camposbrunocampos on 15/11/14.
 */
public class Receipt  {
    String name;
    String howToPrepare;

    int id;

    public Receipt(String name, String howToPrepare, int id) {
        this.name = name;
        this.howToPrepare = howToPrepare;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHowToPrepare() {
        return howToPrepare;
    }

    public void setHowToPrepare(String howToPrepare) {
        this.howToPrepare = howToPrepare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
