import com.oracle.VerSig;

class Verify
{
  public static void main(String args[]) throws Exception
  {
      try
      {
	VerSig.verify(args[2],args[1],args[0]);
      }
      catch(Exception e)
      {
	Runtime r=Runtime.getRuntime();	
	String s="zenity --error --text="+"Retry!";
        Process p=r.exec(s);
	p.waitFor();
      }      
  }
}