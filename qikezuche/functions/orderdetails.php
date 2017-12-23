<?php 
session_start();
header("Content-type: text/html; charset=utf-8");    
$username='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	include_once('../conn/conn.php');
	$usertell=$_SESSION['phone'];

	$sql_u="select * from user where tell='$usertell' ";
	$result_u=mysql_query($sql_u);
	$rs=mysql_fetch_assoc($result_u);
	$username=$rs['username'];
	$realname=$rs['realname'];
	// $username = $_GET['name'];
	$starttime=$_GET['time1'];
	$endtime=$_GET['time2'];
	$bid=$_GET['bid'];
	$num=$_GET['num'];


	$sql="select * from bike where bid='$bid' ";
	$result=mysql_query($sql);
	if($rs=mysql_fetch_assoc($result))
	{
		$otell = $rs['tell'];
		    $dayprice=$rs['dayprice'];
		    $hourprice=$rs['hourprice'];
		    $weekprice=$rs['weekprice'];

		    $time1=strtotime($starttime);
			$time2=strtotime($endtime);

		    $time3=$time2-$time1;
		    $time3/=3600;

		    $hours=(int)$time3;
		    $days=0;
		    $weeks=0;
            if($hours<24 && $hours>=8)
            {
                $days=1;
                $hours=0;
            }
            else
            {
            	if($hours<8)
            	{
            	    $days=0;
            	    $weeks=0;
            	}
            	else
            	{
                   
                    $days=(int)($hours/24);
                    $hours=$hours-$days*24;
                    if($hours>=8)
                    {
                    	$hours=0;
                    	$days+=1;
                    }
            	}
            }

            $totalmoney=$num*($weeks*$weekprice+$days*$dayprice+$hours*$hourprice);
            $money=$totalmoney;
		    $timemsg=$weeks.'周&nbsp;'.$days.'天&nbsp;'.$hours.'小时';
		$sql_o="select * from user where tell='$otell' ";
		$result_o=mysql_query($sql_o);
		$rs_o=mysql_fetch_assoc($result_o);
		$oname = $rs_o['username'];
		    include_once('../html/order.html');
	}
	else
	{
		echo 'no such bike!';	
	}
	mysql_close($conn);
}
else
{
	echo '请您先登录!';
}
 ?>