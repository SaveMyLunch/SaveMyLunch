package savemyboia.com.savemyboia.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import savemyboia.com.savemyboia.Adapters.ReceiptAdapter;
import savemyboia.com.savemyboia.Models.Ingredient;
import savemyboia.com.savemyboia.Models.Receipt;
import savemyboia.com.savemyboia.R;

/**
 * Created by camposbrunocampos on 15/11/14.
 */
public class ReceiptListActivity extends ActionBarActivity {

    private ArrayList<Receipt> receiptList;
    private ReceiptAdapter adapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        receiptList = new ArrayList<Receipt>();
        mListView = (ListView) findViewById(R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Receipt receipt = (Receipt) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ReceiptDetailActivity.class);
                intent.putExtra("id", receipt.getId());
                startActivity(intent);
            }
        });
        loadReceipts();
    }

    private void loadReceipts(){
        ArrayList<Integer> ingredients = getIntent().getIntegerArrayListExtra("ingredientsList");
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("Recipe_Ingredients");
        for (Integer ingredientId : ingredients){
            innerQuery.whereEqualTo("Ingredient_Id", ingredientId);
        }
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Recipe");
        query2.whereMatchesKeyInQuery("Id", "Recipe_Id",innerQuery);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                for(ParseObject obj : parseObjects){
                    Receipt receipt= new Receipt(obj.getString("Name"),obj.getString("Preparation"), obj.getInt("Id"));
                    receiptList.add(receipt);
                }
                if (e == null) {
                    Log.d("score", "Retrieved " + parseObjects.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                adapter = new ReceiptAdapter(receiptList, getApplicationContext());
                mListView.setAdapter(adapter);
            }
        });
    }
}
