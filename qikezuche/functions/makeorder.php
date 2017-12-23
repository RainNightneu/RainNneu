<?php 
session_start();
//获取两个时间

//判断时间对错
//发送短信
if(isset($_SESSION['username']) && !empty($_SESSION['username']))
{
	$starttime=$_POST['time1'];
	$endtime=$_POST['time2'];
	$bid=$_POST['bid'];
    $username=$_SESSION['username'];
	if($starttime=='' || $endtime=='')
	{
		echo '-1';
	}
		else
		{
			$time1=strtotime($starttime);
			$time2=strtotime($endtime);
			if($time1>$time2)
			{
				echo '0';
			}
			else
			{

		    include_once("../conn/conn.php");
		    $sql="select * from bike where bid='$bid' ";
		    $result=mysql_query($sql);
		    $rs=mysql_fetch_assoc($result);
		    $to=$rs['tell'];
            include('sendsms.php');
            $phone=$to; //设置发送的手机号码
            $sms=new SendSMS($phone); //创建对象
		    $renderParams = array('code' => '1221', 'time' => '21'); 
            $sms->setTemplateParams($renderParams);

 //发送短信
            $re = $sms->send();


		    $rs['dayprice'];
		    $rs['hourprice'];
		    $rs['weekprice'];
		    $number=rand(1,9);
		    $data= array('neworder',$number);
		  
		   // $k=sendTemplateSMS($to,$data,"1");
		    $bikenum=1;
		    $money=100;
		    $condition=0;
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

            $timemsg=$weeks.'周'.$days.'天'.$hours.'小时';
		    $sql= "insert into bikeorder (ordercondition,starttime,endtime,renttime,bikenum,owner,renter,money,bid) values (0,'$starttime','$endtime','$timemsg','$bikenum','$to','$username',0,'$bid') ";
		    $result=mysql_query($sql);
		  // file_put_contents('1.txt', mysql_error());
		   if($result)
		    {

		     if (!$re->res_code )
		      { //从发送短信的方法中返回的obj中读取发送成功与否的标识 ,为0时是成功
            	echo "1"; 
              }
		        else 
		        echo '短信发送失败！' . $re->res_message;
		    }	    
		}
    }
}
else
{
	$starttime=$_GET['time1'];
	$endtime=$_GET['time2'];
	$bid=$_GET['bid'];
	echo $starttime.'<br>'.$endtime.'<br>'.$bid;
//	echo '2';
}


 ?>