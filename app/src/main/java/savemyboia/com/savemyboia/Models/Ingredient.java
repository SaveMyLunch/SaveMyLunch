package savemyboia.com.savemyboia.Models;

import android.graphics.Bitmap;

/**
 * Created by camposbrunocampos on 27/10/14.
 */
public class Ingredient {

    String name;
    String calories;
    String type;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    Bitmap image;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    boolean isChecked;

    int id;

    public Ingredient(String name, String calories, String type, int id) {
        this.name = name;
        this.calories = calories;
        this.type = type;
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
