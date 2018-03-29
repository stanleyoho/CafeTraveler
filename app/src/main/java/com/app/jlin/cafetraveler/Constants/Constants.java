package com.app.jlin.cafetraveler.Constants;

import android.view.View;

/**
 * Created by Wayne on 2018/2/5.
 */

public class Constants {

    /** SharePreferences params */
    public static final String SHARE_PREFERENCES_USER_INFO = "USER_INFO";
    public static final String PREFERENCES_TOKEN_ID = "PREFERENCES_TOKEN_ID";
    public static final String PREFERENCES_DEVICE_ID = "PREFERENCES_DEVICE_ID";
    public static final String PREFERENCES_USER_ID = "PREFERENCES_USER_ID";
    public static final String PREFERENCES_CURRENCY_ID = "PREFERENCES_CURRENCY_ID";
    public static final String PREFERENCES_USER_CODE = "PREFERENCES_USER_CODE";
    public static final String PREFERENCES_CDN_BANNER_API_URL = "PREFERENCES_CDN_BANNER_API_URL";
    public static final String PREFERENCES_LIVE_CHAT_URL = "PREFERENCES_LIVE_CHAT_URL";
    public static final String PREFERENCES_CDN_BANNER = "PREFERENCES_CDN_BANNER";
    public static final String PREFERENCES_IS_VIP_HOME = "PREFERENCES_IS_VIP_HOME";
    public static final String PREFERENCES_HIDE_USER_CODE = "PREFERENCES_HIDE_USER_CODE";
    public static final String PREFERENCES_HIDE_USER_NAME = "PREFERENCES_HIDE_USER_NAME";
    public static final String PREFERENCES_USER_GRAPHICS_LOCK = "USER_GRAPHICS_LOCK";// 圖形鎖密碼
    public static final String PREFERENCES_INTERNAL_MESSAGE_SENDED_COUNT = "USER_INTERNAL_MESSAGE_SENDED_COUNT";   // 未讀信件(收到的)
    public static final String PREFERENCES_INTERNAL_MESSAGE_RECEVIE_COUNT = "USER_INTERNAL_MESSAGE_RECEVIE_COUNT"; // 未讀信件(自己發的)
    public static final String PREFERENCES_IS_ANNOUNCEMENT_READ = "PREFERENCES_IS_ANNOUNCEMENT_READ";   // 是已讀公告
    public static final String PREFERENCES_ANNOUNCEMENT_DATE_TIME = "PREFERENCES_ANNOUNCEMENT_DATE_TIME";   //公告最新時間
    public static final String PREFERENCES_WITHDRAWAL_HOT_KEY = "PREFERENCES_WITHDRAWAL_HOT_KEY"; // 提款的快捷金額

    /** android 6.0 request permission */
    public static final int REQUEST_PERMISSION = 2000;
    /** photo request */
    public static final int REQUEST_PHOTO = 2001;
    /** camera request */
    public static final int REQUEST_CAMERA = 2002;

    /** GraphicsLock request code */
    public static final int REQUEST_SET = 3000;
    public static final int REQUEST_VERIFY = 3001;
    public static final int REQUEST_RESET = 3002;

    /** Activity bundle params , Fragment Page Name */
    public static final String BUNDLE_PAGE_NAME = "pageName";
    public static final String BUNDLE_USER_CODE = "userCode";
    public static final String BUNDLE_VERIFY_TYPE = "verifyType";
    public static final String BUNDLE_PIN_CODE = "pinCode";
    public static final String BUNDLE_FORGOT_PASSWORD_MEMBER_CHECK = "forgotPwdMemberCheck";
    public static final String BUNDLE_VERIFY_INFO = "verifyInfo";
    public static final String BUNDLE_VERIFY_EMAIL = "verifyEmail";
    public static final String BUNDLE_VERIFY_MOBILE = "verifyMobile";
    public static final String BUNDLE_BANK_INFO = "bankInfo";

    /** Network type */
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    /** for activity and dialog , 設置StatusBar懸浮於Activity,Dialog上*/
    public static final int APP_SYSTEM_UI_VISIBILITY = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

