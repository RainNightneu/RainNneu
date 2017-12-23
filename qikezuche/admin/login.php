<?php
session_start();
header("Content-type: text/html; charset=utf-8");  
include_once("../conn/conn.php");
$name=$_POST['username'];
$password=$_POST['password'];
	$sql="select uid from admin where username='$name' and password='$password'";
	$rs=mysql_query($sql);
	if(!mysql_fetch_row($rs))
	{
		echo '请不要尝试越俎代庖！';
	}
	else
	{
		$_SESSION['admin']=$name;
		$_SESSION['password']=$password;
		echo '1';
	}
mysql_close($conn);
?>
