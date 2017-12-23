<?php
session_start();
header("Content-type: text/html; charset=utf-8");
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$bid=$_POST['bid'];
    include_once('../conn/conn.php');
    $sql="update bike set begintime='1440892800',endtime='1440892801' where bid='$bid'";
    file_put_contents('1.txt', $sql);
    $result=mysql_query($sql);
    if($result)
    {
        mysql_close($conn);
         echo '1';
    }
    else
    {
        echo mysql_error();
    mysql_close($conn);

    }

}
else
{
    echo '请您先登录';

}

?>