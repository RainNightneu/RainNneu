<?php 
session_start();
header("Content-type: text/html; charset=utf-8");    
$username='';
if(isset($_SESSION['phone']) && !empty($_SESSION['phone']))
{
	$username=$_SESSION['phone'];
	$ope_object=$_POST['ope_object'];
	$ope=$_POST['ope'];
	$oid=$_POST['oid'];
	include('../sendsms/sendsms.php');
	include_once('../conn/conn.php');
    
    $sql="select * from bikeorder where oid = '$oid'";
	$result=mysql_query($sql);
	$rs=mysql_fetch_assoc($result);

	$t=time(); 
 	$handletime=date("Y-m-d H:i:s",$t); 

	if($ope_object==1)
	{
		switch($ope)
		{
			case 1:
			{
				$sql_1="select * from bikeorder where oid='$oid'";
				$result_1=mysql_query($sql_1);
				$rs_1=mysql_fetch_assoc($result_1);
				if($rs_1['ordercondition']==0)
				{
					echo '对不起订单已经取消。';
				}
				else
				{
					if($rs_1['ordercondition']==2)
					{
					echo '订单已经确认。';
					}
					else
					{
						$sql="update bikeorder set ordercondition=2,handletime='$handletime' where oid = '$oid' ";
						$result=mysql_query($sql);
						$sms=new SendSMS($rs['rentertell']);
		    			$sms->setTem_id('91548746');
		    			$renderParams = array('address' => 'qikezuche.com', 'tell' => $username); 
		    			$sms->setTemplateParams($renderParams);
		    			$re = $sms->send();
						if($result && !$re->res_code)
						{
							echo '1';
						}
						else
						{
							echo $re->res_message;
						}
					}
				}


			}
			;break;

			case 2:
			{
				$sql_1="select * from bikeorder where oid='$oid'";
				$result_1=mysql_query($sql_1);
				$rs_1=mysql_fetch_assoc($result_1);
				/*if($rs_1['ordercondition']==2)
				{
					echo '1';
				}
				else
				{*/
					if($rs_1['ordercondition']==0)
					{
						echo '订单已经取消。';
					}
					else
					{
						$sql="update bikeorder set ordercondition=0,mark=1,handletime='$handletime' where oid = '$oid' ";
						$result=mysql_query($sql);
						$reason='车主临时有事，无法租车';
						$sms=new SendSMS($rs['rentertell']);
		    			$sms->setTem_id('91548747');
		    			$renderParams = array('reason' => $reason); 
		    			$sms->setTemplateParams($renderParams);
		    			$re = $sms->send();
						if($result && !$re->res_code)
						{
							echo '1';
						}
						else
						{
							echo $re->res_message;
						}
					}
				//}

			}
			;break;

			case 3:
			{
				$sql_1="select * from bikeorder where oid='$oid'";
				$result_1=mysql_query($sql_1);
				$rs_1=mysql_fetch_assoc($result_1);
				if($rs_1['ordercondition']== 0)
				{
					echo '对不起订单已经取消。';
				}
				else
				{
					if($rs_1['ordercondition']==3)
					{
						echo '1';
					}
					else
					{
						$sql="update bikeorder set ordercondition=3 where oid = '$oid' ";
						$result=mysql_query($sql);
						if($result)
						{
							echo '1';
						}
						else
						{
							echo '0';
						}
					}
				}
			}
			;break;

			case 4:
			{
				$content=$_POST['content'];
				$score=$_POST['score'];
				$sql="update bikeorder set ownerscore='$score',ownercontent='$content' where oid = '$oid' ";
				$result=mysql_query($sql);
				if($result)
				{
					echo '1';
				}
				else
				{
					echo '评价失败';
				}
			}
			;break;
		}
	}
	else
	{
		switch($ope)
		{
			case 1:
			{
				$content=$_POST['content'];
				$score=$_POST['score'];
				$sql="update bikeorder set renterscore='$score',rentercontent='$content' where oid = '$oid' ";
				$result=mysql_query($sql);
				if($result)
				{
					echo '1';
				}
				else
				{
					echo '评价失败';
				}
			}
			;break;

			case 2:
			{
				$sql="select * from bikeorder where oid = '$oid' ";
				$result=mysql_query($sql);
				$rs=mysql_fetch_assoc($result);
				$bid=0;
				if($rs)
				{
					$bid=$rs['bid'];
				}
				echo $bid;
			}
			;break;

			case 3:
			{
				$sql_1="select * from bikeorder where oid='$oid'";
				$result_1=mysql_query($sql_1);
				$rs_1=mysql_fetch_assoc($result_1);
				if($rs_1['ordercondition']==2)
				{
					echo '对不起，订单已经确认，请联系车主取消！';
				}
				else
				{
					if($rs_1['ordercondition']==0)
					{
					echo '订单已经取消。';
					}
					else
					{
						$sql="update bikeorder set ordercondition=0,mark=0 where oid = '$oid' ";
						$result=mysql_query($sql);
						$sms=new SendSMS($rs['owner']);
		    			$sms->setTem_id('91548829');
		    			$reason='选择错误';
		    			$renderParams = array('oid'=>$oid,'reason' => $reason); 
		    			$sms->setTemplateParams($renderParams);
		    			$re = $sms->send();
						if($result && !$re->res_code)
						{
							echo '1';
						}
						else
						{
							echo $re->res_message;
						}
					}
				
				}
			}
			;break;
		}
	}
	mysql_close($conn);
}
else
{
	echo '请您先登录！';
}

 ?>