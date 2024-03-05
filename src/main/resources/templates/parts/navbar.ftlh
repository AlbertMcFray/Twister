<head>
    <style>
        .navbar-nav {
            display: flex;
            justify-content: flex-end;
            flex-grow: unset;
        }

        .nav-item {
            margin: 0 10px;
        }

        .navbar-brand {
            font-size: 25px;
        }

        .nav-link {
            font-size: 20px;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">Twister</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="/api/v1/message/home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/api/v1/user">User list</a>
                </li>
            </ul>
            <#list users as user>
            <#if user.email??>
                <div class="navbar-text">${user.username}</div>
            <#else>
                <button class="btn btn-light my-2 my-sm-0" type="button" onclick="window.location.href = '/login'">
                    Login
                </button>
            </#if>
            </#list>
        </div>
    </div>
</nav>

