package savemyboia.com.savemyboia.Activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import savemyboia.com.savemyboia.Activities.ReceiptListActivity;
import savemyboia.com.savemyboia.Models.Ingredient;
import savemyboia.com.savemyboia.R;


public class MyActivity extends ActionBarActivity {


    private List<Ingredient> ingredientsList;
    private ListView myListView;
    private ArrayList<Integer> chosenIngredients;
    Button cookButton;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    IngredientsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "5vVW7kb1IPST60o9cEykQuXQp8A7fG0nowi6z8ND", "qMg0HbAIltbGyc6ZRZuKjvhniaRFj0FwD6Gmqzh4");
        setContentView(R.layout.activity_my);
        myListView = (ListView)findViewById(R.id.list);
        chosenIngredients = new ArrayList<Integer>();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ingredient currentIngredient = (Ingredient)adapterView.getItemAtPosition(i);
                CheckBox cb = (CheckBox)view.findViewById(R.id.checkbox);
                if(currentIngredient.isChecked()){
                    currentIngredient.setChecked(false);
                    int listAllIndex= ingredientsList.indexOf(currentIngredient);
                    ingredientsList.get(listAllIndex).setChecked(false);

                    int index = chosenIngredients.indexOf(currentIngredient.getId());
                    chosenIngredients.remove(index);
                }else{
                    int listAllIndex= ingredientsList.indexOf(currentIngredient);
                    ingredientsList.get(listAllIndex).setChecked(true);

                    currentIngredient.setChecked(true);
                    chosenIngredients.add(currentIngredient.getId());
                }
                cb.setChecked(!cb.isChecked());
            }
        });
        cookButton = (Button)findViewById(R.id.button);
        cookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReceiptListActivity.class);
                intent.putIntegerArrayListExtra("ingredientsList", chosenIngredients);
                startActivity(intent);

            }
        });
        loadIngredients();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.my, menu);

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchMenuItem = menu.findItem(R.id.action_search);

            searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    return true;
                }


                @Override
                public boolean onQueryTextChange(final String newText) {

//                    lastQuery = newText;
//
//                    Handler handlerQuery = new Handler();
//                    handlerQuery.postDelayed(new Runnable() {
//                        public void run() {
//                            if (lastQuery != null && lastQuery.length() > 2 && lastQuery == newText) {
//                                queryPlaylists(lastQuery);
//                            }
//                        }
//                    }, 1500);
                    int textlength = newText.length();
                    List<Ingredient> tempArrayList = new ArrayList<Ingredient>();
                    for(Ingredient ing: ingredientsList){
                        if (textlength <= ing.getName().length()) {
                            if (ing.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(ing);
                            }
                        }
                    }
                    adapter = new IngredientsAdapter(tempArrayList);
                    myListView.setAdapter(adapter);

                    return true;
                }
            });

        return super.onCreateOptionsMenu(menu);
    }


    private void loadIngredients(){
        ingredientsList = new ArrayList<Ingredient>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("ALIMENTOS");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                for(ParseObject obj : parseObjects){
                    final Ingredient ingredient = new Ingredient(obj.getString("Name"), "", "", obj.getInt("Id"));
                    ingredientsList.add(ingredient);
                    ParseFile imageFile = (ParseFile)obj.get("Image");
                    if(imageFile!=null){
                        imageFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, com.parse.ParseException e) {
                                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                ingredient.setImage(bitmap);
                                if(adapter!=null){
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }

                }
                if (e == null) {
                    Log.d("score", "Retrieved " + parseObjects.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                adapter = new IngredientsAdapter(ingredientsList);
                myListView.setAdapter(adapter);

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    private class IngredientsAdapter extends BaseAdapter{
        private List<Ingredient> ingredientsArrayList;
        private IngredientsAdapter(List<Ingredient> list) {
            this.ingredientsArrayList = list;
        }

        @Override
        public int getCount() {
            return ingredientsArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return ingredientsArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null){
                convertView= inflater.inflate(R.layout.ingredient_list_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView)convertView.findViewById(R.id.name);
                holder.cb = (CheckBox)convertView.findViewById(R.id.checkbox);
                holder.image = (ImageView)convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            Ingredient ingredient = ingredientsArrayList.get(position);
            holder.name.setText(ingredient.getName());
            holder.cb.setChecked(ingredient.isChecked());
            if(ingredient.getImage()!=null){
                holder.image.setImageBitmap(ingredient.getImage());
            }

            return convertView;
        }
        class ViewHolder{
            TextView name;
            ImageView image;
            CheckBox cb;
        }
    }
}
