package com.slipkprojects.sockshttp.util;

import java.util.*;
import java.net.*;
import java.io.*;

public class Util
{
	private static final String CANDIDATE_10_SLASH_8 = "10.0.0.1";
    private static final String SUBNET_10_SLASH_8 = "10.0.0.0";
    private static final int PREFIX_LENGTH_10_SLASH_8 = 8;
    private static final String ROUTER_10_SLASH_8 = "10.0.0.2";

    private static final String CANDIDATE_172_16_SLASH_12 = "172.16.0.1";
    private static final String SUBNET_172_16_SLASH_12 = "172.16.0.0";
    private static final int PREFIX_LENGTH_172_16_SLASH_12 = 12;
    private static final String ROUTER_172_16_SLASH_12 = "172.16.0.2";

    private static final String CANDIDATE_192_168_SLASH_16 = "192.168.0.1";        
    private static final String SUBNET_192_168_SLASH_16 = "192.168.0.0";
    private static final int PREFIX_LENGTH_192_168_SLASH_16 = 16;
    private static final String ROUTER_192_168_SLASH_16 = "192.168.0.2";

    private static final String CANDIDATE_169_254_1_SLASH_24 = "169.254.1.1";        
    private static final String SUBNET_169_254_1_SLASH_24 = "169.254.1.0";
    private static final int PREFIX_LENGTH_169_254_1_SLASH_24 = 24;
    private static final String ROUTER_169_254_1_SLASH_24 = "169.254.1.2";
	private static IProtectSocket protectSocket;

	public interface IProtectSocket
	{
		boolean protectSocket(Socket socket);
		void setRemoteProxy(String str);
	}
	public static void setIProtectSocket(Object obj)
	{
		if (obj instanceof IProtectSocket) {
			protectSocket = ((IProtectSocket)obj);
		}
	}
	public static boolean isProtectSocket(Socket socket)
	{
		return protectSocket != null ? protectSocket.protectSocket(socket) : false;
	}
	public static void setRemoteProxy(String proxy)
	{
		if (protectSocket != null) {
			protectSocket.setRemoteProxy(proxy);
		}
	}
	public static IProtectSocket getIProtectSocket()
	{
		if (protectSocket != null) {
			return protectSocket;
		}
		return null;
	}


    public static String getPrivateAddressSubnet(String privateIpAddress)
    {
        if (0 == privateIpAddress.compareTo(CANDIDATE_10_SLASH_8))
        {
            return SUBNET_10_SLASH_8;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_172_16_SLASH_12))
        {
            return SUBNET_172_16_SLASH_12;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_192_168_SLASH_16))
        {
            return SUBNET_192_168_SLASH_16;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_169_254_1_SLASH_24))
        {
            return SUBNET_169_254_1_SLASH_24;
        }
        return null;
    }

    public static int getPrivateAddressPrefixLength(String privateIpAddress)
    {
        if (0 == privateIpAddress.compareTo(CANDIDATE_10_SLASH_8))
        {
            return PREFIX_LENGTH_10_SLASH_8;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_172_16_SLASH_12))
        {
            return PREFIX_LENGTH_172_16_SLASH_12;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_192_168_SLASH_16))
        {
            return PREFIX_LENGTH_192_168_SLASH_16;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_169_254_1_SLASH_24))
        {
            return PREFIX_LENGTH_169_254_1_SLASH_24;
        }
        return 0;        
    }

    public static String getPrivateAddressRouter(String privateIpAddress)
    {
        if (0 == privateIpAddress.compareTo(CANDIDATE_10_SLASH_8))
        {
            return ROUTER_10_SLASH_8;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_172_16_SLASH_12))
        {
            return ROUTER_172_16_SLASH_12;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_192_168_SLASH_16))
        {
            return ROUTER_192_168_SLASH_16;
        }
        else if (0 == privateIpAddress.compareTo(CANDIDATE_169_254_1_SLASH_24))
        {
            return ROUTER_169_254_1_SLASH_24;
        }
        return null;
    }

	public static String getIPAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			//ex.printStackTrace();
		}
		return "127.0.0.1";
	}

	public static String getPing(String xHost) {
		String time = "";
		String pingCmd = "ping " + xHost;
		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec(pingCmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String inputLine = in.readLine();
			while ((inputLine != null)) {
                if (inputLine.length() > 0 && inputLine.contains("time")) {
					time = inputLine.substring(inputLine.indexOf("time"));
					break;
                }
				inputLine = in.readLine();
			}
			return time;
		} catch (Exception e) {
			return null;
		}
	}

    public static String byteCountToDisplaySize(long bytes, boolean si)
    {
        // http://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java/3758880#3758880
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

	public static String byteTrafficToCount(long bytes, boolean mbit)
    {
        if (mbit)
            bytes = bytes * 8;
        int unit = mbit ? 1000 : 1024;
        if (bytes < unit)
            return bytes + (mbit ? " bit" : " B");
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (mbit ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (mbit ? "" : "");
        if (mbit)
            return String.format(Locale.getDefault(), "%.1f %sb/s", bytes / Math.pow(unit, exp), pre);
        else
            return String.format(Locale.getDefault(), "%.1f %sB/s", bytes / Math.pow(unit, exp), pre);
    }

}

