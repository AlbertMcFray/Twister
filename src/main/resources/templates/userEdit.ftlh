<#import "parts/common.ftlh" as c>
<@c.page>
    User editor
    <form action="/api/v1/admin" method="post">
        <input type="text" value="${user.username}" name="username">
        <div>
            <label>Role:
                <select name="role">
                    <#list roles as role>
                        <option value="${role}" <#if user.roles?seq_contains(role)>selected</#if>>${role}</option>
                    </#list>
                </select>
            </label>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Save</button>
    </form>
</@c.page>