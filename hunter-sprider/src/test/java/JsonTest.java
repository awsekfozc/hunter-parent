import com.csair.csairmind.hunter.common.util.JsonUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 */
public class JsonTest {
    public static void main(String[] args) {
        String s = "data_callback([\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                                \n" +
                "                             \n" +
                "                                            \n" +
                "                {\n" +
                "        \"title\":\"证监会处罚书披露慧球违法细节:动机实为争控制权\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/15/CL4OGMLN0001875N.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news_guonei8_bbs/CL4OGMLN0001875N.html\",\n" +
                "        \"tienum\":0,\n" +
                "        \"tlastid\":\"<a href='http://news.163.com/'>新闻</a>\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/15/CL4OGMLN0001875N.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/8/c/8bc176d14f1a/1.html\",\"keyname\":\"证监会\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/6/6/6167740379d16280/1.html\",\"keyname\":\"慧球科技\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/5/3/533951f85339/1.html\",\"keyname\":\"匹凸匹\"}\n" +
                "                                                                                        ],\n" +
                "        \"time\":\"05/23/2017 15:42:49\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                                \n" +
                "                             \n" +
                "                                            \n" +
                "                ,\n" +
                "        {\n" +
                "        \"title\":\"聂卫平:柯洁与阿法狗非一档次 AI几步棋出乎意料\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/15/CL4OMK4D000187VE.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news2_bbs/CL4OMK4D000187VE.html\",\n" +
                "        \"tienum\":0,\n" +
                "        \"tlastid\":\"<a href='http://news.163.com/'>新闻</a>\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/15/CL4OMK4D000187VE.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/8/4/8042536b5e73/1.html\",\"keyname\":\"聂卫平\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/9/3/963f5c146cd572d7/1.html\",\"keyname\":\"阿尔法狗\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/6/e/67ef6d01/1.html\",\"keyname\":\"柯洁\"}\n" +
                "                                                                                        ],\n" +
                "        \"time\":\"05/23/2017 15:39:00\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"http://cms-bucket.nosdn.127.net/8b27d603efec4de4a014900781558b8220170523155201.png\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                                \n" +
                "                             \n" +
                "                                            \n" +
                "                ,\n" +
                "        {\n" +
                "        \"title\":\"河北冀中能源集团原董事长王社平被＂双开＂\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/15/CL4NR4AF0001899N.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news2_bbs/CL4NR4AF0001899N.html\",\n" +
                "        \"tienum\":0,\n" +
                "        \"tlastid\":\"<a href=/domestic/>国内新闻</a>\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/15/CL4NR4AF0001899N.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/5/8/51804e2d80fd6e90/1.html\",\"keyname\":\"冀中能源\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/7/8/738b793e5e73/1.html\",\"keyname\":\"王社平\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/5/c/53cc5f00/1.html\",\"keyname\":\"双开\"}\n" +
                "                                                    ],\n" +
                "        \"time\":\"05/23/2017 15:31:02\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"http://cms-bucket.nosdn.127.net/d635f42cd32245eeaf5ad08e223ca33220170523153041.png\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                                \n" +
                "                             \n" +
                "                                            \n" +
                "                ,\n" +
                "        {\n" +
                "        \"title\":\"共享单车数量过剩?交通部:不控总量 鼓励自行管理\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/15/CL4NLK7N000187VE.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news2_bbs/CL4NLK7N000187VE.html\",\n" +
                "        \"tienum\":0,\n" +
                "        \"tlastid\":\"<a href='http://news.163.com/'>新闻</a>\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/15/CL4NLK7N000187VE.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/5/7/51714eab53558f66/1.html\",\"keyname\":\"共享单车\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/4/a/4ea4901a90e8/1.html\",\"keyname\":\"交通部\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/6/7/657091cf8fc75269/1.html\",\"keyname\":\"数量过剩\"}\n" +
                "                                                                                        ],\n" +
                "        \"time\":\"05/23/2017 15:17:00\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"http://cms-bucket.nosdn.127.net/26daaa4db12a4a748f51ea9e37e7bc1120170523153530.png\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                    \n" +
                "                             \n" +
                "            \n" +
                "                ,\n" +
                "        {\n" +
                "        \"title\":\"贾庆林在中南海见了几位客人 谈到了这件大事\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/14/CL4LJ38S0001875N.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news_guonei8_bbs/CL4LJ38S0001875N.html\",\n" +
                "        \"tienum\":7,\n" +
                "        \"tlastid\":\"\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/14/CL4LJ38S0001875N.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/8/3/8d3e5e866797/1.html\",\"keyname\":\"贾庆林\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/4/2/4e2d592e653f6cbb5c40/1.html\",\"keyname\":\"中央政治局\"}\n" +
                "                                                                                                                            ],\n" +
                "        \"time\":\"05/23/2017 14:51:42\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"http://cms-bucket.nosdn.127.net/4b93bd3468f94ef196552618e55ec53920170523145118.png\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                    \n" +
                "                             \n" +
                "            \n" +
                "                ,\n" +
                "        {\n" +
                "        \"title\":\"门头沟区发生2.6级地震 北京地震局:系塌陷震动\",\n" +
                "        \"digest\":\"\",\n" +
                "        \"docurl\":\"http://news.163.com/17/0523/14/CL4LE5T7000187VE.html\",\n" +
                "        \"commenturl\":\"http://comment.news.163.com/news2_bbs/CL4LE5T7000187VE.html\",\n" +
                "        \"tienum\":803,\n" +
                "        \"tlastid\":\"\",\n" +
                "        \"tlink\":\"http://news.163.com/17/0523/14/CL4LE5T7000187VE.html\",\n" +
                "        \"label\":\"其它\",\n" +
                "        \"keywords\":[\n" +
                "                                                                                                                        {\"akey_link\":\"http://news.163.com/keywords/9/e/95e859346c9f533a/1.html\",\"keyname\":\"门头沟区\"}\n" +
                "                                                                                                        ,\n" +
                "                                                                    {\"akey_link\":\"http://news.163.com/keywords/5/3/57309707/1.html\",\"keyname\":\"地震\"}\n" +
                "                                                                                                                            ],\n" +
                "        \"time\":\"05/23/2017 14:44:26\",\n" +
                "        \"newstype\":\"article\",\n" +
                "        \"pics3\":[\n" +
                "                    ],\n" +
                "        \"channelname\":\"guonei\",\n" +
                "                    \"imgurl\":\"http://cms-bucket.nosdn.127.net/c36f7392531a4b9aa9b3477e58f3e08c20170523145113.png\",\n" +
                "            \"add1\":\"\",\n" +
                "            \"add2\":\"\",\n" +
                "            \"add3\":\"\"\n" +
                "            }\n" +
                "                    \n" +
                "                        \n" +
                "                                                                                    \n" +
                "                             \n" +
                "            ";
// 把要匹配的字符串写成正则表达式，然后要提取的字符使用括号括起来
// 在这里，我们要提取最后一个数字，正则规则就是“一个数字加上大于等于0个非数字再加上结束符”
        String rule = "http://news.163.com/(\\d+)/(\\d+)/(\\d+)/(\\w+).html";
        System.out.println(s.matches(rule));
        Pattern pattern = Pattern.compile(rule);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
