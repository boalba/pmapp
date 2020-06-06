<%--
  Created by IntelliJ IDEA.
  User: maciej
  Date: 31.05.2020
  Time: 18:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PMApplication</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!--===============================================================================================-->
    <link rel="icon" type="image/png" href="loginPage/images/icons/mw.ico"/>
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/vendor/bootstrap/css/bootstrap.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/vendor/animate/animate.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/vendor/css-hamburgers/hamburgers.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/vendor/select2/select2.min.css">
    <!--===============================================================================================-->
    <link rel="stylesheet" type="text/css" href="loginPage/css/util.css">
    <link rel="stylesheet" type="text/css" href="loginPage/css/main.css">
    <!--===============================================================================================-->
</head>
<body>

<div class="limiter">
    <div class="container-login100" style="background-image: url('/loginPage/images/construction.jpg');">
        <div class="wrap-login100 p-t-190 p-b-30">
            <form class="login100-form validate-form">
                <div class="login100-form-avatar">
                    <img src="loginPage/images/logo.jpg" alt="LOGO">
                </div>

                <span class="login100-form-title p-t-20 p-b-45">
						Project Management Application
					</span>

                <div class="wrap-input100 validate-input m-b-10" data-validate = "Username is required">
                    < data base
                    input class="input100" type="text" name="username" placeholder="Username">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-user"></i>
						</span>
                </div>

                <div class="wrap-input100 validate-input m-b-10" data-validate = "Password is required">
                    <input class="input100" type="password" name="pass" placeholder="Password">
                    <span class="focus-input100"></span>
                    <span class="symbol-input100">
							<i class="fa fa-lock"></i>
						</span>
                </div>

                <div class="container-login100-form-btn p-t-10">
                    <button class="login100-form-btn">
                        Login
                    </button>
                </div>

                <div class="text-center w-full p-t-25 p-b-230">
                </div>

                <div class="text-center w-full">
                    <span class="txt1" href="#">
                        by Boalba 2020
                    </span>
                </div>
            </form>
        </div>
    </div>
</div>




<!--===============================================================================================-->
<script src="loginPage/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="loginPage/vendor/bootstrap/js/popper.js"></script>
<script src="loginPage/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="loginPage/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
<script src="loginPage/js/main.js"></script>

</body>
</html>
