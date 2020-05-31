package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *������AlipayConfig
 *���ܣ�����������
 *��ϸ�������ʻ��й���Ϣ������·��
 *�޸����ڣ�2017-04-05
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ���������룬�̻����Ը����Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */

public class AlipayConfig {

//�����������������������������������Ļ�����Ϣ������������������������������

    // Ӧ��ID,����APPID���տ��˺ż�������APPID��Ӧ֧�����˺�
    public static String app_id = "2016102400753267";

    // �̻�˽Կ������PKCS8��ʽRSA2˽Կ
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvwjjxPiOqUzgDHkYblkLBQEG9EnNJb0SKLdTw6L7ZO4TLK3rZKkcTeZj/DXp5irm3EzlAiaRPiVc+sADx+BJiPVZ8mpX7X6WXd7VHDGfwXJLxPsisPVUDNxM0jjb3ftDWtOtl1Q/NZ2CpNa9BKE2L/sjJ8NrjdddhU4RMHtq4mZGuK5VkHEUgS+X+3ieVv7JHvaib5vy7Qz9pvnKcAlhbwPRHMZG4YQCmz0kGigc+fr5fNp7mZD2TaTzzixJ4gKK6puEAqLgBSttBn/nmRildByEGGkzrU7YGIUKOz2mVU7rb0wkejcERgf7xHQko5rDMpWcnoQ51ZPstVuJ3kkaPAgMBAAECggEBAISW4J4dYop7fSXdEMZ+9J0U/ILGCHK9ndcf/CRBcLgwTl12z5ZEbpoigWjnoh1OqguSVP5615mo4khIPm5L1pKF81jMxZh8707036xZj4+kRrX3xysomZlzUumy34SWBrXyhiKsON+bvS/pb+yXmuihrJerO3Wc0H57orJKFHZtHe1kdRX6W40kzVDMA4ezSP2A8iBV0LSA/C5KFxhR/raA/+NZoJyYWWVdu5MApOAxpcTEiUW797iTGmbGTfjvyj8emHn7XBhSRolaZy1i0+9e8581747HaZMaO8BpLI+JpKNhVDTVGykq5rExOiB3nM0V9GwK6ekQMa1KZ8QYEgECgYEA/8/EivYr3YJYXeRYoUBx2ydIiRowZwB2Q2eMk9/p2rq7faLcSiHhwvfX7n7wicTCGvYuKkxW7Q0J9oqp+l/Uo6zUqe+OC1hFyfyGQgAacNet0JC1OyzwjrH/8w4NIY/DxiBert2jRQnikfIF1VyCYBrTmtnAnB3DjrBMoBGTHtkCgYEAr+NcbGJtFTozWmzoLJb3l5QPzVCgSxz3e1l76H+R05TC50rkFsIcib/pwv9AY0qQCQgMMZuEIbE7OIXfVGnf17T2YVuQiRlz7IISV5bqmHKHB+SxYOwAG21L0UU/pViga8zSWHtbvNNlrndteKLgRRJSs5rtwRwtIfA4DE53/6cCgYEAsKEBwT1WblzorPPU6oo30y0dnWTS1NvxzHq8CwKJUviqKoZ/+kPIwTkmBEvrxARyTfF7UMNjJcxh+7Qkx3bRuK3GLQv2FQk/LsdI4Ft35kBViHPSXuiZtL1cz7L9CBZfA/cnyS/J4wp/QWxN8NMuRjNftkTyBBldBLHgRom7/okCgYAxRZjjh+MU+jvejfmwXA45+TFAC+ELia7OcC/k3q/EPL0ou/MPaQtDj251wLmR1xZoYUfBMdae9Z4oZGA+rnYVYE1Q0qy3q1itHXbz8IQBdrva+zaxdwtEo6aElkiREjXGXRdj0BLP38xxRmV1UPNQhiSCydjmqvT9t+SLlgPJqwKBgBLJqaGuTjKpnCul7A+0o2rh7TMsg1A/SsqhIzoSMeWWLcqo3XKzQBre/BTGBKbbhPmnvi8kSSeENVUDHFHvhKPrcLIEu5eQlSXYzQ+sAfOsfwdP0+5CLXP1P1m0Ln11Ay6u1TMBA8r6YZQHG8bruhCc4vOtdSaM91sd2prkoCPy";

    // ֧������Կ,�鿴��ַ��https://openhome.alipay.com/platform/keyManage.htm ��ӦAPPID�µ�֧������Կ��
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuEH+C5NST5YtB9au2jM9oVDN+4fDWveFnfkFRdxSNy0mNmoFTnX313c8hiwvTD6RCp67rzYDLRFHLdVFz0+C5AK2drc2vewQhP58CoDJVnPexdMY6W4JEIhWDjX1XLKC69RCCHg6bWWs27YWFxtjj+pKRypACHsd1zxxmy2xYdyoZ7MfhKSgNnat5mMScQpmin9/bjFQBX7DrUSksjdAOe+QYPSDXI4WxIgLrG92m7Sy3t9veZQm6GsKqL5669OKurHujffEGOUZ/6Eps8Mw80mgV+/kn+WXLyfT31kx7FRe8Q/G1dUdgnwR5zIMgunvS1sNnWmUWJKrNPI1dbPpLQIDAQAB";

    // �������첽֪ͨҳ��·��  ��http://��ʽ������·�������ܼ�?id=123�����Զ����������������������������
    public static String notify_url = "http://mountaintom.uicp.net/EthanPetStoreArchive/index/orderAlipayNotify";

    // ҳ����תͬ��֪ͨҳ��·�� ��http://��ʽ������·�������ܼ�?id=123�����Զ����������������������������
    public static String return_url = "http://mountaintom.uicp.net/EthanPetStoreArchive/index/return_url.jsp";

    // ǩ����ʽ
    public static String sign_type = "RSA2";

    // �ַ������ʽ
    public static String charset = "utf-8";

    // ֧��������
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // ֧��������
    public static String log_path = "C:\\";


//�����������������������������������Ļ�����Ϣ������������������������������

    /**
     * д��־��������ԣ�����վ����Ҳ���ԸĳɰѼ�¼�������ݿ⣩
     * @param sWord Ҫд����־����ı�����
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

