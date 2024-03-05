<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
      crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
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
            margin-left: 310px;
        }

        .change-pass-container {
            width: 1000px;
            height: 390px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 100px;
        }
    </style>
<div class="change-pass-container">
    <h1 style="text-align: center">Change Password</h1>
    <#if message??>
        <div class="alert alert-danger">${message}</div>
    </#if>
    <form action="/api/v1/registration/resetPassword" method="post">
        <div class="input-container">
            <i class="fa-solid fa-shield-halved"></i>
            <input type="text" class="form-control" placeholder="Reset code" id="resetCode" name="resetCode">
        </div>
        <br>
        <div class="input-container">
            <i class="fa-solid fa-key"></i>
            <input type="text" class="form-control" placeholder="Password" id="password" name="password">
        </div>
        <br>
        <div class="input-container">
            <i class="fa-solid fa-key"></i>
            <input type="text" class="form-control" placeholder="Repeat password" id="password" name="password2">
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"><br>
        <input type="submit" value="Submit" class="btn1">
    </form>
</div>