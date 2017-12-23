<?php 
session_start();
header("Content-type: text/html; charset=utf-8"); 
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
header("Cache-Control: no-cache, must-revalidate"); 
header("Pramga: no-cache");


function calculate_rest($start_time,$end_time,$bid)
{
	$time1=strtotime($start_time);
	$time2=strtotime($end_time);

	$rest_number=0;

	$time3=0;
	$time4=0;
	//获取自行车数   and (ordercondition!= '0' or ordercondition!= '3')
	require_once("../conn/conn.php");
	$sql="select bikenum from bike where bid='$bid'";
	$result=mysql_query($sql);
	$re=mysql_fetch_array($result);
	$rest_number=$re[0];

	$sql_1="select * from bikeorder where bid='$bid'";
	$result_1=mysql_query($sql_1);
	while($re_1=mysql_fetch_assoc($result_1))
	{
		if($re_1['ordercondition']==1 || $re_1['ordercondition']==2)
		{
			$time3=strtotime($re_1['starttime']);
			$time4=strtotime($re_1['endtime']);
			if(!($time2<$time3 || $time1>$time4))
			{
				$rest_number-=$re_1['bikenum'];
				//echo 'jaja';
			}
		}
	}

	return $rest_number;
}



function isFree($bid,$time1,$time2)
{
	$flag=true;
	include('../conn/conn.php');
	$sql="select * from bikeorder where bid='$bid' and (ordercondition=1 || ordercondition=2) ";
	$result=mysql_query($sql);
	while($rs=mysql_fetch_assoc($result))
	{
		$start=$rs['starttime'];
		$end=$rs['endtime'];
		$start=strtotime($start);
		$end=strtotime($end);
		$time1=strtotime($time1);
		$time2=strtotime($time2);
		if(!($time1>$end || $time2<$start))
		{
			$flag=false;
			break;
		}	
	}
//	mysql_close($conn);
	return $flag;
}

$name='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$name = $_SESSION['name'];
    
    if(isset($_GET['starttime']) && !empty($_GET['starttime']) && isset($_GET["endtime"]) && !empty($_GET["endtime"]) )
    {
	$starttime=$_GET["starttime"];
	$endtime=$_GET["endtime"];

	$starttime=strtotime($starttime);	
	$endtime=strtotime($endtime);
    }
	else 
	{
		$starttime=time();
	    $starttime=$starttime-($starttime%300)+600;
		$endtime=$starttime+86400;
	}

	include_once('../conn/conn.php');
	$sql="select * from bike where bikecondition =1 and (begintime > $endtime or endtime < $starttime) ";			
	$result=mysql_query($sql,$conn);
	$_SESSION['sql_operation']=$sql;
	$_SESSION['page']=1;	
	$arr = array();
	$n=0;

	$starttime=date("Y-m-d H:i", $starttime);
	$endtime=date("Y-m-d H:i", $endtime);
//echo $starttime.$endtime;

	while ($rs=mysql_fetch_assoc($result)) 
	{
		if(isFree($rs['bid'],$starttime,$endtime) || calculate_rest($starttime,$endtime,$rs['bid'])>=1)
		{
//			$lon=$rs['lon'];
		//	$lat=$rs['lat'];
			$arr[$n]=$rs;
			$n++;
		}
	}
	

//	file_put_contents('1.txt', $starttime.$endtime);
	 $_SESSION['starttime']=$starttime;
    $_SESSION['endtime']=$endtime;
	include('../html/search.html');
	echo "<script>document.getElementById('starttime').value='".$starttime."';document.getElementById('endtime').value='".$endtime."';</script>";
	mysql_close($conn);
}
else
{
	if(isset($_GET['starttime']) && !empty($_GET['starttime']) && isset($_GET["endtime"]) && !empty($_GET["endtime"]) )
    {
	$starttime=$_GET["starttime"];
	$endtime=$_GET["endtime"];
	$starttime=strtotime($starttime);	
	$endtime=strtotime($endtime);
    }
	else 
	{
		$starttime=time();
	    $starttime=$starttime-($starttime%300)+600;
		$endtime=$starttime+86400;
	}
	include_once('../conn/conn.php');
	$sql="select * from bike where bikecondition = 1 and (begintime > $endtime or endtime < $starttime) ";			
	$result=mysql_query($sql,$conn);
	$_SESSION['sql_operation']=$sql;
	$_SESSION['page']=1;
	$arr = array();
	$n=0;

	$starttime=date("Y-m-d H:i", $starttime);
	$endtime=date("Y-m-d H:i", $endtime);

	while ($rs=mysql_fetch_assoc($result)) 
	{
		if(isFree($rs['bid'],$starttime,$endtime) || calculate_rest($starttime,$endtime,$rs['bid'])>=1)
		{
	//		$lon=$rs['lon'];
	//		$lat=$rs['lat'];
			$arr[$n]=$rs;
			$n++;
		}
	}
	

    $_SESSION['starttime']=$starttime;
    $_SESSION['endtime']=$endtime;
//	file_put_contents('1.txt', $starttime.$endtime);
	include('../html/search.html');
	echo "<script>document.getElementById('starttime').value='".$starttime."';document.getElementById('endtime').value='".$endtime."';</script>";
	mysql_close($conn);
}
 ?>