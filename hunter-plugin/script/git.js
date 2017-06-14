"use strict";
var page = require('webpage').create();

page.onConsoleMessage = function (msg) {
    console.log(msg);
};

page.open("http://10.92.8.18:9999/", function (status) {
    if (status === "success") {
        page.includeJs("https://code.jquery.com/jquery-3.2.1.min.js", function () {
            page.evaluate(function () {
                $("input[name='username']").val('zhangcheng')
                $("input[name='password']").val('qwertsekfo1')
                $("button[type='submit']").click();
            });
        });
        setTimeout('print_cookies()', 2000)
    }
});
function print_cookies() {
    var cookies = page.cookies;
    console.log(JSON.stringify(cookies));
    phantom.exit()
}