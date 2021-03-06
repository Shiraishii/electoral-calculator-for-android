package android.ElectoralCalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

public class PartyListActivity extends Activity {
	
	Button buttonContinue;
	Button buttonAddParty;
	Button buttonClearList;
	
	ListView listParties;
	
	PartyListAdapter adapter = new PartyListAdapter(this, Data.listOfParties);
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partylist);
        
        listParties = (ListView)findViewById(R.id.listParties);
        
        // Inflate the header for listParties
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.listpartiesheader, (ViewGroup) findViewById(R.id.party_list_header));
        
        // Add the header to listParties
        listParties.addHeaderView(header, null, false); // addHeaderView(view, data, isSelectable);
        
        listParties.setAdapter(adapter);
        
        registerForContextMenu(listParties);

        buttonContinue = (Button)findViewById(R.id.buttonContinue);

        buttonContinue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Data.listOfParties.isEmpty()) {
    				Context context = getApplicationContext();
    				CharSequence text = getString(R.string.toastPartyListEmpty);
    				int duration = Toast.LENGTH_SHORT;
    				
    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
				} else {
					Intent intent = new Intent(PartyListActivity.this, OptionsActivity.class);
					startActivity(intent);
				}
			}
        });
        
        buttonAddParty = (Button)findViewById(R.id.buttonAddParty);
                    
        buttonAddParty.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PartyListActivity.this, AddPartyActivity.class);
				startActivity(intent);
			}
		});
        
        buttonClearList = (Button)findViewById(R.id.buttonClearList);
        
        buttonClearList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Clear the list of parties and notify the adapter
				Data.listOfParties.clear();
				adapter.notifyDataSetChanged();
			}
        });
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_remove_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.edit_menu_item:
				// Launch EditPartyActivity to edit the values of the selected party
				Intent intent = new Intent(getApplicationContext(), EditPartyActivity.class);
				
				// Pass selected item's position in the array to EditPartyActivity
				intent.putExtra("POS", info.position - 1); // It's -1 due to the header
				
				startActivity(intent);

                return true;
            
            case R.id.remove_menu_item:
				// Remove the selected party from the list
				Data.listOfParties.remove(info.position - 1); // It's -1 due to the header
				adapter.notifyDataSetChanged();
                
				return true;
            
            case R.id.cancel_menu_item:
            	return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu, menu);
    	return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.about_menu:
    		startActivity(new Intent(this, AboutActivity.class));
    		return true;
    	case R.id.settings_menu:
    		startActivity(new Intent(this, PreferencesActivity.class));
    		return true;
    	// More items go here (if any) ...
    	}
    	return false;
    }
}