// Empty constructor
function ussd() {
}

// The function that passes work along to native shells
// Message is a string, duration may be 'long' or 'short'
ussd.prototype.show = function (ussdCode, successCallback, errorCallback) {
    var options = {};
    options.ussdCode = ussdCode;
    //options.duration = duration;
    cordova.exec(successCallback, errorCallback, 'VoIpUSSD', 'show', [options]);
}

ussd.prototype.show_test = function (ussdCode, successCallback, errorCallback) {
    var options = {};
    options.ussdCode = ussdCode;
    //options.duration = duration;
    cordova.exec(successCallback, errorCallback, 'VoIpUSSD', 'show_test', [options]);
}

ussd.prototype.sendText = function (text, successCallback, errorCallback) {
    var options = {};
    options.text = text;
    //options.duration = duration;
    cordova.exec(successCallback, errorCallback, 'VoIpUSSD', 'send_text', [options]);
}

ussd.prototype.ciTest = function (ussdCode, successCallback, errorCallback) {
    var options = {};
    options.ussdCode = ussdCode;
    //options.duration = duration;
    cordova.exec(successCallback, errorCallback, 'VoIpUSSD', 'ci_test', [options]);
}

// Installation constructor that binds voIpUSSD to window
ussd.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.voIpUSSD = new ussd();
    return window.plugins.voIpUSSD;
};
cordova.addConstructor(ussd.install);
