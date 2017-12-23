<?php
session_start();
header("Content-Type: text/html; charset=utf-8");
$phone=$_SESSION['phone'];
$username=$_POST['username'];
include_once('../conn/conn.php');
$sql="select * from user where username='$username' and tell != '$phone'";
$result=mysql_query($sql);
if($re=mysql_fetch_array($result))
{
  echo '0';
}
else
{
  if(isset($_SESSION['picture_head']) && !empty($_SESSION['picture_head']))
  {
    $filename_head=$_SESSION['picture_head'];
    $photoname_head=$_SESSION['photoname_head'];

  //  file_put_contents('1.txt', $photoname_head);
    rename($filename_head, "../bike/heads/".$photoname_head);

    $head="../bike/heads/".$photoname_head;

    $sql_1="update user set username='$username',photo='$head' where tell='$phone' ";
    $result=mysql_query($sql_1);
    //session_destroy(picture_head);
  //  session_unset('picture_head');
	$_SESSION['picture_head']='';
    echo '1';
  }
  else
  {
    $sql_1="update user set username='$username' where tell='$phone' ";
    $result=mysql_query($sql_1);
    echo '1';
  }
  
}
mysql_close();
?>
