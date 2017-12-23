
<?php 
session_start();


function calculate_rest($start_time,$end_time,$bid)
{
	$time1=strtotime($start_time);
	$time2=strtotime($end_time);

	$rest_number=0;

	$time3=0;
	$time4=0;
	//获取自行车数   and (ordercondition!= '0' or ordercondition!= '3')
	require_once("../conn/conn.php");
	$sql="select bikenum from bike where bid='$bid'";
	$result=mysql_query($sql);
	$re=mysql_fetch_array($result);
	$rest_number=$re[0];

	$sql_1="select * from bikeorder where bid='$bid'";
	$result_1=mysql_query($sql_1);
	while($re_1=mysql_fetch_assoc($result_1))
	{
		if($re_1['ordercondition']==1 || $re_1['ordercondition']==2)
		{
			$time3=strtotime($re_1['starttime']);
			$time4=strtotime($re_1['endtime']);
			if(!($time2<$time3 || $time1>$time4))
			{
				$rest_number-=$re_1['bikenum'];
				//echo 'jaja';
			}
		}
	}

	return $rest_number;
}




	$starttime=$_POST['time1'];
	$endtime=$_POST['time2'];
	$bid=$_POST['bid'];
	$bikenum=$_POST['num'];
   // $username=$_SESSION['phone'];
	if($starttime=='' || $endtime=='')
	{
		echo '-1';
	}
		else
		{
			$rest_number=calculate_rest($starttime,$endtime,$bid);
			$time1=strtotime($starttime);
			$time2=strtotime($endtime);
			if($bikenum>$rest_number)
			{
				echo '1';
			}
			else
			{
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
				    $dayprice=$rs['dayprice'];
				    $hourprice=$rs['hourprice'];
				    $weekprice=$rs['weekprice'];
				    $number=rand(1,9);
				    $data= array('neworder',$number);
				   // $k=sendTemplateSMS($to,$data,"1");
				    //$bikenum=1;
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
		                   if($hours>=168)
		                   {
		                   		$weeks=(int)($hours/168);
		          			    $days=(int)(($hours-$weeks*168)/24);
		                   		$hours=($hours%168)%24;
								if($hours>=8)
		                   		{
		                   			$days++;
		                   			$hours=0;
		                   		}
		                   		if($days == 7)
		                   		{
		                   			$weeks++;
		                   			$days=0;
		                   		}
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
		            }

		            $totalmoney=$bikenum*($weeks*$weekprice+$days*$dayprice+$hours*$hourprice);
		            $money=$totalmoney;
		            $totalmoney='租金共&yen;'.$totalmoney.'&nbsp;|&nbsp;立即预定';
		            $timemsg=$weeks.'周'.$days.'天'.$hours.'小时'.','.$totalmoney;
		            //mysql_close($conn);
		            echo $timemsg;
				}
		}
			
    }
  
 ?>