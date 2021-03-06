/**
 *  ContentListFragment.java
 * 
 *  An abstract class that is a ListFragment presenting data from a Content Provider.
 * 
 *	@author lisastenberg
 *	@copyright (c) 2012 Johan Brook, Robin Andersson, Lisa Stenberg, Mattias Henriksson
 *	@license MIT
 */

package se.chalmers.watchme.ui;

import se.chalmers.watchme.database.DatabaseAdapter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;

public abstract class ContentListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<Cursor> {

	private Uri uri;
	private SimpleCursorAdapter adapter;

	protected DatabaseAdapter db;

	/**
	 * Creates a new ContentListFragment with a Uri.
	 * 
	 * @param uri
	 *            The uri
	 */
	public ContentListFragment(Uri uri) {
		this.uri = uri;
	}

	@Override
	public void onActivityCreated(Bundle b) {
		super.onActivityCreated(b);
		Thread.currentThread().setContextClassLoader(
				getActivity().getClassLoader());
		
		db = new DatabaseAdapter(getActivity().getContentResolver());
		
		setHasOptionsMenu(true);
	}

	/**
	 * Return the Uri
	 * 
	 * @return the Uri
	 */
	public Uri getUri() {
		return uri;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
		adapter.swapCursor(cursor);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// data is not available anymore, delete reference
		adapter.swapCursor(null);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Return the CursorAdapter of the fragment
	 * 
	 * @return the CursorAdapter of the fragment.
	 */
	public SimpleCursorAdapter getAdapter() {
		return adapter;
	}

	/**
	 * Set the adapter of the fragment.
	 */
	protected void setAdapter(SimpleCursorAdapter adapter) {
		this.adapter = adapter;
		setListAdapter(this.adapter);
	}
}
