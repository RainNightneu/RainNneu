<?php 
session_start();
header("Content-type:text/html;charset=utf-8");
//require("picture.php");


if(isset($_SESSION['bid_forchange']) && !empty($_SESSION['bid_forchange']))
{
    $bid=$_SESSION['bid_forchange'];
}
else
{
    echo '请不要直接访问此文件';
    exit;
}

    $bikename=$_POST['bikename'];
    $biketype=$_POST['biketype'];
    $fitheight=$_POST['fitheight'];
    $fitsex=$_POST['fitsex'];
    $bikenum=$_POST['bikenum'];
    $howold=$_POST['howold'];
    $bikeprice=$_POST['bikeprice'];
    $equipments=$_POST['equipments'];
    $speedtype=$_POST['speedtype'];
    $bikesize=$_POST['bikesize'];
    $braketype=$_POST['braketype'];
    $handletype=$_POST['handletype'];
    $jztype=$_POST['jztype'];
    $isquikerelease=$_POST['isquikerelease'];
    $contents=$_POST['contents'];
    $hourprice=$_POST['hourprice'];
    $dayprice=$_POST['dayprice'];
    $weekprice=$_POST['weekprice'];
    $insureprice=$_POST['insureprice'];
    $isfreeforstudent=$_POST['isfreeforstudent'];
    $confircard=$_POST['confircard'];
    $shortesttime=$_POST['shortesttime'];
    $longesttime=$_POST['longesttime'];
    $city=$_POST['city'];
    $school=$_POST['school'];
    $address=$_POST['address'];
    $lon=$_POST['lon'];
    $lat=$_POST['lat'];

include_once('../conn/conn.php');
$sql="select * from bike  where bid='$bid' ";
$result=mysql_query($sql);
$row=mysql_fetch_assoc($result);
$owner=$row['tell'];

$username='';
/*if(isset($_SESSION['picture']) && !empty($_SESSION['picture']))
{
   $filename=$_SESSION['picture'];
   $photoname=$_SESSION['photoname'];
   $photo=movefile($biketype,$filename,$photoname);

   //最小缩略图
   $thumb_photo="thumb".$photoname;
   $save = moveThumbfile($biketype,$thumb_photo);
   $thumbphoto1=$save;
   ImageTool::thumb($photo,$save,300,225);
   //水印
   $water='../image/new.png';
   ImageTool::water($save,$water,NULL,1,50);
   //最大缩略图
   $bigthumb_photo="bigthumb".$photoname;
   $save = moveBigThumbfile($biketype,$bigthumb_photo);
   $bigthumbphoto1=$save;
   ImageTool::thumb($photo,$save,700,525);
   //水印
   $water='../image/new.png';
   ImageTool::water($save,$water,NULL,1,50);
}
else
{
    $photo=$row['photo1'];
    $thumbphoto1=$row['thumbphoto1'];
    $bigthumbphoto1=$row['bigthumbphoto1'];
}*/

    if($lon == 0 && $lat == 0)
    {
        $lon=$row['lon'];
        $lat=$row['lat'];
    }

   $time=date('Y-m-d H:i:s');

   $sql="update bike set bikename='$bikename',biketype='$biketype',suitheight='$fitheight',bikenum='$bikenum',howold='$howold',speedch='$speedtype',bikesize='$bikesize',braketype='$braketype',cbtype='$handletype',jztype='$jztype',isquick='$isquikerelease',fitsex='$fitsex',bikeprice='$bikeprice',equipment='$equipments',content='$contents',city='$city',address='$address',school='$school',lon='$lon',lat='$lat',hourprice='$hourprice',dayprice='$dayprice',weekprice='$weekprice',insureprice='$insureprice',isfree='$isfreeforstudent',confir='$confircard',shortesttime='$shortesttime',longesttime='$longesttime',uploadtime='$time',bikecondition=1 where bid='$bid' ";
   if(mysql_query($sql))
   {
  //  unset($_SESSION['bid_forchange']);
	$_SESSION['bid_forchange']='';
	include("../sendsms/sendsms.php");
	$sms=new SendSMS($owner);		
	$sms->setTem_id('91548746');
	$renderParams = array('address' => 'qikezuche.com', 'tell' => $username); 
	$sms->setTemplateParams($renderParams);
	$re = $sms->send();

    echo '1';
   }
   else
   {
    echo '由于莫名原因审核失败，请联系骑客解决';
   }


mysql_close($conn);
?>