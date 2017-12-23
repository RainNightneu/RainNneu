<?php
session_start();
header("Content-type: text/html; charset=utf-8");
$username='';
if(isset($_SESSION['admin']) && !empty($_SESSION['admin']))
{
	$username=$_SESSION['admin'];
	
}
//获取自行车编号
if(isset($_GET["bid"]))
	$bid=$_GET["bid"];
	else $bid=1;

require('../conn/conn.php');
$sql="select * from bike where bid='$bid' ";
$result=mysql_query($sql,$conn);
$row=mysql_fetch_array($result);
if($row==0)
{
	echo "该编号自行车不存在或者已经下架";
}
else
{
	/*$sql_1="select * from bikeorder where bid='$bid' and renterscore !=0 limit 0,5";
	$result_1=mysql_query($sql_1);
	$score=0;
	$n=0;
	$arrayName = array();

	while($rt=mysql_fetch_assoc($result_1))
	{
			$arrayName[$n]=$rt;
			$score+=$rt['renterscore'];
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
*/
			include("./detail.html");
			/*echo "<script>document.getElementById('starttime').value='".$starttime."';document.getElementById('endtime').value='".$endtime."';</script>";
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
//file_put_contents('11.txt',$weeks.$days.$hours);*/
	$_SESSION['reviewnum']=5;
	$_SESSION['bid']=$bid;

}
mysql_close($conn);
?>