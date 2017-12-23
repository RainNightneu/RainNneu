<?php 
session_start();
header("Content-type:text/html;charset=utf-8");
//if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
//{

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
	$city=$_POST['city'];
	$school=$_POST['school'];
	$starttime=$_POST['starttime'];
	$time1=$starttime;
	$endtime=$_POST['endtime'];
	$time2=$endtime;
	$biketype=$_POST['biketype'];
	$fitheight=$_POST['fitheight'];
	$isfree=$_POST['isfree'];
	$price=$_POST['price'];

	if($biketype=='全部')
	{
		$sql_0="";
	}
	if($biketype=='山地车')
	{
		$sql_0=" and biketype='山地车' ";
	}
	if($biketype=='公路车')
	{
		$sql_0=" and biketype='公路车' ";
	}
	if($biketype=='死飞')
	{
		$sql_0=" and biketype='死飞' ";
	}
	if($biketype=='折叠车')
	{
		$sql_0=" and biketype='折叠车' ";
	}
	if($biketype=='其他')
	{
		$sql_0=" and biketype='其他' ";
	}


	if($fitheight=='155以下')
	{
		$sql_1=" and suitheight='155以下'";
	}
	if($fitheight=='155-165')
	{
		$sql_1=" and suitheight='155-165'";
	}
	if($fitheight=='165-175')
	{
		$sql_1=" and suitheight='165-175'";
	}
	if($fitheight=='175-185')
	{
		$sql_1=" and suitheight='175-185'";
	}
	if($fitheight=='185以上')
	{
		$sql_1=" and suitheight='185以上'";
	}
    if($fitheight=='全部')
    {
    	$sql_1='';
    }

    if($price =='0-15')
    {
    	$sql_2=" and dayprice<=15";
    }
    if($price=='15-30')
    {
    	$sql_2=" and dayprice>15 and dayprice<=30";
    }
    if($price=='30-45')
    {
    	$sql_2=" and dayprice>30 and dayprice<=45";
    }
    if($price=='全部')
    {
    	$sql_2='';
    }
    if($price=='45以上')
    {
    	$sql_2=" and dayprice>45";
    }

    if($isfree=='全部')
    {
    	$sql_3='';
    }
    if($isfree=='是')
    {
    	$sql_3=" and isfree='是' ";
    }
    if($isfree=='否')
    {
    	$sql_3=" and isfree='否' ";
    }

	$starttime=strtotime($starttime);
	$endtime=strtotime($endtime);

	$perNum=20;$n=0;
	if(isset($_GET["page"]))
	$page=$_GET["page"];
	else 
	$page=1;

	include_once('../conn/conn.php');
    if($school=="全部")
	{
		 $sql="select * from bike where bikecondition = 1 and city ='$city' and (begintime > $endtime or endtime < $starttime) ".$sql_0.$sql_1.$sql_2.$sql_3;
	}
	else
	{
	    $sql="select * from bike where bikecondition = 1 and school ='$school' and city ='$city' and (begintime > $endtime or endtime < $starttime) ".$sql_0.$sql_1.$sql_2.$sql_3;
	}
	
	//file_put_contents('5.txt', $sql);
	$result=mysql_query($sql);
	$n=0;
    $arr = array();
	while($rs=mysql_fetch_assoc($result))
	{
		if(isFree($rs['bid'],$starttime,$endtime))
		{
			$arr[$n]=$rs;
			$n++;
		}
	}
	if($n==0)
	{
		echo '0';
	}
	else
	{
		$_SESSION['sql_operation']=$sql;
		$_SESSION['page']=$page;
		echo json_encode($arr);
	}
	mysql_close($conn);
//}
//else
//{
//	echo '请您先登录';
//	echo '<script>window.location.href="../index.php";</script>';
//}
 ?>