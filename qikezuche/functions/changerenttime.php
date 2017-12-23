<?php
session_start();
header("Content-type: text/html; charset=utf-8");
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$bid=$_POST['bid'];
	$starttime=$_POST['starttime'];
	$endtime=$_POST['endtime'];
	//$starttime=date("Y-m-d h:i:s", $starttime);
	//$endtime=date("Y-m-d h:i:s", $endtime);
 	$time1=strtotime($starttime);
	$time2=strtotime($endtime);
    if($time2<=$time1)
    {
        echo '请选择正确的时间区间';
    }
    else
    {
        include_once('../conn/conn.php');
        $sql="update bike set begintime='$time1',endtime='$time2' where bid='$bid'";
        $result=mysql_query($sql);
        if($result)
        {
            echo '1';
        }
        mysql_close($conn);
    }
    
}
else
{
    echo '请您先登录';

}

?>