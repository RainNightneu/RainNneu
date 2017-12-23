<?php
session_start();
$name='';
//$bid=3;

if(isset($_GET["bid"]))
	$bid=$_GET["bid"];
	else $bid=-1;


if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$_SESSION['bid_forchange']=$bid;
	$username=$_SESSION['phone'];
	$name = $_SESSION['name'];
	include_once('../conn/conn.php');
	$sql="select * from bike  where bid='$bid' ";
	$result=mysql_query($sql);
	$row=mysql_fetch_assoc($result);
	if($row)
	{
    include('change_bike.html');
	}
	else
	{
		echo '该自行车已经下架或不存在';
	}
}
else
{
	echo '<script> alert("请您先登录！");window.location.href="../login.html";</script>';

}
?>