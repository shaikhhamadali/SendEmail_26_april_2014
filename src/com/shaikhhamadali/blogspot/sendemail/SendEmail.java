package com.shaikhhamadali.blogspot.sendemail;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class SendEmail extends Activity {
	//Create variables
	Uri selectedImageUri;
	//declare views/controls
	Button btnSendMail;
	ImageButton imBtnImage;
	EditText edTMail,edTSubject,edTMessage,edTSignature;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_email);
		initializeControls();
	}
	private void initializeControls() {
		//get reference of the UI Controls
		edTMail=(EditText)findViewById(R.id.edTMail);
		edTSubject=(EditText)findViewById(R.id.edTSubject);
		edTMessage=(EditText)findViewById(R.id.edTMessage);
		edTSignature=(EditText)findViewById(R.id.edTSignature);
		imBtnImage=(ImageButton)findViewById(R.id.imBtnImage);
		btnSendMail	=(Button)findViewById(R.id.btnSendMail);
		//Set click listeners to Buttons
		imBtnImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Create instance of intent
				Intent intent = new Intent();
				// set type as image
				intent.setType("image/*");
				/*set action as ACTION_GET_CONTENT(Activity Action:
                Allow the user to select a particular kind of data and return it.)*/
				intent.setAction(Intent.ACTION_GET_CONTENT);
				/*start activity for result and pass create Chooser 
                 and set title of dialog as Select Picture*/
				startActivityForResult(Intent.createChooser(intent,
						"Select Picture"), 1);
			}
		});
		btnSendMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Create instance of intent
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[]{edTMail.getText().toString()});
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, edTSubject.getText().toString());
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, edTMessage.getText().toString()+"\n\n"+edTSignature.getText().toString()); 
				// check that is user picked an image or not
				if(selectedImageUri!=null){
					emailIntent.setType("image/jpeg");
					//attach image to email by telling intent the uri of image
					emailIntent.putExtra(Intent.EXTRA_STREAM, selectedImageUri);
				}
				else emailIntent.setType("text/plain");
				/*start activity and pass create Chooser 
	                 and set title of dialog as Send your email in: */
				startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
			}
		});
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//RESULT_OK means that user has selected an image 
		if (resultCode == RESULT_OK) {
			//check that is the same request code that pass above 
			if (requestCode == 1) {
				//get uri from data for attachment as image
				selectedImageUri = data.getData();
				//set image on imagebutton to show the selection
				imBtnImage.setImageURI(selectedImageUri);
			}
		}
	}

}
