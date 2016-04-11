package com.deviceInfromation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.provider.Settings.Secure;

import java.security.MessageDigest;
import java.util.List;

import engineering.R;

/**
 * Created by sd on 2015/11/3.
 */
public class activity_deviceInfo extends Activity implements OnClickListener {
    TelephonyManager telephonyManager;

    int callState;//电话状态：
    TextView tv_callState;

    CellLocation cellLocation;//电话方位........

    String imei;//唯一的设备ID
    TextView tv_imei;
    String softwareVersion;//设备的软件版本号：
    TextView tv_softwareVersion;
    String phonenumber;//手机号
    TextView tv_phonenumber;

    List<NeighboringCellInfo> neiborPhoneInfolist;//...........

    String netCountryIso;//获取ISO标准的国家码，即国际长途区号
    TextView tv_netCountryIso;
    String mccNcc;//MCC+MNC(mobile country code + mobile network code)
    TextView tv_mccNcc;
    String operatorName;//按照字母次序的current registered operator(当前已注册的用户)的名字
    TextView tv_operatorName;
    int typeNet;//当前使用的网络类型：
    TextView tv_typeNet;
    int typePhone;//手机类型：
    TextView tv_typePhone;
    String simCountryIso;//获取ISO国家码，相当于提供SIM卡的国家码。
    TextView tv_simCountryIso;
    String simOperator;//获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
    TextView tv_simOperator;
    String simOperatorName;//服务商名称：
    TextView tv_simOperatorName;
    String simSerialNumber;//SIM卡的序列号：
    TextView tv_simSerialNumber;
    int simState;//SIM的状态信息：
    TextView tv_simState;
    String subscriberId;//唯一的用户ID：
    TextView tv_subscriberId;
    String voiceMailAlphaTag;//取得和语音邮件相关的标签，即为识别符
    TextView tv_voiceMailAlphaTag;
    String voiceMailNumber;//获取语音邮件号码：
    TextView tv_voiceMailNumber;
    boolean iccCard;//ICC卡是否存在
    TextView tv_iccCard;
    boolean netWorkRoaming;//是否漫游:
    TextView tv_netWorkRoaming;
    boolean canChangeDtmfToneLength;//dtmf音调长度.
    TextView tv_canChangeDtmfToneLength;

    String Pseudo_Unique;
    TextView tv_Pseudo_Unique;

    String The_Android_ID;
    TextView tv_The_Android_ID;

    String The_Wlan_Mac_Address;
    TextView tv_The_Wlan_Mac_Address;
    WifiManager wifiManager;

    String The_BT_MAC_Address;
    TextView tv_The_BT_MAC_Address;
    BluetoothAdapter bluetoothAdapter = null;

    String Combined_Device_ID;
    TextView tv_Combined_Device_ID;

