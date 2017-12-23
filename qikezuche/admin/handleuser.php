<?php
session_start();
if(isset($_SESSION['admin']) && $_SESSION['admin']!='')
{
	include("../sendsms/sendsms.php");
	include('../conn/conn.php');
	$ope=$_POST['ope'];
	$tell=$_POST['tell'];
	$time=date('Y-m-d H:i:s');
	if($ope==0)
	{
		//通过审核
		$sql="update user set mark=6,confirm_time='$time' where tell='$tell' ";
		if($result=mysql_query($sql))
		{
			//发短信提示成功
			//$send=new SendSMS($tell)
			//$send.setTem_id()
			echo '0';
		}
		else
		{
			echo 'Failed! Please contact the CTO';
		}
	}
	else
	{
		$sql="update user set mark=9 where tell='$tell' ";
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