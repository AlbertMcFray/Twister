<#import "parts/common.ftlh" as c>
<@c.page>
<div style="margin-left: 315px">
    <#if user??>
        <#if user.username??>
            <h5>Hello, ${user.username}</h5>
        <#else>
            <h5>Hello, guest</h5>
        </#if>
    </#if>
    <div>Simple clone of Twitter</div>
</div>
</@c.page>
