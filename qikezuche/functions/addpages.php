<?php 
session_start();
$page=$_SESSION['page']; 

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
	$sql="select * from bikeorder where bid='$bid'";
	$result=mysql_query($sql);
	while($rs=mysql_fetch_array($result))
	{
		if($rs['ordercondition']==1 || $rs['ordercondition']==2)
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
	}
	return $flag;
}


$perNum = 20;  //每页显示的项数
$perow = 1;   //每行显示的条数
$city=$_POST['city'];
$school=$_POST['school'];
$starttime=$_POST['starttime'];
$endtime=$_POST['endtime'];

require_once('../conn/conn.php');

$pos=$page*$perNum;

$sql=$_SESSION['sql_operation']." limit ".$pos.",$perNum";
$result=mysql_query($sql);

$i=0; 
$arr=array();
while($rs=mysql_fetch_array($result))
{
	if(isFree($rs['bid'],$starttime,$endtime) || calculate_rest($starttime,$endtime,$rs['bid'])>=1)
	{
		$arr[$i]=$rs;
    	$i++;
	}
    
}
if($i==0)
{
	echo '0';
	mysql_close($conn);
}
else
{
	$_SESSION['page']++;
	mysql_close($conn);
	echo json_encode($arr);

}



 ?>