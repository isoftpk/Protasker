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
import android.widget.EditText;
import android.widget.TextView;

public class TaskListAdapter extends ArrayAdapter<Task> {

	protected static final String LOG_TAG = TaskListAdapter.class.getSimpleName();
	
	private List<Task> items;
	private int layoutResourceId;
	private Context context;
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;
    public ViewHolder holder;

	public TaskListAdapter(Context context, int layoutResourceId, List<Task> items) {
		super(context, layoutResourceId, items);
        mSelectedItemsIds = new SparseBooleanArray();
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.items = items;
        inflater = LayoutInflater.from(context);
	}

    public class ViewHolder {
        EditText taskEdit;
        TextView taskView;
    }



	@Override
	public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.task_list_item, null);
            // Locate the TextViews in listview_item.xml
            holder.taskEdit = (EditText) view.findViewById(R.id.edit_task_item);
            holder.taskView = (TextView) view.findViewById(R.id.view_task_item);


//            holder.editTask.setEnabled(true);
//            holder.editTask.setClickable(true);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Capture position and set to the TextViews
        holder.taskEdit.setText(items.get(position).getName());
        holder.taskView.setText(items.get(position).getName());
        return view;
	}

	private void setupItem(TaskListHolder holder) {
		holder.name.setText(holder.atomPayment.getName());
//		holder.value.setText(String.valueOf(holder.atomPayment.getValue()));
	}

	public static class TaskListHolder {
		Task atomPayment;
		EditText name;
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