    /** Api response errorMessage Success */
    public static final String API_RESPONSE_MESSAGE_SUCCESS = "Success";// 成功

    // 中國 , 印度尼西亞 , 泰國
    public static final int CURRENCY_CN = 156;
    public static final int CURRENCY_IN = 360;
    public static final int CURRENCY_TH = 764;
    public static final String[] CURRENT_LANGUAGE_URI = new String[]{"chs", "id", "th"};
    public static final int[] CURRENT_LANGUAGE_ID = new int[]{0,200,100};
    public static final int[] CURRENCY_ID = new int[]{CURRENCY_CN, CURRENCY_IN, CURRENCY_TH};
    public static final String[] CURRENCY = new String[]{"RMB", "IDR", "THB"};
    public static final String[] CURRENCY_NAME = new String[]{"人民币", "IDR", "บาท"};
    public static final int[] PHONE_COUNTRY_CODE = new int[]{86,62,66};

    // 盤口，odds type 馬來,香港,歐洲,印度尼西亞,美洲
    public static final int ODDS_TYPE_MALAYSIA = 1;
    public static final int ODDS_TYPE_HONG_KONG = 2;
    public static final int ODDS_TYPE_EURO = 3;
    public static final int ODDS_TYPE_INDONESIAN = 4;
    public static final int ODDS_TYPE_AMERICAN = 5;

    public static final int PROMOTION_ITEM_STATUS_NOT_NEED_APPLY = 0; // 不需申請
    public static final int PROMOTION_ITEM_STATUS__APPLY = 1; // 申請中
    public static final int PROMOTION_ITEM_STATUS_CAN_NOT_APPLY = 2; // 無法申請
    public static final int PROMOTION_ITEM_STATUS_APPLY_NOW = 3; // 立即申請

    public static final int PRODUCT_CATEGORY_SPORTS_BOOK = 1; // 體育優惠
    public static final int PRODUCT_CATEGORY_LIVE_CASINO = 2; // 真人娛樂優惠
    public static final int PRODUCT_CATEGORY_SLOT = 3; // 電子遊藝優惠
    public static final int PRODUCT_CATEGORY_SSC_KENO = 4; // 彩票優惠

    public static final int UI_TYPE_ALL = 0; // 全部
    public static final int UI_TYPE_MINE = 1; // 我的優惠
    public static final int UI_TYPE_HOT = 2; // 最多人看得優惠
    public static final int UI_TYPE_RECOMMEND = 3; // 推薦給你的優惠

    public static final int INTERNAL_MESSAGE_CATEGORY_DEPOSIT = 100; //存款
    public static final int INTERNAL_MESSAGE_CATEGORY_WITHDRAWAL = 200; //提款
    public static final int INTERNAL_MESSAGE_CATEGORY_ACCOUNTS = 300;  //帳戶
    public static final int INTERNAL_MESSAGE_CATEGORY_BET = 400;  //投注
    public static final int INTERNAL_MESSAGE_CATEGORY_PROMOTION = 600; //優惠
    public static final int INTERNAL_MESSAGE_CATEGORY_OTHER = 1000; //其他

    public static final int INTERNAL_MESSAGE_SENDER_CUSTOMER_SERVICE = 0; //其他
    public static final int INTERNAL_MESSAGE_SENDER_SYSTEM = 1; //其他
    public static final int INTERNAL_MESSAGE_SENDER_FINANCE = 2; //其他

    public static final int VERIFY_TYPE_EMAIL = 0; // 電郵
    public static final int VERIFY_TYPE_MOBILE = 1; // 手機

