<?php
session_start();
header("Content-type: text/html; charset=utf-8");
include('sendsms.php');
$vcode=$_POST['vcode'];
if($vcode=='' || $vcode != $_SESSION['vcode'])
{
   echo '-1';
}
else
{
  include_once('../conn/conn.php');
  $phone=$_POST['number'];
  $sql="select uid from user where tell = '$phone' ";
  $rs=mysql_query($sql,$conn);
  if(mysql_fetch_row($rs))
  {
    echo '0';
  }
  else
  {
    $sms=new SendSMS($phone);
    $sms->setTem_id('91548316');
    $num=rand(1000,9999);
    $renderParams = array('code' =>$num, 'time' =>5); 
    $sms->setTemplateParams($renderParams);
    $re = $sms->send();
    if(!$re->res_code)
    {
       $_SESSION['phone']=$phone;
       $_SESSION['tellcode']=$num;
file_put_contents("1.txt",$num);
      echo '1';
    }
    else
      echo $re->res_message;
  }
  mysql_close($conn);
}

?>

