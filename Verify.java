import com.oracle.VerSig;
import edu.sxccal.utilities.Log;
class Verify
{
  public static void main(String args[]) throws Exception
  {
      try
      {
	boolean v=VerSig.verify(args[2],args[1],args[0]);
	Runtime r=Runtime.getRuntime();
	Process p=r.exec("zenity --info --text="+"Verification:"+v);
	p.waitFor();
      }
      catch(Exception e)
      {
	Runtime r=Runtime.getRuntime();	
	String s=Log.create_log(e);
        Process p=r.exec("zenity --error --text="+s);
	p.waitFor();
      }      
  }
}