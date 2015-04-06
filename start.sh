#!/bin/bash
export MAIN_DIALOG='
<window title="GenQR">
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
	<action signal="clicked">javac GenQR.java && java GenQR $FILE $FILE_DIRECTORY</action>
	<variable>"flag"</variable>
      </button>
    </vbox>
   
   </vbox>
</window>
'
gtkdialog --program=MAIN_DIALOG
