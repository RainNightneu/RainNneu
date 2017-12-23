<?php
session_start();
if(isset($_SESSION['admin']) && $_SESSION['admin']!='')
{
	include("../sendsms/sendsms.php");
	include('../conn/conn.php');
	$ope=$_POST['ope'];
	$oid=$_POST['oid'];
	if($ope==0)
	{
		//确认订单
		$sql="update bikeorder set ordercondition=2 where oid='$oid' ";
		if($result=mysql_query($sql))
		{
			echo '0';
		}
		else
		{
			echo 'Failed! Please contact the CTO';
		}
	}
	else
	{
		$sql="update bikeorder set ordercondition=0,mark=1 where oid='$oid' ";
		if($result=mysql_query($sql))
		{
			//发短信提示重新提交
			echo '0';
		}
		else
		{
			echo 'Failed! Please contact the CTO';
		}
	}
}
else
{
	echo "不要瞎访问！";
}

?>