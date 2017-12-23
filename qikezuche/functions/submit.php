<?php
session_start();
$username='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	include("../conn/conn.php");
	$sql="select * from user where tell='$username' ";
	$result=mysql_query($sql);
	$row=mysql_fetch_assoc($result);
	mysql_close($conn);
    include('../html/submit.html');
}
else
{
echo "<script>alert('请您先登录！');history.go(-1);</script>";

}
?>