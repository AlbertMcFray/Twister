<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
      crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<!-- Recaptcha -->
<script src="https://www.google.com/recaptcha/api.js"></script>
    <style>
        .form-control {
            border-width: 2px;
            border-color: black;
            padding-left: 40px
        }

        .input-container {
            position: relative;
        }

        .input-container i {
            position: absolute;
            top: 50%;
            left: 10px;
            transform: translateY(-50%);
        }

        .forgetpass {
            position: relative;
            float: right;
            right: 28px;
        }

        .dnthave {
            position: absolute;
            margin-top: 65px;
            margin-left: 375px;
        }

        .btn1 {
            border: 0;
            background: #7f5feb;
            color: #dfdeee;
            border-radius: 100px;
            width: 350px;
            height: 40px;
            font-size: 16px;
            position: absolute;
            transition: 0.3s;
            cursor: pointer;
            margin-top: 45px;
            margin-left: 295px;
            margin-right: auto;
        }

        .rmb {
            position: absolute;
            margin-left: 0.5%;
            margin-top: -5px;
            color: black;
            font-size: 15px;
        }

        .login-container {
            width: 1000px;
            height: 345px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 100px;
        }

    </style>
<div class="login-container">
    <h1 style="text-align: center">Login</h1>
    <#if messageSuccess?has_content>
        <div class="alert alert-success" role="alert">
            ${messageSuccess}
        </div>
    </#if>
    <#if errorMessage?has_content>
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </#if>
    <form action="/login" method="post">
        <div class="input-container">
            <i class="fa-solid fa-envelope"></i>
            <input type="email" class="form-control" placeholder="your@email.example" id="email" required name="email">
        </div>
        <br>
        <div class="input-container">
            <i class="fa-solid fa-key"></i>
            <input type="password" class="form-control" placeholder="Password" id="password" required name="password">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"><br>
        <label>
            <input type="checkbox">
            <span></span>
            <small class="rmb" for="remember-me">Remember me</small>
        </label>
        <a href="/api/v1/registration/sendResetCode" class="forgetpass">Forget Password?</a>
        <input type="submit" value="Sign in" class="btn1">
    </form>
    <a href="/api/v1/registration/register" class="dnthave">Don’t have an account? Sign up</a>
</div>