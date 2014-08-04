import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class Tests {
	
	public static void main(String args[]) throws UnknownHostException{
		/*Enumeration e = null;
		try {
			e = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(e.hasMoreElements())
		{
		    NetworkInterface n = (NetworkInterface) e.nextElement();
		    Enumeration ee = n.getInetAddresses();
		    while (ee.hasMoreElements())
		    {
		        InetAddress i = (InetAddress) ee.nextElement();
		        System.out.println(i.getHostAddress());
		    }
		}*/
		 System.out.println(getIp());
	}
	
	public static String getIp(){
	    String ipAddress = null;
	    Enumeration<NetworkInterface> net = null;
	    try {
	        net = NetworkInterface.getNetworkInterfaces();
	    } catch (SocketException e) {
	        throw new RuntimeException(e);
	    }

	    while(net.hasMoreElements()){
	        NetworkInterface element = net.nextElement();
	        Enumeration<InetAddress> addresses = element.getInetAddresses();
	        while (addresses.hasMoreElements()){
	            InetAddress ip = addresses.nextElement();
	            if (ip instanceof Inet4Address){

	                if (ip.isSiteLocalAddress()){

	                    ipAddress = ip.getHostAddress();
	                }

	            }

	        }
	    }
	    return ipAddress;
	}
	
}


