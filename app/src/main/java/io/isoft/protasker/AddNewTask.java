package io.isoft.protasker;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.MultiChoiceModeListener;

public class AddNewTask extends ActionBarActivity  {

    ListView list;
	TaskListAdapter listviewadapter;
    List<Task> lstTodo = new ArrayList<Task>();
    String[] comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        comment = new String[] { "Task 1", "Task 2", "Task 3",
                "Task 4", "Task 5", "Task 6", "Task 7", "Task 8",
                "Task 9", "Task 10" };

        for (int i = 0; i < comment.length; i++) {
            Task todo = new Task(comment[i],i);
            lstTodo.add(todo);
        }

        setupListViewAdapter();
		

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.menu_action_new:
                listviewadapter.insert(new Task("New Task", 0), 0);
                return true;

            default:

            return super.onOptionsItemSelected(item);
        }
    }

    public void removeTaskOnClickHandler(View v) {
		Task itemToRemove = (Task)v.getTag();
		listviewadapter.remove(itemToRemove);
	}

	private void setupListViewAdapter() {

        // Locate the ListView in listview_main.xml
        list = (ListView)findViewById(R.id.EnterTask_TaskList);

        // Pass results to TaskListAdapter Class
		listviewadapter = new TaskListAdapter(AddNewTask.this, R.layout.task_list_item, lstTodo);

        // Binds the Adapter to the ListView
		list.setAdapter(listviewadapter);
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click
        list.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                System.out.println("onItemCheckedStateChanged");
                // Capture total checked items
                final int checkedCount = list.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Items");
                // Calls toggleSelection method from ListViewAdapter Class
                listviewadapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                System.out.println("onActionItemClicked");
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = listviewadapter.getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Task selecteditem = listviewadapter.getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                listviewadapter.remove(selecteditem);
                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);



                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listviewadapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                System.out.println("Task Clicked!");
            }
        });

	}

}
