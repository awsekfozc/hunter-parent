# 分布式爬虫MD

> hunter分布式爬虫系统，主要目的是开发一个可配置的，可管可控的，支持通用，支持特殊定制的爬虫系统


## 爬虫技术结构

![image_1bjp7fktl12io1f951k321k4i2ju9.png-34.2kB][1]


## 爬虫功能模块结构图

![image_1bj4kpkid1v2a1nng1pk7sf91mv09.png-32.9kB][2]

各模块主要功能如下：
<table>
<tr>
 <th>模块</th>
 <th>名称</th>
 <th>主要功能</th>
 <th>备注</th>
</tr>
<tr>
 <td rowspan="6">爬虫管理中心</td>
 <td rowspan="6">hunter-master</td>
 <td>爬虫客户端注册</td>
 <td>爬虫客户端向集群注册时调用的接口</td>
</tr>
<tr>
 <td>接受爬虫客户端心跳</td>
 <td>爬虫客户端向集群报告状态时调用的接口</td>
</tr>
<tr>
 <td>爬虫集群状态监控</td>
 <td>管理中心需要定时检查集群各个节点的状态信息</td>
</tr>
<tr>
 <td>集群日志管理</td>
 <td></td>
</tr>
<tr>
 <td>处理申请爬虫任务的请求</td>
 <td>爬虫客户端向集群申请任务时调用的接口</td>
</tr>
<tr>
 <td>处理数据统计上报</td>
 <td>爬虫客户端爬取到数据后报告给集群</td>
</tr>
<tr>
 <td rowspan="5">爬虫客户端</td>
 <td rowspan="5">hunter-client</td>
 <td>向管理中心注册</td>
 <td>向集群发起注册请求</td>
</tr>
<tr>
 <td>向管理中心发送心跳</td>
 <td>向集群报告健康状况</td>
</tr>
<tr>
 <td>请求爬虫任务</td>
 <td>机器空闲时向集群请求任务</td>
</tr>
<tr>
 <td>日志处理</td>
 <td>爬虫运行过程中日志向集群报告，集群统一处理</td>
</tr>
<tr>
 <td>发送采集量到管理中心</td>
 <td>数据发送消息队列成功，向集群报数</td>
</tr>
<tr>
 <td rowspan="10">爬虫公用模块</td>
 <td rowspan="10">hunter-common</td>
 <td>实体类vo</td>
 <td>包含系统所有实体</td>
</tr>
<tr>
 <td>工具类utlis</td>
 <td></td>
</tr>
<tr>
 <td>数据库配置类config</td>
 <td>Mysql,redis,kafka配置类</td>
</tr>
<tr>
 <td>系统参数配置类config</td>
 <td>各种系统参数，如：心跳间隔，重新上线次数</td>
</tr>
<tr>
 <td>自定义异常exception</td>
 <td></td>
</tr>
<tr>
 <td>状态枚举enums</td>
 <td></td>
</tr>
<tr>
 <td>请求类request</td>
 <td></td>
</tr>
<tr>
 <td>响应类response</td>
 <td></td>
</tr>
<tr>
 <td>服务接口inf</td>
 <td></td>
</tr>
<tr>
 <td>常量参数constant</td>
 <td></td>
</tr>
<tr>
 <td>爬虫任务调度模块</td>
 <td>hunter-secheduler</td>
 <td>生成任务</td>
 <td>该模块主要用于生成种子爬虫任务，定时调度。生成任务分为两种类型，资源解析类和详情解析类</td>
</tr>
<tr>
 <td rowspan="5">爬虫任务处理模块</td>
 <td rowspan="5">hunter-spirder</td>
 <td>爬取网页</td>
 <td>根据配置信息爬取目标网页内容</td>
</tr>
<tr>
 <td>解析网页</td>
 <td>根据配置信息对网页做解析</td>
</tr>
<tr>
 <td>网页去重</td>
 <td>根据去重类型对爬取的内容去是否重复比较</td>
</tr>
<tr>
 <td>增量任务</td>
 <td>如果爬取的数据都是新数据就继续往后爬取，不能超过最大爬取页数</td>
</tr>
<tr>
 <td>数据持久化</td>
 <td>目前只有一种持久化插件：kafka可动态扩展</td>
</tr>
<tr>
 <td rowspan="5">爬虫服务模块</td>
 <td rowspan="5">hunter-service</td>
 <td>爬虫任务测试服务</td>
 <td></td>
</tr>
<tr>
 <td>爬虫任务提交</td>
 <td></td>
</tr>
<tr>
 <td>爬出任务停止</td>
 <td></td>
</tr>
<tr>
 <td>Cookie池所需账号密码测试</td>
 <td>提供测试账号密码有效性的功能</td>
</tr>
<tr>
 <td>获取集群节点信息</td>
 <td></td>
</tr>
<tr>
 <td rowspan="5">爬虫插件模块</td>
 <td rowspan="5">hunter-plugs</td>
 <td>Cookie池插件</td>
 <td>用于破解cookie反扒</td>
</tr>
<tr>
 <td>IP代理插件</td>
 <td>用于破解IP频率反扒</td>
</tr>
<tr>
 <td>自动登陆插件</td>
 <td>用于生成cookie池</td>
</tr>
<tr>
 <td>持久化插件</td>
 <td>用于保存数据，暂时只有kafka。可动态扩展</td>
</tr>
<tr>
 <td>去重插件</td>
 <td>数据去重的规则，暂时有：内容去重，URL去重。可动态扩展</td>
</tr>
</table>


## 爬虫技术结构图

![image_1bj4kqppd16961h7iiipd3t1k5b9.png-95.4kB][3]


---

分布式爬虫系统-hunter 305855967@qq.com

  [1]: http://static.zybuluo.com/awsekfozc/p315e726hcacndksw0c13z4f/image_1bjp7fktl12io1f951k321k4i2ju9.png
  [2]: http://static.zybuluo.com/awsekfozc/mpgivzndlzmulyjpjccilspg/image_1bj4kpkid1v2a1nng1pk7sf91mv09.png
  [3]: http://static.zybuluo.com/awsekfozc/pqv2ywx3ppt6trjdcxjkop8o/image_1bj4kqppd16961h7iiipd3t1k5b9.png
