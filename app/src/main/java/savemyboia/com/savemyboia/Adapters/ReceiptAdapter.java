package savemyboia.com.savemyboia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import savemyboia.com.savemyboia.Models.Ingredient;
import savemyboia.com.savemyboia.Models.Receipt;
import savemyboia.com.savemyboia.R;

/**
 * Created by camposbrunocampos on 15/11/14.
 */
public class ReceiptAdapter extends BaseAdapter {
    private List<Receipt> receiptList;
    private Context mContext;
    public ReceiptAdapter(List<Receipt> list, Context context) {
        this.receiptList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return receiptList.size();
    }

    @Override
    public Object getItem(int position) {
        return receiptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            convertView= inflater.inflate(R.layout.receipt_list_item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Receipt receipt= receiptList.get(position);
        holder.name.setText(receipt.getName());
//        holder.howToPrepare.setChecked(receipt.getHowToPrepare());

        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView howToPrepare;
        CheckBox cb;
    }
}
