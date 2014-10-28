package savemyboia.com.savemyboia;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import savemyboia.com.savemyboia.Models.Ingredient;


public class MyActivity extends Activity {


    private List<Ingredient> ingredientsList;
    private ListView myListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "5vVW7kb1IPST60o9cEykQuXQp8A7fG0nowi6z8ND", "qMg0HbAIltbGyc6ZRZuKjvhniaRFj0FwD6Gmqzh4");
        setContentView(R.layout.activity_my);
        myListView = (ListView)findViewById(R.id.list);
        loadIngredients();
    }

    private void loadIngredients(){
        ingredientsList = new ArrayList<Ingredient>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ALIMENTOS");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                for(ParseObject obj : parseObjects){
                    Ingredient ingredient = new Ingredient(obj.getString("Name"), "", "");
                    ingredientsList.add(ingredient);
                }
                if (e == null) {
                    Log.d("score", "Retrieved " + parseObjects.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                IngredientsAdapter adapter = new IngredientsAdapter();
                myListView.setAdapter(adapter);

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class IngredientsAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return ingredientsList.size();
        }

        @Override
        public Object getItem(int position) {
            return ingredientsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.ingredient_list_item, parent, false);
            TextView textView = (TextView)convertView.findViewById(R.id.name);
            Ingredient ingredient = ingredientsList.get(position);
            textView.setText(ingredient.getName());

            return convertView;
        }
    }
}
