<?php
session_start();
header("Content-type: text/html; charset=utf-8");
//include_once('conn/conn.php');


$phone=$_POST['phone'];
$vcode=$_POST['vcode'];
$password=$_POST['password'];
if($phone != $_SESSION['phone'] || $vcode != $_SESSION['tellcode'])
{
   echo '手机或验证码错误！';
}
else
{
  include_once('conn/conn.php');
  $sql="update user set password='$password' where tell = '$phone' ";
  $rs=mysql_query($sql);
  if($rs)
  {
        $_SESSION['tellcode']=0;
	$sql_1="select * from user where tell='$phone' ";
	$resul_1=mysql_query($sql_1);
	$re_1=mysql_fetch_assoc($resul_1);
	$_SESSION['name']=$re_1['username'];

        echo '1';
  }
  else
  {
    echo '修改失败，请重试！';
  }
  mysql_close($conn);
}

?>

