<?php
session_start();
header("Content-type: text/html; charset=utf-8");  
include_once("conn/conn.php");
$name=$_POST['username'];
$password=$_POST['password'];
$vcode=strtolower($_POST['vcode']);

if($vcode != $_SESSION['vcode'])
{
	echo '-1';
}
else
{
	$sql="select uid from user where tell='$name' and password='$password'";
	$rs=mysql_query($sql,$conn);
	$re=mysql_fetch_row($rs);
	if(!$re)
	{

		echo '0';
	}
	else
	{
		$_SESSION['phone']=$name;
		$_SESSION['password']=$password;

		$sql_1="select * from user where tell='$name' ";
		$resul_1=mysql_query($sql_1);
		$re_1=mysql_fetch_assoc($resul_1);

		$_SESSION['name']=$re_1['username'];
		echo '1';
	}
}

mysql_close($conn);
?>
