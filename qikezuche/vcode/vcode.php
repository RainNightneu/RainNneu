<?php
session_start();
ob_clean();
//注意png与后面生成图片格式需要保持一致
header ("Content-type: image/png"); 
/*//创建画布
//$image是资源类型
$width =300;
$height=200;
$image=imagecreatetruecolor($width,$height);
//创建各种颜料
$blue=imagecolorallocate($image,0,0,255);
//用颜料填充画布
imagefill($image,0,0,$blue);
imagejpeg($image);
imagedestroy($image);
//绘画（写字，画线，
//保存图片
//清理战场，销毁画布

//创建画布时，可以用imagecreatetruecolor，还可以打开图片当做画布
$file='1.jpg';
$image=imagecreatefromjpeg($file);
//print_r($image);
$red=imagecolorallocate($image,255,0,0);
$blue=imagecolorallocate($image,0,0,255);
imageline($image,0,0,10,10,$red);
imagepng($image);
imagedestroy($im);*/
//imagestring(resource $image,int font,int $x,int $y,string $s,int $color)x,y是坐标
$image =imagecreatetruecolor(73,38);
$red=imagecolorallocate($image,255,0,0);
$gray=imagecolorallocate($image,50,50,50);
//随机颜色
$randcolor=imagecolorallocate($image,mt_rand(150,255),mt_rand(150,255),mt_rand(150,255));
imagefill($image,0,0,$randcolor);
$str=substr(str_shuffle('ABCDEFGHJKMNPRTUVWXYZabcdefghjkmnpqrstuvwxyz2346789'),0,4);
$_SESSION['vcode']=strtolower($str);
file_put_contents('1.txt',$str);
imagestring($image,5,18,9,$str,$red);
imageline($image,0,0,75,35,$red);
imagepng($image);
imagedestroy($image);
?>
