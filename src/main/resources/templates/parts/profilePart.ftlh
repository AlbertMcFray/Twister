
<div>
    <h2>${user.username}</h2>
    <br>
    <#if IsCurrentUser>
    <#else>
        <#if isSubscriber>
            <a class="btn btn-info" href="/api/v1/user/unsubscribe/${user.id}">Unsubscribe</a>
        <#else >
            <a class="btn btn-info" href="/api/v1/user/subscribe/${user.id}">Subscribe</a>
        </#if>
    </#if>
    <br>
    Email: <b>${user.email}</b>
    <br>
    <#if user.created??>
        Registration: <b>${(user.created?date("yyyy-MM-dd"))?string("MMMM dd, yyyy")}</b>
    <#else>
        Registration: Not available
    </#if>
    <br>
    <a href="/api/v1/user/subscribers/${user.id}/list"> Subscribers:<b>${subscribers}</b></a>
    <a href="/api/v1/user/subscriptions/${user.id}/list"> Subscriptions:${subscriptions}</a>
    <br>
    <#if IsCurrentUser>
        <a href="/api/v1/user/reposted">Reposts</a>
        <br>
        <a href="/api/v1/user/saved">Saved</a>
    <#else>
        <a href="/api/v1/user/user-message/reposted/${user.id}">Reposts</a>
    </#if>
    <br>
    <#if IsCurrentUser>
        <form action="/logout" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="userId" value="${user.id}"/>
            <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
                Logout
            </button>
        </form>
    </#if>
</div>
