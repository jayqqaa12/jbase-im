package com.jayqqaa12.im.gateway.support;

import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: 12
 * @create: 2019-09-11 14:39
 **/
@Component
@Slf4j
public class IpHepler {

    private static final String URL01 = "http://www.ip138.com/";
    private static final String URL02 = "https://ifconfig.co/ip";
    private static final String URL03 = "https://ipinfo.io/ip";


    @Autowired
    RedisTemplate redisTemplate;


    @Value("${tcp.socket.port}")
    Integer port;

    @Value("${domain:}")
    String domain;

    /**
     * 当前节点的ip
     */
    private String nodeIp;

    public String getNodeIp() {
        return nodeIp;
    }


    @PostConstruct
    public void init() {

        setNodeIp();

        if (StringUtils.isEmpty(domain)) domain = nodeIp;

    }


    public void setNodeIp() {

        loadIp1();

        if (nodeIp == null) loadIp2();
        if (nodeIp == null) loadIp3();
    }

    private void loadIp2() {

        try {

            String ss2 = HttpUtil.get(URL02).trim();
            if (orIp(ss2)) {
                this.nodeIp = ss2;
            }
        } catch (Exception e) {
            log.error("ip2 load error {}", e);
        }


    }

    private void loadIp3() {

        try {

            String ss2 = HttpUtil.get(URL03).trim();
            if (orIp(ss2)) {
                this.nodeIp = ss2;
            }
        } catch (Exception e) {
            log.error("ip2 load error {}", e);
        }


    }


    private void loadIp1() {

        try {

            String httpResponseStr = HttpUtil.get(URL01);
            String regex = "<\\s*iframe(.+?)src=[\"'](.*?)[\"']\\s*/?\\s*>";
            Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(httpResponseStr);
            String url = "";    //抓到可以使用的url动态地址
            while (matcher.find()) {
                String group1 = matcher.group(2);
                url = group1.substring(0, group1.indexOf("\""));
            }
            if (StringUtils.isNotBlank(url)) {
                String ss = HttpUtil.get(url);
                Pattern p = Pattern.compile("(\\[[^\\]]*\\])");
                Matcher m = p.matcher(ss);
                String ipStr = "";
                while (m.find()) {
                    ipStr = StringUtils.strip(m.group().substring(1, m.group().length() - 1), "[]");
                }
                if (orIp(ipStr)) {
                    this.nodeIp = ipStr;
                }
            }

        } catch (Exception e) {
            log.error("ip1 load error {}", e);
        }


    }

    /**
     * 判断是否是ip的格式
     *
     * @param ip
     * @return
     */
    public static boolean orIp(String ip) {
        if (ip == null || "".equals(ip))
            return false;
        String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        return ip.matches(regex);
    }

}
