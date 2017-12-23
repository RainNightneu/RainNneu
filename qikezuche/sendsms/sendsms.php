<?php

Class SendSMS
{
  private $app_id = '741853820000245471';// 应用ID
  private $app_secret = '4d603926dca576d27f217c638b005625';// 应用的密钥
  private $timestamp=''; //时间戳，格式为：yyyy-MM-dd hh:mm:ss
  public $phone='';
  public $template_id='91548316';//短信模板id
  private $template_params=array();//短信模板要渲染的参数
  private $grant_type='client_credentials'; //Client Credentials授权模式
  private $access_token_url = 'https://oauth.api.189.cn/emp/oauth2/v3/access_token';// 获取access_token的URL
  private $send_url = 'http://api.189.cn/v2/emp/templateSms/sendSms';// 发送短信接口请求的诋地址

  //构造方法 new的时候会执行
  public function __construct($phone){
    $this->checkPhone($phone); //检测手机号码合法性
    date_default_timezone_set('Asia/Shanghai'); //设置时区，避免8小时的时间差
    $this->timestamp = date('Y-m-d H:i:s');
  }

  //检查号码合法性
 public function setTem_id($id)
 {
  $this->template_id=$id;
 }

  private function checkPhone($phone)
  {
    if (preg_match('/^(1[0-9]{10})$/',$phone)){
      $this->phone=$phone;
    }
    else{
      echo '手机号码格式不正确。';exit;
    }
  }

  //获取模板参数
  private function getTemplateParams() {
  	return json_encode($this->template_params);
  }

  //设置渲染模板用的参数
  public function setTemplateParams($params =array()) {
  	$this->template_params = $params;
  }

  //获取access_token
  private function getAccessKey() {
    $param['grant_type']= "grant_type=".$this->grant_type;
    $param['app_id'] = "app_id=".$this->app_id;
    $param['app_secret'] = "app_secret=".$this->app_secret;
    $plaintext = implode("&",$param);
    $result = $this->curl_post($this->access_token_url,$plaintext);
    $obj = json_decode($result);
    return $obj->access_token;
  }

  //发送验证码方法
  public function send() 
  {
    $access_token=$this->getAccessKey();//准备access_token
    $param['app_id']= "app_id=".$this->app_id;
    $param['access_token'] = "access_token=".$access_token;
    $param['acceptor_tel'] = "acceptor_tel=".$this->phone;//接收方电话号码
    $param['template_id'] = "template_id=".$this->template_id;//短信模板id--------------------
    $param['template_param'] = "template_param=".$this->getTemplateParams();//拼接渲染模板参数
    $param['timestamp'] = "timestamp=".$this->timestamp;

    ksort($param);//按照key排序

    $plaintext = implode("&",$param);

    $param['sign'] = "sign=".rawurlencode(base64_encode(hash_hmac("sha1", $plaintext, $this->app_secret, $raw_output=True)));//生成sign参数

    ksort($param);

    $str = implode("&",$param);

    $result = $this->curl_post($this->send_url,$str);//开始请求目的地地址，发送短信

    $obj = json_decode($result); 
    return $obj;//返回地址的结果
  }

  //cURL库抓网页,模拟post请求
  private function curl_post($url='', $postdata=''){
    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, FALSE);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, FALSE);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
    curl_setopt($ch, CURLOPT_POST, 1);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $postdata);
    curl_setopt($ch, CURLOPT_TIMEOUT, 10);
    $data = curl_exec($ch);
    curl_close($ch);
    return $data;
  }
}
?>


