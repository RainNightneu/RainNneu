<?php
session_start();
header("Content-type: text/html; charset=utf-8"); 
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
header("Cache-Control: no-cache, must-revalidate"); 
header("Pramga: no-cache");

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


function unicode2utf8($str){
        if(!$str) return $str;
        $decode = json_decode($str);
        if($decode) return $decode;
        $str = '["' . $str . '"]';
        $decode = json_decode($str);
        if(count($decode) == 1){
                return $decode[0];
        }
        return $str;
}

$name='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$name = $_SESSION['name'];
	
}
//获取自行车编号
if(isset($_GET["bid"]))
	$bid=$_GET["bid"];
	else $bid=1;

require('../conn/conn.php');
//print_r($conn);
$sql="select * from bike where bid='$bid' ";
$result=mysql_query($sql,$conn);
$row=mysql_fetch_array($result);
if($row==0)
{
	echo "该编号自行车不存在或者已经下架";
}
else
{
	    	$ownertell=$row['tell'];
		//echo $ownertell;
		$sql_owner="select * from user where tell='$ownertell' ";
		$result_owner=mysql_query($sql_owner,$conn);
		$rs_owner=mysql_fetch_array($result_owner);
		$ownername=$rs_owner['1'];
		//echo mysql_error();
		//车主计算模块
		$owner_order_array=calculate_owner($ownertell,$bid);
	//	var_dump($rs_owner);


	$sql_1="select * from bikeorder where bid='$bid' and renterscore !=0 limit 0,5";
	$result_1=mysql_query($sql_1);
	$score=0;
	$n=0;
	$arrayName = array();
	$renternamefir = array();//added by deonew first letter
	$renternamelas = array();//added by deonew last letter
	$rentertimetail = array();//added by deonew last letter
	while($rt=mysql_fetch_assoc($result_1))
	{
		$arrayName[$n]=$rt;
		$score+=$rt['renterscore'];
		$renternamefir[$n] = mb_substr($rt['rentername'], 0,1,'utf-8');//added by deonew
		$renternamelas[$n] ='';// substr($rt['rentername'], strlen($rt['rentername'])-1,1,'utf-8');//added by deonew
		$rentertimetail[$n] = substr($rt['ordertime'], 0,16);//added by deonew
		$n++;
	}	

	if($n!=0)
	{
		$score/=$n;
	}
	else
	{
		$score=5;
	}
	$score=intval($score);

	if(isset($_SESSION['starttime']) && !empty($_SESSION['starttime']))
	{
		$starttime=$_SESSION['starttime'];
		$endtime=$_SESSION['endtime'];
	}
    else
    {
    	$starttime=mktime();
	    $starttime=$starttime-($starttime%300)+600;
		$endtime=$starttime+86400;

		$starttime=date("Y-m-d h:i", $starttime);
		$endtime=date("Y-m-d h:i", $endtime);
    }
    		$rest_bike=calculate_rest($starttime,$endtime,$bid);
			include("../html/detail.html");
			echo "<script>document.getElementById('starttime').value='".$starttime."';document.getElementById('endtime').value='".$endtime."';</script>";
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

	echo "<script>document.getElementById('totaltime').innerHTML=' ".$weeks.'周'.$days.'天'.$hours.'小时' ."';</script>";

	$sql_price="select * from bike where bid='$bid' ";
	$result_price=mysql_query($sql_price);

	$r=mysql_fetch_assoc($result_price);
	$total=$r['weekprice']*$weeks+$r['dayprice']*$days+$r['hourprice']*$hours;
	$str='租金共'.$total.'元  |  立即预定';
	echo "<script>document.getElementById('submitbutton').innerHTML=' ".$str." ' ;
	</script>";
	    //file_put_contents('11.txt',$weeks.$days.$hours);
		$_SESSION['reviewnum']=5;
		$_SESSION['bid']=$bid;

}


mysql_close($conn);



function calculate_owner($ownertell,$bid)
{
	$own_order_arr=array();
	$time_array=array();
	require_once("../conn/conn.php");
	$sql_owner="select * from bikeorder where owner='$ownertell'";
	$result_owner=mysql_query($sql_owner);
	$finished_order=0;
	$accept_rate=0.0;
	$total_order=0;
	$wait_time=0;
	$k=0;
	$i=0;
	$starttime_arr=array();
	$endtime_arr=array();
	//$accept_time=;
	while($rs_owner=mysql_fetch_assoc($result_owner))
	{
		$total_order++;
		if($rs_owner['ordercondition']==3)
		{
			$finished_order++;
		}
		if($rs_owner['ordercondition']!=1 && $rs_owner['ordercondition']!=0)
		{
			$accept_rate++;
		}
		if($rs_owner['ordercondition']==2 || ($rs_owner['ordercondition']==0 && $rs_owner['mark']==1) || $rs_owner['ordercondition']==3)
		{
			if($rs_owner['handletime']!=null)
			{
				$k++;
				$handletime=$rs_owner['handletime'];
				
				$ordertime=$rs_owner['ordertime'];
				
				$handletime=strtotime($handletime);
				$ordertime=strtotime($ordertime);
				$wait_time+=($handletime-$ordertime);
		
			}
			
		}

	/*	if($rs_owner['ordercondition']==1 || $rs_owner['ordercondition']==2  )
		{
			$starttime_arr[$i]=$rs_owner['starttime'];
			$endtime_arr[$i]=$rs_owner['endtime'];
			$i++;
			// echo "hia";
		}*/
	}

	$sql_bike="select * from bikeorder where bid='$bid'";
	$result_bike=mysql_query($sql_bike);
	while($rs_bike=mysql_fetch_assoc($result_bike))
	{
		if($rs_bike['ordercondition']==1 || $rs_bike['ordercondition']==2  )
		{
			$starttime_arr[$i]=$rs_bike['starttime'];
			$endtime_arr[$i]=$rs_bike['endtime'];
			$i++;
		}
	}

	if($k==0)
	{
		$k=1;
	}

	$wait_time=$wait_time/$k;
	//$wait_time=date("Y-m-d h:i:s", $wait_time);
	//$wait_days=$wait_time/86400;
	$wait_hours=intval($wait_time/3600);
	$wait_minites=intval(($wait_time%3600)/60);
	$wait_seconds=$wait_time-$wait_hours*3600-$wait_minites*60;

	$wait_time_str=$wait_hours."小时".$wait_minites."分";

	$own_order_arr[0]=$finished_order;

	if($total_order==0)
	{
		$total_order=1;
	}

	
	$rate=intval(($accept_rate/$total_order)*100);
    $own_order_arr[1]=$rate;
	$own_order_arr[3]=$wait_time_str;
	$own_order_arr[4]=$i;//不可出租时间段数目
	$own_order_arr[5]=$starttime_arr;//开始时间
	$own_order_arr[6]=$endtime_arr;//结束时间

	return $own_order_arr;

}
?>