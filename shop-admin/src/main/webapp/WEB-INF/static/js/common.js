/**
 * 日期格式化
 * @param time
 * @returns {*}
 */
var formatDate = function(time) {
    if (time == '' || time == null || time == undefined) return '';
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
    var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
    var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
    var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
    var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
    return year + "年" + month + "月" + date + "日 " + hour + ":" + minute + ":" + second;
}

var isMobile = function(mobile){
    var ab = /^1[3|4|5|7|8][0-9]\d{8}$/;
    return ab.test(mobile);
}

/**
 * 将数值四舍五入(保留2位小数)后格式化成金额形式
 *
 * @param num
 *            数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.45'
 * @type String
 */
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ','
            + num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

/**
 * 将数值四舍五入(保留1位小数)后格式化成金额形式
 *
 * @param num
 *            数值(Number或者String)
 * @return 金额格式的字符串,如'1,234,567.4'
 * @type String
 */
function formatCurrencyTenThou(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 10 + 0.50000000001);
    cents = num % 10;
    num = Math.floor(num / 10).toString();
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ','
            + num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

function formatBankNo(BankNo) {
    if (BankNo == "")
        return;
    var account = new String(BankNo);
    account = account.substring(0, 22); /* 帐号的总数, 包括空格在内 */
    if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
        /* 对照格式 */
        if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
            var accountNumeric = accountChar = "", i;
            for (i = 0; i < account.length; i++) {
                accountChar = account.substr(i, 1);
                if (!isNaN(accountChar) && (accountChar != " "))
                    accountNumeric = accountNumeric + accountChar;
            }
            account = "";
            for (i = 0; i < accountNumeric.length; i++) { /* 可将以下空格改为-,效果也不错 */
                if (i == 4)
                    account = account + " "; /* 帐号第四位数后加空格 */
                if (i == 8)
                    account = account + " "; /* 帐号第八位数后加空格 */
                if (i == 12)
                    account = account + " ";/* 帐号第十二位后数后加空格 */
                account = account + accountNumeric.substr(i, 1)
            }
        }
    } else {
        account = " " + account.substring(1, 5) + " "
            + account.substring(6, 10) + " " + account.substring(14, 18)
            + "-" + account.substring(18, 25);
    }
    if (account != BankNo)
        BankNo = account;
    return BankNo;
}

function formatBankNoAndProtect(BankNo) {
    if (BankNo == "")
        return;
    var account = new String(BankNo);
    account = account.substring(0, 22); /* 帐号的总数, 包括空格在内 */
    if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
        /* 对照格式 */
        if (account.match(".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}|"
                + ".[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{7}") == null) {
            var accountNumeric = accountChar = "", i;
            for (i = 0; i < account.length; i++) {
                accountChar = account.substr(i, 1);
                if (!isNaN(accountChar) && (accountChar != " "))
                    accountNumeric = accountNumeric + accountChar;
            }
            account = "";
            for (i = 0; i < accountNumeric.length; i++) { /* 可将以下空格改为-,效果也不错 */
                if (i == 4)
                    account = account + " "; /* 帐号第四位数后加空格 */
                if (i == 8)
                    account = account + " "; /* 帐号第八位数后加空格 */
                if (i == 12)
                    account = account + " ";/* 帐号第十二位后数后加空格 */
                if (i > 3 && i < 12) {
                    account = account + "*"
                } else {
                    account = account + accountNumeric.substr(i, 1)
                }
            }
        }
    } else {
        account = " " + account.substring(1, 5) + " " + "*****" + " "
            + account.substring(14, 18) + "-" + account.substring(18, 25);
    }
    if (account != BankNo)
        BankNo = account;
    return BankNo;
}

function formatPayTypeName(payType){
    if(payType==10){
        return "银联快捷";
    }else if(payType==11){
        return "银联Wap(勇易)";
    }else if(payType==20){
        return "微信支付(APP)";
    }else if(payType==21){
        return "微信支付(二维码)";
    }else if(payType==22){
        return "微信支付(扫码)";
    }else if(payType==23){
        return "微信支付(公众号)";
    }else if(payType==24){
        return "微信支付(Wap)";
    }else if(payType==40){
        return "支付宝(APP)";
    }else if(payType==41){
        return "支付宝(二维码)";
    }else if(payType==42){
        return "支付宝(扫码)";
    }else if(payType==43){
        return "支付宝(服务窗)";
    }else if(payType==44){
        return "支付宝(Wap)";
    }else if(payType==50){
        return "MPOS";
    }else if(payType==51){
        return "MPOS(即富)";
    }else if(payType==52){
        return "大POS";
    }
}

function formatWithdrawTypeName(withdrawType){
    if(withdrawType==0){
        return "D0";
    }else if(withdrawType==1){
        return "T0";
    }else if(withdrawType==2){
        return "T1";
    }

}