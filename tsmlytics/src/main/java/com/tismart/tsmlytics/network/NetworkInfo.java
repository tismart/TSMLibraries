package com.tismart.tsmlytics.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.tismart.tsmlytics.entities.Network;

/**
 * Created by luis.rios on 29/04/2015.
 */
public class NetworkInfo {

    private static final CustomPhoneStateListener phoneStateListener = new CustomPhoneStateListener();

    /**
     * <p>Obtiene la información de la red del dispositivo.</p>
     * <p>Si hay algún error el método devolverá null</p>
     * <p>Network.Connection = Modo de conexión Wi-Fi, Mobile, Ethernet, Bluetooth en String</p>
     * <p>Network.Type = Tipo de conexión Wi-Fi, 3G, Edge entre otros en String</p>
     * <p>Network.Streght = Intensidad en la conexión. Siempre es un número negativo en String</p>
     *
     * @param mContext contexto de la aplicación
     * @return Network
     */
    public static Network getNetworkInfo(Context mContext) {
        Network network = new Network();
        try {
            android.net.NetworkInfo networkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo == null)
                network = null;
            else {
                if (networkInfo.isConnected()) {
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            network.setConnection("Wi-Fi");
                            break;
                        case ConnectivityManager.TYPE_BLUETOOTH:
                            network.setConnection("Bluetooth");
                            break;
                        case ConnectivityManager.TYPE_DUMMY:
                            network.setConnection("Dummy");
                            break;
                        case ConnectivityManager.TYPE_ETHERNET:
                            network.setConnection("Ethernet");
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            network.setConnection("Mobile");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                            network.setConnection("Mobile Dun");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                            network.setConnection("Mobile Hipri");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                            network.setConnection("Mobile MMS");
                            break;
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            network.setConnection("Mobile Supl");
                            break;
                        case ConnectivityManager.TYPE_VPN:
                            network.setConnection("VPN");
                            break;
                        case ConnectivityManager.TYPE_WIMAX:
                            network.setConnection("Wi-Max");
                            break;
                        default:
                            network.setConnection("");
                            break;
                    }

                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
//                            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
//                            return wifiInfo.getLinkSpeed()+" "+wifiInfo.LINK_SPEED_UNITS;
                            network.setType("Wi-Fi");
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            switch (networkInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                    network.setType("1xRTT");
                                    break; // ~ 50-100 kbps
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                    network.setType("CDMA");
                                    break; // ~ 14-64 kbps
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                    network.setType("EDGE");
                                    break; // ~ 50-100 kbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                    network.setType("EVDO 0");
                                    break; // ~ 400-1000 kbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                    network.setType("EVDO A");
                                    break; // ~ 600-1400 kbps
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                    network.setType("GPRS");
                                    break; // ~ 100 kbps
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                    network.setType("HSDPA");
                                    break; // ~ 2-14 Mbps
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                    network.setType("HSPA");
                                    break; // ~ 700-1700 kbps
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                    network.setType("HSUPA");
                                    break; // ~ 1-23 Mbps
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                    network.setType("3G");
                                    break; // ~ 400-7000 kbps
                                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                    network.setType("EHRPD");
                                    break; // ~ 1-2 Mbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                    network.setType("EVDO B");
                                    break; // ~ 5 Mbps
                                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                    network.setType("HSPAP");
                                    break; // ~ 10-20 Mbps
                                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                    network.setType("IDEN");
                                    break; // ~25 kbps
                                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                    network.setType("LTE");
                                    break; // ~ 10+ Mbps
                                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                                default:
                                    network.setType("");
                                    break;
                            }
                            break;
                        default:
                            network.setType("");
                            break;
                    }

                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
                            network.setStrength(String.valueOf(wifiInfo.getRssi()));
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                            network.setStrength(String.valueOf((CustomPhoneStateListener.iSignalStrength * 2) - 113));
                            break;
                        default:
                            network.setStrength("");
                    }

                } else
                    network = null;
            }
        } catch (Exception ex) {
            network = null;
        }

        return network;
    }

    @Deprecated
    public static String getNetworkConnection(Context mContext) {
        try {
            android.net.NetworkInfo networkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo == null)
                return "";
            else {
                if (networkInfo.isConnected()) {
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            return "Wi-Fi";
                        case ConnectivityManager.TYPE_BLUETOOTH:
                            return "Bluetooth";
                        case ConnectivityManager.TYPE_DUMMY:
                            return "Dummy";
                        case ConnectivityManager.TYPE_ETHERNET:
                            return "Ethernet";
                        case ConnectivityManager.TYPE_MOBILE:
                            return "Mobile";
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                            return "Mobile Dun";
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                            return "Mobile Hipri";
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                            return "Mobile MMS";
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            return "Mobile Supl";
                        case ConnectivityManager.TYPE_VPN:
                            return "VPN";
                        case ConnectivityManager.TYPE_WIMAX:
                            return "Wi-Max";
                        default:
                            return "";
                    }


                } else
                    return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    @Deprecated
    public static String getNetworkType(Context mContext) {
        try {
            android.net.NetworkInfo networkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo == null)
                return "";
            else {
                if (networkInfo.isConnected()) {
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
//                            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
//                            return wifiInfo.getLinkSpeed()+" "+wifiInfo.LINK_SPEED_UNITS;
                            return "Wi-Fi";
                        case ConnectivityManager.TYPE_MOBILE:
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            switch (networkInfo.getSubtype()) {
                                case TelephonyManager.NETWORK_TYPE_1xRTT:
                                    return "1xRTT"; // ~ 50-100 kbps
                                case TelephonyManager.NETWORK_TYPE_CDMA:
                                    return "CDMA"; // ~ 14-64 kbps
                                case TelephonyManager.NETWORK_TYPE_EDGE:
                                    return "EDGE"; // ~ 50-100 kbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                    return "EVDO 0"; // ~ 400-1000 kbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                    return "EVDO A"; // ~ 600-1400 kbps
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                    return "GPRS"; // ~ 100 kbps
                                case TelephonyManager.NETWORK_TYPE_HSDPA:
                                    return "HSDPA"; // ~ 2-14 Mbps
                                case TelephonyManager.NETWORK_TYPE_HSPA:
                                    return "HSPA"; // ~ 700-1700 kbps
                                case TelephonyManager.NETWORK_TYPE_HSUPA:
                                    return "HSUPA"; // ~ 1-23 Mbps
                                case TelephonyManager.NETWORK_TYPE_UMTS:
                                    return "3G"; // ~ 400-7000 kbps
                                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                    return "EHRPD"; // ~ 1-2 Mbps
                                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                    return "EVDO B"; // ~ 5 Mbps
                                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                    return "HSPAP"; // ~ 10-20 Mbps
                                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                    return "IDEN"; // ~25 kbps
                                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                    return "LTE"; // ~ 10+ Mbps
                                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                                default:
                                    return "";
                            }
                        default:
                            return "";
                    }
                } else
                    return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    @Deprecated
    public static String getNetworkStrength(Context mContext) {
        try {
            android.net.NetworkInfo networkInfo = ((ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (networkInfo == null)
                return "";
            else {
                if (networkInfo.isConnected()) {
                    switch (networkInfo.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            WifiInfo wifiInfo = ((WifiManager) mContext.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
                            return String.valueOf(wifiInfo.getRssi()) + " dBm";
                        case ConnectivityManager.TYPE_MOBILE:
                        case ConnectivityManager.TYPE_MOBILE_DUN:
                        case ConnectivityManager.TYPE_MOBILE_HIPRI:
                        case ConnectivityManager.TYPE_MOBILE_MMS:
                        case ConnectivityManager.TYPE_MOBILE_SUPL:
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                            telephonyManager.listen(null, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
                            return String.valueOf((CustomPhoneStateListener.iSignalStrength * 2) - 113) + " dBm";
                        default:
                            return "";
                    }
                } else
                    return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    private static class CustomPhoneStateListener extends PhoneStateListener {
        public static int iSignalStrength = 0;

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            if (signalStrength.isGsm())
                iSignalStrength = signalStrength.getGsmSignalStrength();
            else
                iSignalStrength = signalStrength.getCdmaDbm();
        }
    }
}
