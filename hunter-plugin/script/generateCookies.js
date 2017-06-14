/***
 * phantomjs方式生成cookies
 * generateCookies.js http://weibo.com/login.php #loginname input[name='password'] 18007303287 qwertsekfo1 span[node-type='submitStates']
 */
var page = require('webpage').create()
var system = require('system')
var url = system.args[1]
var username_position = system.args[2]
var pwd_position = system.args[3]
var username = system.args[4]
var pwd = system.args[5]
var sub_position = system.args[6]
if (system.args.length < 5) {
    console.log("错误的参数");
    phantom.exit()
} else {
    // console.log("url=" + url);
    // console.log("username_position=" + username_position);
    // console.log("pwd_position=" + pwd_position);
    // console.log("username=" + username);
    // console.log("pwd=" + pwd);
    // console.log("sub_position=" + sub_position);
    // console.log("sub_position=" + sub_position);
    page.open(url, function () {
        // console.log(page.url)
        // var c =page.cookies
        // for(var i in c) {
        //     console.log(c[i].name + '=' + c[i].value);
        // }
        page.includeJs("https://code.jquery.com/jquery-3.2.1.min.js", function () {
            page.evaluate(function (username_position,pwd_position,username,pwd,sub_position) {
                $(username_position).val(username)
                $(pwd_position).val(pwd)
                console.log("1="+$(username_position))
                $(sub_position).click()
            },username_position,pwd_position,username,pwd,sub_position);
            setTimeout('print_cookies()', 2000)
            console.log("2="+$(username_position))
        });
    });
}
function print_cookies() {
    // console.log(page.url)
    // var c =page.cookies
    // for(var i in c) {
    //     console.log(c[i].name + '=' + c[i].value);
    // }
    console.log(JSON.stringify(page.cookies));
    phantom.exit()
}