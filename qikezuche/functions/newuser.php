<?php
 header("Content-type: text/html; charset=utf-8");
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
$phpmailer->Subject = '新用户信息，等待审核';
$phpmailer->Body = '后台有新用户信息上传，请尽快处理！';
//设置收信人
$phpmailer->AddAddress('qikezuche@163.com','qikezuche');
// 添加一个抄送
$phpmailer->AddCC('qikezuche@163.com','CTO');

// 发信

echo $phpmailer->send()?'ok':'fail';
