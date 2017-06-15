"use strict";
var page = require('webpage').create();

phantom.addCookie(
    [{
        "domain": "10.92.8.18",
        "expires": "周四, 22 六月 2017 01:43:03 GMT",
        "expiry": 1498095783,
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
        "value": "lyv1hvvivuy815u8kxoccxd5o"
    }]
);

page.open("http://10.92.8.18:9999/summary/?r=bi/loong.git", function (status) {
    if (status === "success") {
        console.log(page.content)
        phantom.exit(0)
    } else {
        phantom.exit(1)
    }
});
