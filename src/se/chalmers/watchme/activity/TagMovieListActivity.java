package se.chalmers.watchme.activity;

import se.chalmers.watchme.R;
import se.chalmers.watchme.ui.MovieListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class TagMovieListActivity extends FragmentActivity {

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //Recieve cursor sent from TagListFragment
        Intent intent = getIntent();
        String cursor = intent.getStringExtra(MainActivity.EXTRA_CURSOR);
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        
        //Put cursor as a parameter to a new MovieListFragment
        ft.add(android.R.id.content, new MovieListFragment(cursor));
        ft.commit();
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override
	public void onBackPressed() {
	    this.finish();
	    System.out.println("--- H�R ----");
	    overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	    return;
	}
	
}
