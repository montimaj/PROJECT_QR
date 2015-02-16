package edu.sxccal.qrcodescanner;

import com.oracle.android.GenSig;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

public class GenQR extends Activity
{
	private EditText et;
	public static TextView tv;
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gen_qr);		
		et=(EditText)findViewById(R.id.genqr);
		et.setText(QRCode.filePath+"/");
		tv=(TextView)findViewById(R.id.wfile);
		et.setOnEditorActionListener(new OnEditorActionListener() 
		{		    
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		    {		        
		        if (actionId == EditorInfo.IME_ACTION_DONE)
		        {		            
		            String fp=et.getText().toString();
		            tv.setText("");
		            GenSig.Gen_sig(fp);		            
		            return true;
		        }
		        return false;
		    }
		});
	}	
}
