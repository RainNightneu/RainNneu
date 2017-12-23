<?php
session_start();
header("Content-type: text/html; charset=utf-8"); 

function calculate_owner($ownertell)
{
	$own_order_arr=array();
	include("../conn/conn.php");
	$sql_owner="select * from bikeorder where owner='$ownertell'";
	$result_owner=mysql_query($sql_owner);
	$finished_order=0;
	$accept_rate=0.0;
	$total_order=0;
	$wait_time=0;
	$k=0;
	//$total_money=0;
	$total_profit=0;
	//$accept_time=;
	while($rs_owner=mysql_fetch_assoc($result_owner))
	{
		$total_order++;
		if($rs_owner['ordercondition']==3)
		{
			$finished_order++;
			$total_profit+=$rs_owner['money'];
		}
		if($rs_owner['ordercondition']!=1 && $rs_owner['ordercondition']!=0)
		{
			$accept_rate++;
		}
		if($rs_owner['ordercondition']==2 || ($rs_owner['ordercondition']==0 && $rs_owner['mark']==1) || $rs_owner['ordercondition']==3)
		{
			if($rs_owner['handletime']!=null)
			{
				$k++;
				$handletime=$rs_owner['handletime'];
				
				$ordertime=$rs_owner['ordertime'];
				
				$handletime=strtotime($handletime);
				$ordertime=strtotime($ordertime);
				$wait_time+=($handletime-$ordertime);
		
			}
			
		}
	}
	if($k==0)
	{
		$k=1;
	}

	$wait_time=$wait_time/$k;
	//$wait_time=date("Y-m-d h:i:s", $wait_time);
	//$wait_days=$wait_time/86400;
	$wait_hours=intval($wait_time/3600);
	$wait_minites=intval(($wait_time%3600)/60);
	$wait_seconds=$wait_time-$wait_hours*3600-$wait_minites*60;

	$wait_time_str=$wait_hours."小时".$wait_minites."分";


	if($total_order==0)
	{
		$total_order=1;
	}

	$own_order_arr[0]=$finished_order;
	$own_order_arr[1]=intval(($accept_rate/$total_order)*100)."%";
	$own_order_arr[2]=$wait_time_str;
	$own_order_arr[3]="&yen;".$total_profit;
	//$own_order_arr[4]=$total_profit;
	return $own_order_arr;

}

if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$owner_order_array=calculate_owner($username);

	include('../conn/conn.php');
	$sql="select * from user where tell='$username' ";
	$result=mysql_query($sql);
	$row=mysql_fetch_assoc($result);
	switch ($row['mark']) {
		//未确认
		case '1':
			$infocondition = "<label style='color:gray;font-size:14px;font-weight:400;line-height:30px;vertical-align: top;'>尚未认证</label>";
			break;
		case '3':
			$infocondition = "<label style='color:#ff9600;font-size:14px;font-weight:400;line-height:30px;vertical-align: top;'>等待审核</label>";
			break;
		case '6':
			$infocondition = "<label style='color:green;font-size:14px;font-weight:400;line-height:30px;vertical-align: top;'>通过审核</label>";
			break;
		case '9':
			$infocondition = "<label style='color:red;font-size:14px;font-weight:400;line-height:30px;vertical-align: top;'>未通过审核，请重新上传</label>";
			break;
		default:
			$infocondition = "<label style='color:#ff9600;font-size:14px;font-weight:400;line-height:30px;vertical-align: top;'>等待cto啦啦啦</label>";
			break;
	}
	mysql_close($conn);


	
	include('selfcenter.html');
}
else
{
	echo "<script>alert('请您先登录');window.location.href='../login.html';</script>";
}

?>