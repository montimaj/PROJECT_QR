import com.oracle.VerSig;

import java.io.IOException;

class Verify
{
  public static void main(String args[]) throws IOException
  {
      VerSig.verify(args[2],args[1],args[0]);
  }
}