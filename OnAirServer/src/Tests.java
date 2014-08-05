import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;


public class Tests {
	
	public static void main(String args[]) throws UnknownHostException, SocketException{
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
		 System.out.println("broadcast: "+getBroadcast().getHostAddress()+"\nip address:"+
				 getIp());
	}
	
	public static InetAddress getBroadcast() throws SocketException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		for (Enumeration<NetworkInterface> niEnum = NetworkInterface
				.getNetworkInterfaces(); niEnum.hasMoreElements();) {
			NetworkInterface ni = niEnum.nextElement();
			if (!ni.isLoopback()) {
				for (InterfaceAddress interfaceAddress : ni
						.getInterfaceAddresses()) {

					return interfaceAddress.getBroadcast();
				}
			}
		}
		return null;
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


