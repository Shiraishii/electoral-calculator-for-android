package android.ElectoralCalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddParty extends Activity {
	
	EditText editParty;
	EditText editVotes;
	Button buttonAddNewParty;
	Button buttonCancelNewParty;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addparty);
		
        editParty = (EditText)findViewById(R.id.editParty);
        editVotes = (EditText)findViewById(R.id.editVotes);
        
        buttonAddNewParty = (Button)findViewById(R.id.buttonAddNewParty);
        
        buttonAddNewParty.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			String strNewParty = editParty.getText().toString();
    			String strNewVotes = editVotes.getText().toString();

    			/* If editParty and editVotes are not empty,
    			 * then add the party to the map and to the arraylist
    			 * and notify the adapter that there is new data
    			 */
    			if (strNewParty.length() > 0 && strNewVotes.length() > 0)
    			{
    				MainActivity.votes.put(strNewParty, Integer.parseInt(strNewVotes));
    			
    				MainActivity.listItems.add(strNewParty + ": " + strNewVotes);
    				//PartyListActivity.adapter.notifyDataSetChanged();
    				
    				// Clean the EditTexts
    				editParty.setText("");
    				editVotes.setText("");
    				
    				editParty.requestFocus();
    				
    				Intent intent = new Intent(AddParty.this, MainActivity.class);
    				startActivity(intent);
    			}
    			/* Else if both EditTexts are empty
    			 * use a toast notification to warn the user
    			 */
    			else if (strNewParty.length() == 0 && strNewVotes.length() == 0)
    			{
    				Context context = getApplicationContext();
    				CharSequence text = getString(R.string.toastPartyVotesEmpty);
    				int duration = Toast.LENGTH_SHORT;
    				
    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    			}
    			/* Else if editParty is empty
    			 * use a toast notification to warn the user
    			 */
    			else if (strNewParty.length() == 0)
    			{
    				Context context = getApplicationContext();
    				CharSequence text = getString(R.string.toastPartyEmpty);
    				int duration = Toast.LENGTH_SHORT;
    				
    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    			}
    			/* Else if editVotes is empty
    			 * use a toast notification to warn the user
    			 */
    			else if (strNewVotes.length() == 0)
    			{
    				Context context = getApplicationContext();
    				CharSequence text = getString(R.string.toastVotesEmpty);
    				int duration = Toast.LENGTH_SHORT;
    				
    				Toast toast = Toast.makeText(context, text, duration);
    				toast.show();
    			}
    		}
    	});
	}
}