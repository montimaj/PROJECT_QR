package AuthenticQR;

import com.oracle.VerSig;
import edu.sxccal.utilities.Log;

/**
* Displays verification result
* using {@link com.oracle.VerSig}
*/
class Verify
{
  /** 
  * @param args[] input files where 
  * <p> 
  * args[2]= path to 'suepk'
  * args[1]= path to 'sig'
  * args[0]= path to the input file
  */
  public static void main(String args[]) throws Exception
  {
      try
      {
	boolean v=VerSig.verify(args[2],args[1],args[0]);
	String[] x={"zenity","--info","--text="+"Verification result: "+v};
	Process p=new ProcessBuilder(x).start(); //Display verification result in a new window
	p.waitFor();
      }
      catch(Exception e)
      {		
	String s=Log.create_log(e); //Create log file if exception occurs
	String[] x={"zenity","--error","--text="+s};
	Process p=new ProcessBuilder(x).start(); //Show error window
	p.waitFor();
      }      
  }
}