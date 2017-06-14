"use strict";
var page = require('webpage').create();

phantom.addCookie([{
    "domain": "10.92.8.18",
    "expires": "周二, 20 六月 2017 08:26:25 GMT",
    "expiry": 1497947185,
    "httponly": false,
    "name": "Gitblit",
    "path": "/",
    "secure": false,
    "value": "59b8430eb7a859e613698ae9b19c2c4ad017a498"
}, {
    "domain": "10.92.8.18",
    "httponly": true,
    "name": "JSESSIONID",
    "path": "/",
    "secure": false,
    "value": "7y5h131b3revwlovxmmat43u"
}]);

page.open("http://10.92.8.18:9999/blobdiff/bi%2Fhunter.git/461df548151133b60d187ad17513aaa5533e488a/hunter-service%2Fsrc%2Fmain%2Fjava%2Fcom%2Fcsair%2Fcsairmind%2Fhunter%2Fservice%2Frequest%2FMachineInfoRequest.java?hb=b04790e4730fc34b4652eab34f01e3b4902a60b3", function (status) {
    if (status === "success") {
        console.log(page.content)
        phantom.exit(0)
    } else {
        phantom.exit(1)
    }
});
