<?php
//require_once("conn.php");
$conn=mysql_connect("localhost","root","137515");
mysql_select_db("qike",$conn);
mysql_query("set names utf8 ");

$i=1;
$message='';
$current_time=mktime();
$sql_order="select * from bikeorder where ordercondition=1 order by ordertime asc";
$result_order=mysql_query($sql_order);
while($row_order=mysql_fetch_assoc($result_order))
{
    if($current_time-strtotime($row_order['ordertime'])>7200)
    {
        //echo "<tr><td>".$i."</td><td>".$row_order['oid']."</td><td>等待车主处理</td><td>车主超时未处理</td><td>".$row_order['rentername']."</td><td>".$row_order['rentertell']."</td><td>车主姓名</p></td><td>".$row_order['owner']."</td><td>".$row_order['ordertime']."</td><td><a href=''><span>详情&nbsp;</span></a><button class='order_agree'  onclick='comfirmorder(this);'>确认</button><button  class='order_refuse' onclick='cancelorder(this);'>拒绝</button></td></tr>";
        //$message=
        $i++;
    }
}
//echo $i;
if($i>0)
{
    require('./PHPMailer/class.phpmailer.php');
    $phpmailer = new PHPMailer();
    $phpmailer->IsSMTP();  // 用smtp协议来发
    $phpmailer->Host = 'smtp.163.com';
    $phpmailer->SMTPAuth = true;
    $phpmailer->Username = 'JackPaul163';
    $phpmailer->Password = 'jiangshaoMvp69';
    $phpmailer->From = 'JackPaul163@163.com';
    $phpmailer->FromName = 'JackPaul163';
    $phpmailer->Subject = 'order time out!!';
    $phpmailer->Body = 'http://www.qikezuche.com/admin';
    $phpmailer->AddAddress('496061885@qq.com','496061885');
    $phpmailer->AddAddress('qikezuche@163.com','qikezuche');

    // 发信
    if($phpmailer->send())
        echo "1";
    else
        echo "通知管理员失败，请根据网站下方联系方式联系客服，谢谢！";
}

?>