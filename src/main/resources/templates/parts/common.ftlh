<#macro page>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            --twitter-color: #50b7f5;
            --twitter-background: #e6ecf0;
        }

        .sidebarOption {
            display: flex;
            align-items: center;
            cursor: pointer;
            margin-bottom: 20px;
        }

        .sidebarOption .fa-regular, .fa-solid,
        .fa-cloudflare {
            padding: 20px;
        }

        .sidebarOption h2 {
            font-weight: 800;
            font-size: 20px;
            margin-right: 20px;
        }

        .sidebarOption:hover {
            background-color: var(--twitter-background);
            border-radius: 30px;
            color: var(--twitter-color);
            transition: color 100ms ease-out;
        }

        .sidebarOption.active {
            color: var(--twitter-color);
        }

        body {
            display: flex;
            height: 100vh;
            max-width: 1300px;
            margin-left: auto;
            margin-right: auto;
            padding: 0 10px;
        }

        .sidebar {
            position: fixed;
            border-right: 1px solid var(--twitter-background);
            flex: 0.2;
            min-width: 250px;
            margin-left: 240px;
            padding-left: 20px;
            padding-right: 20px;
            height: 100vh;
        }

        .fa-cloudflare {
            color: var(--twitter-color);
            font-size: 30px;
        }

        .sidebar a {
            color: inherit;
            text-decoration: none;
        }

        .sidebar a:hover {
            color: inherit;
            text-decoration: none;
        }

        .mainContent{
            width: 70%;
            margin-left: 175px;
            flex-shrink: 0;
        }

        .rightSidebar{
            border-left: 1px solid var(--twitter-background);
            padding-right: 20px;
            height: 100vh;
            position: relative;
            margin-left: 1px;
        }

        .profile-box {
            display: flex;
            flex-flow: row nowrap;
            justify-content: center;
            align-items: center;
            margin-top: 19rem;
            align-self: center;
            padding: 0 0.6rem;
        }
        .profile-box:hover {
            background: rgba(29, 161, 242, 0.1);
            border-radius: 25px;
            cursor: pointer;
        }

        .profile-box img {
            display: block;
            width: 45px;
            height: 45px;
            margin: auto;
            border-radius: 100%;
            margin-right: 0.5rem;
        }

        .profile-box .full-name {
            font-weight: 600;
            font-size: 1.1rem;
        }

        .full-name, .user-name {
            margin: 1px 0;
        }

    </style>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Twister</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
              crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
        <!-- Recaptcha -->
        <script src="https://www.google.com/recaptcha/api.js"></script>
    </head>
    <body>

    <! -- sidebar starts -->
    <div class="sidebar">
        <i class="fa-brands fa-cloudflare"></i>
        <div class="sidebarOption active">
            <i class="fa-solid fa-house"></i>
            <h2><a href="/">Twister</a></h2>
        </div>
        <div class="sidebarOption">
            <i class="fa-regular fa-message"></i>
            <h2><a href="/api/v1/message/home">Messages</a></h2>
        </div>
        <#if user??>
            <#if user.isEnabled()>
                <#if user.username??>
                    <div class="sidebarOption">
                        <i class="fa-regular fa-user"></i>
                        <h2><a href="/api/v1/user/profile">Profile</a></h2>
                    </div>
                </#if>
            </#if>
        <#else>
            <div class="sidebarOption">
                <i class="fa-regular fa-user"></i>
                <h2><a href="/login">Login</a></h2>
            </div>
        </#if>
        <div class="sidebarOption">
            <i class="fa-regular fa-bookmark"></i>
            <h2><a href="/api/v1/user/saved">Saved</a></h2>
        </div>
        <div class="sidebarOption">
            <i class="fa-solid fa-retweet"></i>
            <h2><a href="/api/v1/user/reposted">Reposts</a></h2>
        </div>
        <div class="sidebarOption">
            <i class="fa-solid fa-magnifying-glass"></i>
            <h2>Search</h2>
        </div>
        <#if user??>
            <#if user.isAdmin()>
                <div class="sidebarOption">
                    <i class="fa-regular fa-rectangle-list"></i>
                    <h2><a href="/api/v1/admin">User list</a></h2>
                </div>
            </#if>
        </#if>
        <div class="profile-box">
            <img  src="/images/man.jpg" alt="profile">
            <div class="profile-link">
                <#if user??>
                <p class="full-name">${user.username}</p>
                <p class="user-name">@king.david</p>
                </#if>
            </div>
        </div>
    </div>

    <! -- sidebar ends -->

    <div class="mainContent">
        <#nested>
    </div>

    <div class="rightSidebar">
        <#include "rightSidebar.ftlh">
    </div>


        <!-- Optional JS -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>

    </body>
    </html>
</#macro>