    public static final int PRODUCT_WALLET_OLE777 = 0;//豪利777主账户
    public static final int PRODUCT_WALLET_SPORTS_BOOK = 1;//体育博彩账户
    public static final int PRODUCT_WALLET_EA = 2;//EA真人娱乐场账户
    public static final int PRODUCT_WALLET_KG = 3;//KG彩票账户
    public static final int PRODUCT_WALLET_AG = 4;//AG真人娱乐场账户
    public static final int PRODUCT_WALLET_OPUS = 5;//OPUS真人娱乐场账户
    public static final int PRODUCT_WALLET_PT = 6;//PT真人＆电子游戏账户
    public static final int PRODUCT_WALLET_BBIN = 7;//BBIN真人娱乐场账户
    public static final int PRODUCT_WALLET_EAN2 = 8;//EAn2真人娱乐场账户
    public static final int PRODUCT_WALLET_Q_TECH = 9;//QTech电子游戏账户
    public static final int PRODUCT_WALLET_DF = 10;//梦幻体育账户
    public static final int PRODUCT_WALLET_OG = 11;//OG真人娱乐场账户

    // Plat From value
    public static final String PLAT_FROM = "android";

    // api error coed
    public static final String API_FORGOT_PASSWORD_USER_DISABLED = "020005"; // 忘記密碼時，輸入電子郵件或手機錯誤太多次

    //Lobby Page Select
    public static final int LOBBY_HOME_PAGE = 0;
    public static final int LOBBY_VIP_PAGE = 1;

    /** realm db version */
    public static final int REALM_VERSION = 1;
    /** realm db name */
    public static final String REALM_NAME = "%s_Endebess.realm";

    //Internal message Select
    public static final int INTERNAL_MESSAGE_MODE_SEND = 1;
    public static final int INTERNAL_MESSAGE_MODE_RECEIVE = 0;

    //Common Function
    public static final  int COMMON_FUNCTION_BANK_TRANSFER = 1;
    public static final  int COMMON_FUNCTION_THIRD_PARTY = 2;
    public static final  int COMMON_FUNCTION_ALIPAY_DEPOSIT = 3;
    public static final  int COMMON_FUNCTION_WECHAT_DEPOSIT = 4;
    public static final  int COMMON_FUNCTION_QQPAY_DEPOSIT = 5;
    public static final  int COMMON_FUNCTION_ATM_DEPOSIT = 6;
    public static final  int COMMON_FUNCTION_CASH_DEPOSIT = 7;
    public static final  int COMMON_FUNCTION_WITH_DRAWL = 8;
    public static final  int COMMON_FUNCTION_TRANSFER = 9;
    public static final  int COMMON_FUNCTION_HELP_CENTER = 10;
    public static final  int COMMON_FUNCTION_CSAH_LOG = 11;
    public static final  int COMMON_FUNCTION_BET_HISTROY = 12;
    public static final  int COMMON_FUNCTION_ANNOUNCEMENT = 13;

    //Product Type Id
    public static final  int PRODUCT_TYPE_LOBBY_GAME= 0; // Lobby Tiger
    public static final  int PRODUCT_TYPE_ONEWORKS_SPORTSBOOK= 1;
    public static final  int PRODUCT_TYPE_EA_LIVECASINO = 2;
    public static final  int PRODUCT_TYPE_KG_KENO = 3;
    public static final  int PRODUCT_TYPE_KG_SSC = 4;
    public static final  int PRODUCT_TYPE_BETSOFT_GAMES = 5;
    public static final  int PRODUCT_TYPE_AG_LIVECASINO = 6;
    public static final  int PRODUCT_TYPE_OPUS_CASINO = 7;
    public static final  int PRODUCT_TYPE_KG2 = 8;
    public static final  int PRODUCT_TYPE_LOTTORACE = 9;
    public static final  int PRODUCT_TYPE_ONEWORKS_ROYAL = 10;
    public static final  int PRODUCT_TYPE_MG = 12;
    public static final  int PRODUCT_TYPE_PLAYTECH_RNG = 13;
    public static final  int PRODUCT_TYPE_PLAYTECH_CASINO = 14;
    public static final  int PRODUCT_TYPE_BBIN_CASINOE = 16;
    public static final  int PRODUCT_TYPE_EAN2_LIVE = 17;
    public static final  int PRODUCT_TYPE_QTECH = 18;
    public static final  int PRODUCT_TYPE_DFSTARS = 19;
    public static final  int PRODUCT_TYPE_OG_LIVECASINO = 20;
}

