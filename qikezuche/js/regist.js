function createXMLHttpRequest()

{

var xmlHttp=null;

  if (window.XMLHttpRequest) 

  {

     xmlHttp = new XMLHttpRequest(); //FireFox、Opera等浏览器支持的创建方式

  } else 

  {

     xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");//IE浏览器支持的创建方式

  }

  return xmlHttp;

}

function chv(vcode)

{

  vcode.src=vcode.src+'?_s='+Math.random();

}


function sendsms()

{

  var xml=createXMLHttpRequest();

  var phone=eval(document.getElementById("tell")).value;

  var randcode=eval(document.getElementById("vcode")).value;

  randcode=randcode.toLowerCase();

  //alert(randcode);

  if(phone == "" || isNaN(phone))

  {

    alert("请输入正确的手机号");

  }

  else

  {

    if(phone.length !=11)

    {

       alert("请输入正确的手机号");

    }



    else

    {

      var post_method="number="+phone+"&vcode="+randcode;

      xml.open("POST","../sendsms/sendvcode.php",true);

      xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");

    xml.onreadystatechange=function()

      {

      if (xml.readyState==4 && xml.status==200)

      {

          if(xml.responseText == -1)

        {

            alert("请填写正确的验证码再发送短信验证码");

        }

        else

        {

          var p1=document.getElementById("phonemsg");

           p1.style.color="red";

          if(xml.responseText == 0)

          {

           p1.innerHTML="该手机号已经注册";

          }

          else  

          {



            if(xml.responseText == 1)      

            {

              

              p1.innerHTML="";

              var cObj = document.getElementById("tell");



              cObj.setAttribute("readOnly",'true');

              alert("发送成功");



            }



            else 

            {

              alert(xml.responseText);

            }

          }

            



        }

        

      }

      }

    

      xml.send(post_method);



    }

      

  }

  

}





function regist()

{

  

  var tellcode=password1=eval(document.getElementById("sms")).value;

  var username=password1=eval(document.getElementById("username")).value;

  var password1=eval(document.getElementById("pwd1")).value;

  var password2=eval(document.getElementById("pwd2")).value;

  var phone=eval(document.getElementById("tell")).value;



  if(tellcode == "")

  {

    alert("请输入短信验证码");

  }

    else

    {

        if(password1 == "" || username == "")

        {

          alert("请输入密码或用户名");

        }

        else

        {



          var p1=document.getElementById("passwordmsg");

              p1.style.color="red";

          if(password1 != password2)

          {

                 p1.innerHTML="两次密码不一致";

          }

           else

           {

            

                p1.innerHTML="";

                tellcode=tellcode;

              //  alert(tellcode);

                var xml=createXMLHttpRequest();

                var post_method="username="+username+"&password="+password1+"&tellcode="+tellcode+"&phone="+phone;

                xml.open("POST","../regist.php",true);

                xml.setRequestHeader("Content-Type","application/x-www-form-urlencoded;");



               // alert(post_method);

             xml.onreadystatechange=function()

                {

                  if (xml.readyState==4 && xml.status==200)

                  {



                     if(xml.responseText == 0)

                       {

                        

                           alert("手机号或者手机验证码错误");



                       }

                      if(xml.responseText == 1)

                      {

                          var p1=document.getElementById("usernamemsg");

                          p1.style.color="red";

                          p1.innerHTML="该用户名已经注册";

                      }

                      //alert("aaa");



                         if(xml.responseText == 2)

                         {

                           //alert("注册成功"); 

                          window.location.href="../index.php";

                         }

//alert(xml.responseText);

                    if(xml.responseText == 3)

                    {



                      var p1=document.getElementById("phonemsg");

                          p1.style.color="red";

                          p1.innerHTML="该手机已经注册";

                    }

                    

                  }

                }

            

                xml.send(post_method);



           }

        }

    }

 

}



