<?php 
session_start();
header("Content-type:text/html;charset=utf-8");

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

//if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
//{
	$city=$_POST['city'];
	$school=$_POST['school'];
	$starttime=$_POST['starttime'];
	$time1=$starttime;
	$endtime=$_POST['endtime'];
	$time2=$endtime;
	$starttime=strtotime($starttime);
	$endtime=strtotime($endtime);

	$perNum=20;$n=0;
	if(isset($_GET["page"]))
	$page=$_GET["page"];
	else 
	$page=1;
	//file_put_contents('4.txt', $starttime.$endtime);

	include_once('../conn/conn.php');
	$sql="select * from bike where bikecondition = 1 and school ='$school' and city ='$city' and (begintime > $endtime or endtime < $starttime)";
	//file_put_contents('3.txt', $sql);
	$result=mysql_query($sql);
	$n=0;
    $arr = array();
	while($rs=mysql_fetch_assoc($result))
	{
		if(isFree($rs['bid'],$time1,$time2))
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
		$_SESSION['sql_operation']="select * from bike where bikecondition = 1 and school ='$school' and city ='$city' ";
		$_SESSION['page']=$page;
		echo json_encode($arr);
	}
	mysql_close($conn);
//}
//else
//{
	//echo '请您先登录';
	//echo '<script>window.location.href="../index.php";</script>';
//}
 ?>