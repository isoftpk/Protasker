package io.isoft.protasker;

import java.util.List;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {

	protected static final String LOG_TAG = TaskListAdapter.class.getSimpleName();
	
	private List<Task> items;
	private int layoutResourceId;
	private Context context;
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;

	public TaskListAdapter(Context context, int layoutResourceId, List<Task> items) {
		super(context, layoutResourceId, items);
        mSelectedItemsIds = new SparseBooleanArray();
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
        inflater = LayoutInflater.from(context);
	}

    private class ViewHolder {
        TextView todo;
    }

	@Override
	public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.atom_pay_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.todo = (TextView) view.findViewById(R.id.todo_comment);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        holder.todo.setText(items.get(position).getName());
        return view;
	}

	private void setupItem(TaskListHolder holder) {
		holder.name.setText(holder.atomPayment.getName());
//		holder.value.setText(String.valueOf(holder.atomPayment.getValue()));
	}

	public static class TaskListHolder {
		Task atomPayment;
		TextView name;
		//TextView value;
		Button removePaymentButton;
	}
	
	private void setNameTextChangeListener(final TaskListHolder holder) {
		holder.name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				holder.atomPayment.setName(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) { }
		});
	}

//	private void setValueTextListeners(final TaskListHolder holder) {
//		holder.value.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before, int count) {
//				try{
//					holder.atomPayment.setValue(Double.parseDouble(s.toString()));
//				}catch (NumberFormatException e) {
//					Log.e(LOG_TAG, "error reading double value: " + s.toString());
//				}
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
//
//			@Override
//			public void afterTextChanged(Editable s) { }
//		});
//	}

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
