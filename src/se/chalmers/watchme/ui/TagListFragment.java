package se.chalmers.watchme.ui;

import se.chalmers.watchme.R;
import se.chalmers.watchme.activity.MainActivity;
import se.chalmers.watchme.activity.TagMovieListActivity;
import se.chalmers.watchme.database.DatabaseAdapter;
import se.chalmers.watchme.database.ICursorHelper;
import se.chalmers.watchme.database.TagsTable;
import se.chalmers.watchme.database.GenericCursorLoader;
import se.chalmers.watchme.database.WatchMeContentProvider;
import se.chalmers.watchme.model.Tag;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;

public class TagListFragment extends ContentListFragment {
	
	private DatabaseAdapter db;
	
	public TagListFragment() {
		super(WatchMeContentProvider.CONTENT_URI_TAGS);
	}
	
	@Override
	public void onActivityCreated(Bundle b) {
		super.onActivityCreated(b);
		
		setUpAdapter();
		
		// Set up listener to delete a Tag.
		this.getListView().setOnItemLongClickListener(new OnDeleteListener());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.tag_list_fragment_view, container, false);
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		db = new DatabaseAdapter(getActivity().getContentResolver());
		
		return new GenericCursorLoader(getActivity(), new ICursorHelper() {
			
			@Override
			public Uri getUri() {
				return TagListFragment.this.getUri();
			}
			
			@Override
			public String getSortOrder() {
				return null;
			}
			
			@Override
			public Cursor getCursor() {
				return db.getAllTagsCursor();
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		/**
		 * When a tag is clicked, create a cursor pointing at movies containing
		 * that tag. Then send it to TagMovieListActivity using intent.putExtra()
		 */
		
		Intent intent = new Intent(getActivity(), TagMovieListActivity.class);
		intent.putExtra(MainActivity.TAG_ID, id);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
			
	}
	
	 /**
     * The listener for when the user does a long-tap on an item in the list.
     * 
     * The Movie object in the list is removed if the user confirms that he wants to remove the Movie.
     * 
     * @author Johan
     * @author lisastenberg
     */
    private class OnDeleteListener implements OnItemLongClickListener {
    	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			db = new DatabaseAdapter(getActivity().getContentResolver());
			
			Cursor selectedTag = (Cursor) getListView().getItemAtPosition(position);
    		final Tag tag = db.getTag(Long.parseLong(selectedTag.getString(0)));
    		
            AlertDialog.Builder alertbox = new AlertDialog.Builder(getActivity());
            alertbox.setMessage(getString(R.string.delete_dialog_text) +" \"" + tag.getName() + "\"?");           
            alertbox.setPositiveButton(getString(R.string.delete_button_positive), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                	
                	db = new DatabaseAdapter(getActivity().getContentResolver());
                	db.removeTag(tag);
                	
                    Toast.makeText(getActivity().getApplicationContext(), "\"" + tag.getName() + "\" ws deleted" , Toast.LENGTH_SHORT).show();
                }
            });
            alertbox.setNeutralButton(getString(R.string.delete_button_negative), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    
                }
            });
            
            alertbox.show();
			return true;
		}    	
	}

	private void setUpAdapter() {
		String[] from = new String[] { TagsTable.COLUMN_NAME };
		int[] to = new int[] { android.R.id.text1 };
		
		getActivity().getSupportLoaderManager().initLoader(1, null, this);
		super.setAdapter(new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1 , null, from, to, 0));
	}
}
