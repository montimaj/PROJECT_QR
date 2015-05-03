#!/bin/bash

clear
export MAIN_DIALOG='

<window title="Authentic QR">
  
  <notebook labels="Generate|Verify">
    
    <vbox>
    
    <frame Select Input File>
	  <hbox>
	    <entry>
	      <variable>FILE</variable>
	    </entry>
	    <button>
	      <input file stock="gtk-open"></input>
	      <variable>FILE_BROWSE</variable>
	      <action type="fileselect">FILE</action>
	    </button>
	  </hbox>
     </frame>
    
      <frame Select Output Directory>
	<hbox>
	  <entry accept="directory">
	    <variable>FILE_DIRECTORY</variable>
	  </entry>
	  <button>
	    <input file stock="gtk-open"></input>
	    <variable>FILE_BROWSE_DIRECTORY</variable>
	    <action type="fileselect">FILE_DIRECTORY</action>
	  </button>
	</hbox>
      </frame>
    
      <vbox>
	<button>
	  <height>20</height>
	  <width>40</width>
	  <label>Generate</label>
	  <action signal="clicked">clear && java GenQR "$FILE" "$FILE_DIRECTORY"</action>
	  <variable>"flag"</variable>
	</button>
      </vbox>
    
    </vbox>
    
    <vbox>
      <expander label="Extract Zip" expanded="false" use-underline="true">
	<frame Unzip>
	  <hbox>
	    <entry>
	      <variable>ZFILE</variable>
	    </entry>
	    <button>
	      <input file stock="gtk-open"></input>
	      <variable>zfile</variable>
	      <action type="fileselect">ZFILE</action>
	    </button>
	  </hbox>
	  <vbox>
	    <button>
	      <height>20</height>
	      <width>40</width>
	      <label>Extract</label>
	      <action signal="clicked">unzip -o "$ZFILE" -d "$(dirname "$ZFILE")"; zenity --info --text="Unzip Successful!"</action>
	    </button>
	  </vbox>
	</frame>
      </expander>
      
      <expander label="Verification" expanded="true">
	<vbox>
	  <frame Choose File to be Tested>
	    <hbox>
	      <entry>
		<variable>XFILE</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>xfile</variable>
		<action type="fileselect">XFILE</action>
	      </button>
	    </hbox>
	  </frame>
      
	  <frame Choose Corresponding Signature>
	    <hbox>
	      <entry>
		<variable>XSIG</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>pkey</variable>
		<action type="fileselect">XSIG</action>
	      </button>
	    </hbox>
	  </frame>
    
	  <frame Choose Appropriate Public Key>
	    <hbox>
	      <entry>
		<variable>PKEY</variable>
	      </entry>
	      <button>
		<input file stock="gtk-open"></input>
		<variable>pkey</variable>
		<action type="fileselect">PKEY</action>
	      </button>
	    </hbox>
	  </frame>
    
	  <vbox>
	    <button>
	      <height>20</height>
	      <width>40</width>
	      <label>Verify</label>
	      <action signal="clicked">clear && java Verify "$XFILE" "$XSIG" "$PKEY"</action>
	    </button>
	  </vbox>
	 </vbox>
      </expander>
    
    </vbox>
   
   </notebook>
   
</window>

'
gtkdialog --program=MAIN_DIALOG
