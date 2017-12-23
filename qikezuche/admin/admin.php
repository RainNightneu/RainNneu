<?php
session_start();
if(isset($_SESSION['admin']) && $_SESSION['admin']!='')
{
	$perPage=20;
	include_once("../conn/conn.php");
	
	$yestoday_regist_num=0;
	$yestoday_confirm_num=0;
	$today_regist_num=0;
	$today_confirm_num=0;

	$time1= time();
	$time2=$time1-86400;
	$today=date("Y-m-d",$time1); //2010-08-29
	$yestoday=date("Y-m-d",$time2); 
	

	$sql_1="select * from user";
	$result_1=mysql_query($sql_1);
	while($rs=mysql_fetch_array($result_1))
	{	
		$arr1=explode(" ", $rs['regtime']);
		$arr2=explode(" ", $rs['confirm_time']);
		if($arr1[0]==$yestoday)
		{
			$yestoday_regist_num++;
		}
		if($arr1[0]==$today)
		{
			$today_regist_num++;
		}

		if($arr2[0]==$yestoday)
		{
			$yestoday_confirm_num++;
		}
		if($arr2[0]==$today)
		{
			$today_confirm_num++;
		}
	}


	$yestoday_order_num=0;
	$yestoday_order_deny=0;
	$today_order_num=0;
	$today_order_deny=0;

	$sql_2="select * from bikeorder";
	$result_2=mysql_query($sql_2);
	while($rs2=mysql_fetch_array($result_2))
	{	
		$arr1=explode(" ", $rs2['ordertime']);

		if($rs2['ordercondition']==0)
		{
			if($arr1[0]==$yestoday)
			{
				$yestoday_order_deny++;
			}
			if($arr1[0]==$today)
			{
				$today_order_deny++;
			}
		}


		if($arr1[0]==$yestoday)
		{
			$yestoday_order_num++;
		}
		if($arr1[0]==$today)
		{
			$today_order_num++;
		}
	}


	include("index.html");
	mysql_close($conn);
}
else
{
    include("./login.html");
}
?>