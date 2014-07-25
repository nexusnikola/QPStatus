package my_class;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikola.qpstatus.R;

public class MyPerformanceArrayAdapter extends ArrayAdapter<String> {

	private Activity context;
	private List<Map<String, String>> listOfMaps;

	public static class ViewHolder {
		public TextView text;
		public ImageView okImage;
		public ImageView printImage;
		public ImageView cardImage;
		public LinearLayout lila_row;
	}

	public MyPerformanceArrayAdapter(Activity context, int textViewResourceId,
			ArrayList<String> objects, List<Map<String, String>> listOfMaps) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.listOfMaps = listOfMaps;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_layout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.row_label);
			viewHolder.text.setTextColor(Color.WHITE);
			viewHolder.okImage = (ImageView) rowView
					.findViewById(R.id.row_icon);
			viewHolder.printImage = (ImageView) rowView.findViewById(R.id.row_print);
			viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.row_card);
			viewHolder.lila_row = (LinearLayout) rowView.findViewById(R.id.row_lila);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Map<String, String> map = listOfMaps.get(position);
		
		iconsSet(holder, map);
		
		if(position%2 == 0)
			holder.lila_row.setBackgroundColor(Color.GRAY);
		else 
			holder.lila_row.setBackgroundColor(Color.DKGRAY);
		
		return rowView;
	}

	@Override
	public void clear() {
		super.clear();
		if(listOfMaps != null)
			listOfMaps.clear();
	};
	
	public static void iconsSet(ViewHolder holder, Map<String, String> map) {
		holder.text.setText(map.get("PRICE"));
		
		if (map.get("RESPONSE_CODE").equals("FLAG00")) {
			holder.okImage.setImageResource(R.drawable.approved);
		} else {
			holder.okImage.setImageResource(R.drawable.disapproved);
		}

		if (map.get("PrinterStat").equals("PrintOK")) {
			holder.printImage.setImageResource(R.drawable.printer);
		} else {
			holder.printImage.setImageResource(R.drawable.printererr);
		}
		
		if (map.get("ISSUER_NAME").equals("VIS")) {
			holder.cardImage.setImageResource(R.drawable.pay_visa);
		} else if (map.get("ISSUER_NAME").equals("MAS")) {
			holder.cardImage.setImageResource(R.drawable.pay_master);
		} else if (map.get("ISSUER_NAME").equals("MAE")) {
			holder.cardImage.setImageResource(R.drawable.pay_maestro);
		} else if (map.get("ISSUER_NAME").equals("DIN")) {
			holder.cardImage.setImageResource(R.drawable.pay_diners);
		} else if (map.get("ISSUER_NAME").equals("AMX")) {
			holder.cardImage.setImageResource(R.drawable.pay_amex);
		} else {
			holder.cardImage.setImageResource(R.drawable.pay_card);
		}
	}

}
