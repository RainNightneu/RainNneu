<?php
session_start();
header("Content-type: text/html; charset=utf-8");
$reviewnum=$_SESSION['reviewnum'];
$bid=$_SESSION['bid'];
include('../conn/conn.php');

$sql="select * from bikeorder where bid='$bid' and renterscore !=0 limit ".$reviewnum.",5";
$result=mysql_query($sql);
$arrayName = array();
$n=0;
//echo mysql_error($conn);
while($rt=mysql_fetch_assoc($result))
{
		$arrayName[$n]=$rt;
		$n++;
}
if($n==0)
{
	echo '0';
}
else
{
	echo json_encode($arrayName);
	$_SESSION['reviewnum']=5+$n;
}
mysql_close($conn);
?>