import com.csair.csairmind.hunter.common.util.JsonUtil;

import java.util.Map;

/**
 * Created by zhangcheng on 2017/5/23 0023.
 */
public class JsonTest {
    public static void main(String[] args) {
        String str ="data_callback(\n" +
                "        {\n" +
                "            \"title\": \"贾庆林在中南海见了几位客人 谈到了这件大事\", \n" +
                "            \"digest\": \"\", \n" +
                "            \"docurl\": \"http://news.163.com/17/0523/14/CL4LJ38S0001875N.html\", \n" +
                "            \"commenturl\": \"http://comment.news.163.com/news_guonei8_bbs/CL4LJ38S0001875N.html\", \n" +
                "            \"tienum\": 0, \n" +
                "            \"tlastid\": \"\", \n" +
                "            \"tlink\": \"http://news.163.com/17/0523/14/CL4LJ38S0001875N.html\", \n" +
                "            \"label\": \"其它\", \n" +
                "            \"keywords\": [\n" +
                "                {\n" +
                "                    \"akey_link\": \"http://news.163.com/keywords/8/3/8d3e5e866797/1.html\", \n" +
                "                    \"keyname\": \"贾庆林\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"akey_link\": \"http://news.163.com/keywords/4/2/4e2d592e653f6cbb5c40/1.html\", \n" +
                "                    \"keyname\": \"中央政治局\"\n" +
                "                }\n" +
                "            ], \n" +
                "            \"time\": \"05/23/2017 14:51:42\", \n" +
                "            \"newstype\": \"article\", \n" +
                "            \"pics3\": [ ], \n" +
                "            \"channelname\": \"guonei\", \n" +
                "            \"imgurl\": \"http://cms-bucket.nosdn.127.net/4b93bd3468f94ef196552618e55ec53920170523145118.png\", \n" +
                "            \"add1\": \"\", \n" +
                "            \"add2\": \"\", \n" +
                "            \"add3\": \"\"\n" +
                "        }, \n" +
                "        {\n" +
                "            \"title\": \"门头沟区发生2.6级地震 北京地震局:系塌陷震动\", \n" +
                "            \"digest\": \"\", \n" +
                "            \"docurl\": \"http://news.163.com/17/0523/14/CL4LE5T7000187VE.html\", \n" +
                "            \"commenturl\": \"http://comment.news.163.com/news2_bbs/CL4LE5T7000187VE.html\", \n" +
                "            \"tienum\": 0, \n" +
                "            \"tlastid\": \"\", \n" +
                "            \"tlink\": \"http://news.163.com/17/0523/14/CL4LE5T7000187VE.html\", \n" +
                "            \"label\": \"其它\", \n" +
                "            \"keywords\": [\n" +
                "                {\n" +
                "                    \"akey_link\": \"http://news.163.com/keywords/9/e/95e859346c9f533a/1.html\", \n" +
                "                    \"keyname\": \"门头沟区\"\n" +
                "                }, \n" +
                "                {\n" +
                "                    \"akey_link\": \"http://news.163.com/keywords/5/3/57309707/1.html\", \n" +
                "                    \"keyname\": \"地震\"\n" +
                "                }\n" +
                "            ], \n" +
                "            \"time\": \"05/23/2017 14:44:26\", \n" +
                "            \"newstype\": \"article\", \n" +
                "            \"pics3\": [ ], \n" +
                "            \"channelname\": \"guonei\", \n" +
                "            \"imgurl\": \"http://cms-bucket.nosdn.127.net/c36f7392531a4b9aa9b3477e58f3e08c20170523145113.png\", \n" +
                "            \"add1\": \"\", \n" +
                "            \"add2\": \"\", \n" +
                "            \"add3\": \"\"\n" +
                "        }\n" +
                "    \n" +
                ")";

        System.out.println(JsonUtil.toBean(str, Map.class));
    }
}
