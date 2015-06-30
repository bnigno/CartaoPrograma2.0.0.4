package pa.pm.cartaoprograma2;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListView extends BaseAdapter {
	private LayoutInflater mInflater;
	private ArrayList<ItemListView> itens;

	public AdapterListView(Context context, ArrayList<ItemListView> itens) {

		this.itens = itens;

		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return itens.size();
	}

	public ItemListView getItem(int position) {
		if (itens.isEmpty()) {
			return null;
		} else
			return itens.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View view, ViewGroup parent) {

		ItemListView item = itens.get(position);

		view = mInflater.inflate(R.layout.item_listview, null);

		((TextView) view.findViewById(R.id.textn)).setText(item.getTexto());

		return view;
	}
}