<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
      integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
      crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
<style>
    .form-control {
        border-width: 2px;
        border-color: black;
    }
    .forgot-pass-container {
        width: 1000px;
        height: 540px;
        margin: 0 auto;
        padding: 20px;
        border: 1px solid #ccc;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        margin-top: 100px;
    }
</style>
    <div class="forgot-pass-container">
<h1 style="text-align: center">Reset password</h1>
<hr>
<div class="forgot">
    <h2>Forgot your password?</h2>
    <p>Change your password in three easy steps.</p>
    <li><span class="text-primary text-medium">1.</span>Enter your email address below.</li>
    <li><span class="text-primary text-medium">2.</span>The link will be sent to  your email.</li>
    <li><span class="text-primary text-medium">3.</span>Open the link to reset your password.</li>
</div>
<br>
<br>
<form action="/api/v1/registration/sendResetCode" method="post">
    <div>
        <div class="form-group">
            <label for="email-for-pass">Enter your email address</label> <input
                    class="form-control" type="text" placeholder="your@email.example" name="email" id="email" required=""><small
                    class="form-text text-muted">Enter the registered email address . Then we'll email an OTP to this address.</small>
        </div>
    </div>
    <div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"><br><br>
        <button class="btn btn-success" type="submit">Reset Password</button>
        <button class="btn btn-danger" type="submit">Back to Login</button>
    </div>
</form>
    </div>