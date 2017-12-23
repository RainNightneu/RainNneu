<?php
session_start();
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$tell=$_SESSION['phone'];
	include_once('../conn/conn.php');
	$sql_1="select * from user where tell='$tell' ";
	$result_1=mysql_query($sql_1);
	$rs_1=mysql_fetch_assoc($result_1);
	if($rs_1['mark']!=6)
	{
		echo "请您先到个人中心进行身份认证。";
	}
	else
	{
		$renter=$_SESSION['phone'];
		$equipments=$_POST['equipments'];
		$bikenum=$_POST['num'];
		$bid=$_POST['bid'];
		$ownertell=$_POST['ownertell'];
		$starttime=$_POST['starttime'];
		$endtime=$_POST['endtime'];
		$rentername=$_POST['rentername'];
		$rentertell=$_POST['rentertell'];
	    $insureprice=$_POST['insureprice'];
		$starttime=date("Y-m-d H:i:s", $starttime);
		$endtime=date("Y-m-d H:i:s", $endtime);
		$money=$_POST['money'];

		$ordertime=date("Y-m-d H:i:s");

    //计算租车时间
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

		    $renttime=$weeks.'周'.$days.'天'.$hours.'小时';  

            file_put_contents("time.txt", $starttime); 

//$ownertell='13269479683';
   
	include('../sendsms/sendsms.php');
    $sms=new SendSMS($ownertell);
    $sms->setTem_id('91548745');
    $renderParams = array('address' => 'qikezuche.com', 'tell' => $rentertell); 
    $sms->setTemplateParams($renderParams);
    $re = $sms->send();
    
    
    
    if(!$re->res_code)
    {

        $sql="select * from bike where bid='$bid'";
        $result=mysql_query($sql);
        $rs=mysql_fetch_assoc($result);

        $num=$rs['bid'];
        $num2=rand(5,10000);
        $num3=rand(6,999);
		$orderNumber=strval($num2).strval($num).strval($num3);
		$orderNumber=intval($orderNumber);


        if($rs['bikecondition']==0)
        {
            echo '抱歉,已经有人预定该车!';
        }
        else
        {
        $sql="insert into bikeorder (orderNumber,ordercondition,starttime,endtime,renttime,bikenum,equipments,owner,renter,money,insureprice,bid,ordertime,rentername,rentertell) values ('$orderNumber','1','$starttime','$endtime','$renttime','$bikenum','$equipments','$ownertell','$renter','$money','$insureprice','$bid','$ordertime','$rentername','$rentertell')";
        $result=mysql_query($sql);

    //    $sql_2="select * from bikeorder where owner='$ownertell' ";
    //    $result_2=mysql_query($sql_2);
    //    $rs_2=mysql_fetch_assoc($result_2);
    //    $oid=$sql_2['oid'];
   //     $orderNumber=strval($num2).strval($oid).strval($num3);
   //     $orderNumber=intval($orderNumber);

   //      $sql_3="update bikeorder set orderNumber='$orderNumber' where oid='$oid' ";
   //      $result_3=mysql_query($sql_3);
         
        if($result)
        {
            echo '1';
        }
        else
        {
            echo '预订错误，请刷新重试！';
        }
        }
	    
        mysql_close($conn);
    }
    else
    {
    	//file_put_contents('1.txt', $sms->phone);
    	echo '发送失败,:'.$re->res_message.';请稍后重试！';
    }
	}
}
else
{
    echo '请您先登录';

}

?>