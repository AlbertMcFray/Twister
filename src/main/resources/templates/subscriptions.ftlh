<#import "parts/common.ftlh" as c>
<#import "parts/pager.ftlh" as p>
<@c.page>
<div style="margin-left: 315px">
    <h2>${user.username}</h2>
    <div>${type}</div>
    <@p.pager url page/>
    <ul class="list-group">
        <#list page.content as userItem>
            <li class="list-group-item">
                <a href="/api/v1/user/user-message/${userItem.id}">${userItem.username}</a>
            </li>
        </#list>
    </ul>
    <@p.pager url page/>
</div>
</@c.page>
