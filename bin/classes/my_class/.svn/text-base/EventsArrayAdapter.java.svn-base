package my_class;

import java.util.List;
import java.util.Map;

import com.nikola.qpstatus.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsArrayAdapter extends ArrayAdapter<String> {

	private Activity context;
	private List<Map<String, String>> listOfMaps;	
	private static String[] highRisk = { "UNAUTH", "MDO", "AUTH", "PDO", "PRNerrRSP" };
	private static String[] standardRisk = { "STU", "RBT", "CLS" };
	
	public EventsArrayAdapter(Activity context, int resource, List<String> objects, 
			List<Map<String, String>> listOfMaps) {
		super(context, resource, objects);
		this.listOfMaps = listOfMaps;
		this.context = context;
	}

	public static class ViewHolder {
		public TextView text;
		public ImageView okImage;
		public LinearLayout lila_row;
	}
	
	@Override
	public void clear() {
		super.clear();
		listOfMaps.clear();
	};
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.events_row_layout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.row_label);
			viewHolder.text.setTextColor(Color.WHITE);
			viewHolder.okImage = (ImageView) rowView
					.findViewById(R.id.row_icon);
			viewHolder.lila_row = (LinearLayout) rowView.findViewById(R.id.row_lila);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		Map<String, String> map = listOfMaps.get(position);
		
		setHeader(holder, map);
		
		if(position%2 == 0)
			holder.lila_row.setBackgroundColor(Color.GRAY);
		else 
			holder.lila_row.setBackgroundColor(Color.DKGRAY);
		
		return rowView;
	}

	public static void setHeader(ViewHolder holder, Map<String, String> map) {
		holder.text.setText(map.get("MESSAGE"));
		
		if(isHighRisk(map.get("FLAG")))
			holder.okImage.setImageResource(R.drawable.disapproved);
		else if(isOkRisk(map.get("FLAG")))
			holder.okImage.setImageResource(R.drawable.approved);
		else
			holder.okImage.setImageResource(R.drawable.minus);
	}
	
	public static boolean isHighRisk(String type){
		for (int i = 0; i < highRisk.length; i++) {
			if(highRisk[i].equals(type))
				return true;
		}
		return false;
	}
	
	public static boolean isOkRisk(String type){
		for (int i = 0; i < standardRisk.length; i++) {
			if(standardRisk[i].equals(type))
				return true;
		}
		return false;
	}
}
