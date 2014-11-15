package savemyboia.com.savemyboia.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;
import java.util.List;

import savemyboia.com.savemyboia.Models.Ingredient;
import savemyboia.com.savemyboia.Models.Receipt;
import savemyboia.com.savemyboia.R;

/**
 * Created by camposbrunocampos on 15/11/14.
 */
public class ReceiptDetailActivity extends ActionBarActivity {
    private Receipt recipe;
    private TextView mName;
    private TextView mPrepare;
    private ImageView mImage;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);
        mName = (TextView)findViewById(R.id.name);
        mPrepare= (TextView)findViewById(R.id.description);
        mImage= (ImageView)findViewById(R.id.image);
        mProgressBar= (ProgressBar)findViewById(R.id.progress);
        int receiptId = getIntent().getIntExtra("id",0);
        loadReceiptDetails(receiptId);
    }
    private void loadReceiptDetails(int recipeId){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Recipe");
        query.whereEqualTo("Id", recipeId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                ParseObject pObj= null;
                for (ParseObject obj : parseObjects) {
                    recipe = new Receipt(obj.getString("Name"), obj.getString("Preparation"),obj.getInt("Id"));
                    pObj = obj;
                }
                if (e == null) {
                    Log.d("score", "Retrieved " + parseObjects.size() + " scores");
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
                if(pObj !=null){
                    ParseFile imageFile = (ParseFile)pObj.get("Image");
                    imageFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, com.parse.ParseException e) {
                            mProgressBar.setVisibility(View.GONE);
                            Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            mImage.setImageBitmap(bitmap);
                        }
                    });
                }
                updateUIWithRecipe();
            }
        });
    }

    private void updateUIWithRecipe(){
        mName.setText(recipe.getName());
        mPrepare.setText(recipe.getHowToPrepare());
    }
}
