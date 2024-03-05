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
        /* CSS for the icon and input container */
        .input-container {
            position: relative;
        }

        .input-container i {
            position: absolute;
            top: 50%;
            left: 10px;
            transform: translateY(-50%);
        }

        .btn2 {
            border:0;
            background: #7f5feb;
            color: #dfdeee;
            border-radius: 100px;
            width: 350px;
            height: 40px;
            font-size: 16px;
            position: absolute;
            transition: 0.3s;
            cursor: pointer;
            margin-top: 25px;
            margin-left: 300px;
        }

        .register-container {
            width: 1000px;
            height: 585px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 100px;
        }
    </style>
<div class="register-container">
    <h1 style="text-align: center">Create Account</h1>
    <#if message??>
        <div class="alert alert-danger">${message}</div>
    </#if>
    <#if captchaError??>
        <div class="alert alert-danger" role="alert">
            ${captchaError}
        </div>
    </#if>
    <hr>
    <form action="/api/v1/registration/register" method="post">
        <div class="form-group input-container">
            <i class="fa-solid fa-user"></i>
            <input type="text" class="form-control"
                   placeholder="Username" required id="username" name="username" >
        </div><br>
        <div class="form-group input-container">
            <i class="fa-solid fa-envelope"></i>
            <input type="email" class="form-control"
                   placeholder="your@email.example" id="email" required name="email">
        </div><br>
        <div class="form-group input-container">
            <i class="fa-solid fa-key"></i>
            <input type="text" class="form-control"
                   placeholder="Password" id="password" required name="password">
        </div><br>
        <div class="form-group input-container">
            <i class="fa-solid fa-key"></i>
            <input type="text" class="form-control"
                   placeholder="Password retype" id="password" required name="password2">
        </div><br>
        <div class="g-recaptcha" data-sitekey="6LchdnYpAAAAAJFx5auYmxbF5KcY5uhaQcXNfUoQ"></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" class="btn2">Register</button>
    </form>
</div>
