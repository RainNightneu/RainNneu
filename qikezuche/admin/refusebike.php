<?php 
session_start();
header("Content-type:text/html;charset=utf-8");

if(isset($_SESSION['bid_forchange']) && !empty($_SESSION['bid_forchange']))
{
    $bid=$_SESSION['bid_forchange'];
}
else
{
    echo '请不要直接访问此文件';
    exit;
}
 
include_once('../conn/conn.php');
$sql="select * from bike  where bid='$bid' ";
$result=mysql_query($sql);
$row=mysql_fetch_assoc($result);
//$username='';

   $time=date('Y-m-d H:i:s');

   $sql="update bike set uploadtime='$time',bikecondition=3 where bid='$bid' ";
   if(mysql_query($sql))
   {
    unset($_SESSION['bid_forchange']);
    echo '1';
   }
   else
   {
    echo '由于莫名原因修改失败，请联系骑客解决';
   }
mysql_close($conn);
?>