    Button btn_getInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);
        initView();
        initData();
        initListener();
    }

    public void initView() {
        telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);

        btn_getInfo = (Button) findViewById(R.id.btn_getInfo);

        tv_callState = (TextView) findViewById(R.id.tv_callState);
        tv_imei = (TextView) findViewById(R.id.tv_imei);
        tv_softwareVersion = (TextView) findViewById(R.id.tv_softwareVersion);
        tv_phonenumber = (TextView) findViewById(R.id.tv_phonenumber);
        tv_netCountryIso = (TextView) findViewById(R.id.tv_netCountryIso);
        tv_mccNcc = (TextView) findViewById(R.id.tv_mccNcc);
        tv_operatorName = (TextView) findViewById(R.id.tv_operatorName);
        tv_typeNet = (TextView) findViewById(R.id.tv_typeNet);
        tv_typePhone = (TextView) findViewById(R.id.tv_typePhone);
        tv_simCountryIso = (TextView) findViewById(R.id.tv_simCountryIso);
        tv_simOperator = (TextView) findViewById(R.id.tv_simOperator);
        tv_simOperatorName = (TextView) findViewById(R.id.tv_simOperatorName);
        tv_simSerialNumber = (TextView) findViewById(R.id.tv_simSerialNumber);
        tv_simState = (TextView) findViewById(R.id.tv_simState);
        tv_subscriberId = (TextView) findViewById(R.id.tv_subscriberId);
        tv_voiceMailAlphaTag = (TextView) findViewById(R.id.tv_voiceMailAlphaTag);
        tv_voiceMailNumber = (TextView) findViewById(R.id.tv_voiceMailNumber);
        tv_iccCard = (TextView) findViewById(R.id.tv_iccCard);
        tv_netWorkRoaming = (TextView) findViewById(R.id.tv_netWorkRoaming);
        tv_canChangeDtmfToneLength = (TextView) findViewById(R.id.tv_canChangeDtmfToneLength);
        tv_Pseudo_Unique = (TextView) findViewById(R.id.tv_Pseudo_Unique);
        tv_The_Android_ID = (TextView) findViewById(R.id.tv_The_Android_ID);
        tv_The_Wlan_Mac_Address = (TextView) findViewById(R.id.tv_The_Wlan_Mac_Address);
        tv_The_BT_MAC_Address = (TextView) findViewById(R.id.tv_The_BT_MAC_Address);
        tv_Combined_Device_ID = (TextView) findViewById(R.id.tv_Combined_Device_ID);
    }

    @SuppressLint("NewApi")
    public void initData() {
        callState = telephonyManager.getCallState();

        cellLocation = telephonyManager.getCellLocation();

        imei = telephonyManager.getDeviceId();
        softwareVersion = telephonyManager.getDeviceSoftwareVersion();
        phonenumber = telephonyManager.getLine1Number();

        //Log.i("lyw","................."+phonenumber);

        neiborPhoneInfolist = telephonyManager.getNeighboringCellInfo();

        netCountryIso = telephonyManager.getNetworkCountryIso();
        mccNcc = telephonyManager.getNetworkOperator();
        operatorName = telephonyManager.getNetworkOperatorName();
        typeNet = telephonyManager.getNetworkType();
        typePhone = telephonyManager.getPhoneType();
        simCountryIso = telephonyManager.getSimCountryIso();
        simOperator = telephonyManager.getSimOperator();
        simOperatorName = telephonyManager.getSimOperatorName();
        simSerialNumber = telephonyManager.getSimSerialNumber();
        simState = telephonyManager.getSimState();
        subscriberId = telephonyManager.getSubscriberId();
        voiceMailAlphaTag = telephonyManager.getVoiceMailAlphaTag();
        voiceMailNumber = telephonyManager.getVoiceMailNumber();
        iccCard = telephonyManager.hasIccCard();
        netWorkRoaming = telephonyManager.isNetworkRoaming();
        //canChangeDtmfToneLength = telephonyManager.canChangeDtmfToneLength();//没有此方法？？

        Pseudo_Unique = "35" + Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        The_Android_ID = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

        wifiManager = (WifiManager) getSystemService(getApplicationContext().WIFI_SERVICE);
        The_Wlan_Mac_Address = wifiManager.getConnectionInfo().getMacAddress();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        The_BT_MAC_Address = bluetoothAdapter.getAddress();

        String str=imei + Pseudo_Unique + The_Android_ID + The_Wlan_Mac_Address + The_BT_MAC_Address;
        Combined_Device_ID = computeMD5(str);

    }

    public void initListener() {
        btn_getInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getInfo://点击显示所有数据。
                tv_callState.setText(callState + "");
                tv_imei.setText(imei);
                tv_softwareVersion.setText(softwareVersion);
                tv_phonenumber.setText(phonenumber);
                tv_netCountryIso.setText(netCountryIso);
                tv_mccNcc.setText(mccNcc);
                tv_operatorName.setText(operatorName);
                tv_typeNet.setText(typeNet + "");
                tv_typePhone.setText(typePhone + "");
                tv_simCountryIso.setText(simCountryIso);
                tv_simOperator.setText(simOperator);
                tv_simOperatorName.setText(simOperatorName);
                tv_simSerialNumber.setText(simSerialNumber);
                tv_simState.setText(simState + "");
                tv_subscriberId.setText(subscriberId + "");
                tv_voiceMailAlphaTag.setText(voiceMailAlphaTag);
                tv_voiceMailNumber.setText(voiceMailNumber + "");
                tv_iccCard.setText(iccCard + "");
                tv_netWorkRoaming.setText(netWorkRoaming + "");
                //tv_canChangeDtmfToneLength.setText(canChangeDtmfToneLength + "");
                tv_Pseudo_Unique.setText(Pseudo_Unique);

                tv_The_Android_ID.setText(The_Android_ID);

                tv_The_Wlan_Mac_Address.setText(The_Wlan_Mac_Address);

                tv_The_BT_MAC_Address.setText(The_BT_MAC_Address);

                tv_Combined_Device_ID.setText(Combined_Device_ID);

                break;
            default:
                break;
        }

    }


    public String computeMD5(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        messageDigest.update(str.getBytes(), 0, str.length());
        byte p_md5Data[] = messageDigest.digest();
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        return m_szUniqueID.toUpperCase();
    }
}
