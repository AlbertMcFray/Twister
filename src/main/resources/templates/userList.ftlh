<#import "parts/common.ftlh" as c>
<@c.page>
    <style>

    </style>
    <h1 style="text-align: center">Admin panel</h1><hr>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Username</th>
            <th scope="col">Email</th>
            <th scope="col">Is Active</th>
            <th scope="col">Role</th>
            <th scope="col">Ban</th>
            <th scope="col">Edit</th>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.username}</th>
                <th>${user.email}</th>
                <th><#if user.isEnabled()>True<#else>False</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th>
                    <form action="/api/v1/admin/ban/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" class="btn btn-light" value="Ban">
                    </form>
                </th>
                <th><a href="/api/v1/admin/${user.id}" style="color: green">Edit</a></th>
            </tr>
        <#else>
            <h3>No users</h3>
        </#list>
    </table>
</@c.page>