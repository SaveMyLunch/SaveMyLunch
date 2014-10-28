package savemyboia.com.savemyboia.Models;

/**
 * Created by camposbrunocampos on 27/10/14.
 */
public class Ingredient {

    String name;
    String calories;
    String type;

    public Ingredient(String name, String calories, String type) {
        this.name = name;
        this.calories = calories;
        this.type = type;
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

}
