<?php 
session_start();
header("Content-type:text/html;charset=utf-8");
//require_once('./PHPMailer/class.phpmailer.php');
require("picture.php");
require("../sendsms/sendsms.php");
//处理上传图片函数，将图片转移到对应的文件夹
function movefile($biketype,$filename,$photoname)
{

    if($biketype=='山地车')
    {
        rename($filename, "../bike/type1/".$photoname);
        return "../bike/type1/".$photoname;
    }
    if($biketype=='公路车')
    {
        rename($filename, "../bike/type2/".$photoname);
        return "../bike/type2/".$photoname;
    }
    if($biketype=='死飞')
    {
        rename($filename, "../bike/type3/".$photoname);
        return "../bike/type3/".$photoname;
    }
    if($biketype=='折叠车')
    {
        rename($filename, "../bike/type4/".$photoname);
        return "../bike/type4/".$photoname;
    }
    if($biketype=='其他')
    {
        rename($filename, "../bike/type5/".$photoname);
        return "../bike/type5/".$photoname;
    }
}
function moveThumbfile($biketype,$filename)
{

    if($biketype=='山地车')
    {
        return "../bike/thumb1/".$filename;
    }
    if($biketype=='公路车')
    {
        return "../bike/thumb2/".$filename;
    }
    if($biketype=='死飞')
    {
        return "../bike/thumb3/".$filename;
    }
    if($biketype=='折叠车')
    {
        return "../bike/thumb4/".$filename;
    }
    if($biketype=='其他')
    {
        return "../bike/thumb5/".$filename;
    }
}


function moveBigThumbfile($biketype,$filename)
{

    if($biketype=='山地车')
    {
        return "../bike/bigthumb1/".$filename;
    }
    if($biketype=='公路车')
    {
        return "../bike/bigthumb2/".$filename;
    }
    if($biketype=='死飞')
    {
        return "../bike/bigthumb3/".$filename;
    }
    if($biketype=='折叠车')
    {
        return "../bike/bigthumb4/".$filename;
    }
    if($biketype=='其他')
    {
        return "../bike/bigthumb5/".$filename;
    }
}

$username='';
if(isset($_SESSION['picture']) && !empty($_SESSION['picture']))
{
    $filename=$_SESSION['picture'];
    $photoname=$_SESSION['photoname'];
    $tell=$_SESSION['phone'];
    include_once('../conn/conn.php');
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


   // 将缓存区图片移到对应文件夹

    $photo=movefile($biketype,$filename,$photoname);

    //最小缩略图

   $thumb_photo="thumb".$photoname;
   $save = moveThumbfile($biketype,$thumb_photo);
   $thumbphoto1=$save;
   ImageTool::thumb($photo,$save,300,225);
   //水印
   $water='../image/new.png';
   //ImageTool::water($photo,$water,NULL,1,50);
   ImageTool::water($save,$water,NULL,1,50);
  
    //最大缩略图
   $bigthumb_photo="bigthumb".$photoname;
   $save = moveBigThumbfile($biketype,$bigthumb_photo);
   $bigthumbphoto1=$save;
   ImageTool::thumb($photo,$save,700,525);
   //水印
   $water='../image/new.png';
  // ImageTool::water($photo,$water,NULL,1,50);
   ImageTool::water($save,$water,NULL,1,50);
   $time=date('Y-m-d H:i:s');
//suitheight equipments fitsex address confircard shorttesti longest 


        $num=time();
        $num=$num%100;
        $num2=rand(5,100000);
        $num3=rand(6,999);
        $bikeNumber=strval($num2).strval($num).strval($num3);
        $bikeNumber=intval($bikeNumber);


   $sql="insert into bike (bikeNumber,bikename,biketype,suitheight,bikenum,howold,speedch,bikesize,braketype,cbtype,jztype,isquick,fitsex,bikeprice,equipment,photo1,thumbphoto1,bigthumbphoto1,content,city,address,school,lon,lat,hourprice,dayprice,weekprice,insureprice,isfree,confir,shortesttime,longesttime,uploadtime,tell,bikecondition) values ('$bikeNumber','$bikename','$biketype','$fitheight','$bikenum','$howold','$speedtype','$bikesize','$braketype','$handletype','$jztype','$isquikerelease','$fitsex','$bikeprice','$equipments','$photo','$thumbphoto1','$bigthumbphoto1','$contents','$city','$address','$school','$lon','$lat','$hourprice','$dayprice','$weekprice','$insureprice','$isfreeforstudent','$confircard','$shortesttime','$longesttime','$time','$tell','2') ";
   if(mysql_query($sql))
   {
    unset($_SESSION['picture']);
    unset($_SESSION['photoname']);
    mysql_close($conn);
 
/*
require('./PHPMailer/class.phpmailer.php');
$phpmailer = new PHPMailer();
$phpmailer->IsSMTP();  // 用smtp协议来发
$phpmailer->Host = 'smtp.qq.com';
$phpmailer->SMTPAuth = true;
$phpmailer->Username = '479082766';
$phpmailer->Password = 'jiangshaoMvp69';
$phpmailer->CharSet='utf-8';
// 可以发信了
$phpmailer->From = '479082766@qq.com';
$phpmailer->FromName = 'JackPaul';
$phpmailer->Subject = '新上传单车，等待处理';
$phpmailer->Body = '后台有新自行车上传，请尽快处理！';
//设置收信人
$phpmailer->AddAddress('qikezuche@163.com','qikezuche');
// 添加一个抄送
$phpmailer->AddCC('qikezuche@163.com','CTO');
*/
	
        $phone='15117928812'; //设置发送的手机号码 
 		$sms=new SendSMS($phone); //创建对象 
		
        $sms->setTem_id('91548746');
        $renderParams = array('address' => 'qikezuche.com', 'tell' =>"1"); 
        $sms->setTemplateParams($renderParams);
        $re = $sms->send();
        if(!$re->res_code)
        {
            echo '1';
        }
        else
        {
            echo $re->res_message;
        }
    }
   else
   {
    echo mysql_error();
   }
    //echo $bigthumb_photo;
}
else
{
    echo '0';//没选择照片
}

?>