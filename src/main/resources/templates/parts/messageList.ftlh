<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

<style>
    .tweet {
        display: flex;
        border-bottom: 1px solid #e6ecf0;
        margin-bottom: 2px;
        padding: 10px 15px;
    }

    .tweet:hover {
        background: rgba(0, 0, 0, 0.03);
        cursor: pointer;
    }

    .tweet a:hover {
        text-decoration: underline;
    }

    .tweet__author-logo {
        width: 49px;
        height: 49px;
        border-radius: 50%;
        margin-right: 10px;
    }

    .tweet__header {
        display: flex;
    }

    .tweet__author-name {
        font-size: 15px;
        font-weight: 700;
        margin-right: 5px;
    }

    .tweet__author-slug {
        color: #5b7083;
        font-size: 15px;
    }

    .tweet__publish-time {
        margin-left: 5px;
        color: #657786;
    }

    .card-img-top {
        width: 480px;
        height: 400px;
        object-fit: cover;
        border-radius: 10px;
    }

    .content-icons {
        display: flex;
        flex-flow: row nowrap;
        justify-content: space-evenly;
        align-items: flex-start;
        padding: 1rem 4rem 1rem 0;
    }

    .content-icons i {
        color: rgb(101, 119, 134);
        font-size: 1rem;
        cursor: pointer;

    }
    .content-icons i:last-child {
        font-size: 14px;
    }

    .content-icons .blue:hover {
        color: rgba(29, 161,242,1.00);
    }
    .content-icons .green:hover {
        color: rgb(23 191 99);
    }
    .content-icons .red:hover {
        color: rgb(224, 36, 94);
    }

    .user-content {
        font-family: "Roboto";
        padding-right: 1rem;
        color: rgb(48, 52, 56);
        font-weight: 400;
        line-height: 1.5;
    }
    .user-content span {
        word-break: break-all;
    }
    .user-content a {
        color: rgb(27, 149, 224);
    }
</style>

<#if messages??>
    <#list messages as message>
        <div class="tweet">
            <img class="tweet__author-logo" src="/images/man.jpg"/>
            <div class="tweet__main">
                <div class="tweet__header">
                    <div class="tweet__author-name">
                        <#if user.username != message.authorName>
                            <strong><a style="color: #14171a"
                                       href="/api/v1/user/user-message/${message.author.id}">${message.authorName}</a></strong>
                        <#else>
                            <strong><a style="color: #14171a"
                                       href="/api/v1/user/profile">${message.authorName}</a></strong>
                        </#if>
                    </div>
                    <div class="tweet__author-slug">
                        @author-slug
                    </div>
                    <div class="tweet__publish-time">
                        <#if message.posted??>
                            <span>${message.posted?datetime("yyyy-MM-dd'T'HH:mm")?string("yyyy-MM-dd HH:mm")}</span>
                        <#else>
                            No date
                        </#if>
                    </div>
                </div>

                <div class="user-content">
                    <span>${message.text}</span>
                    <br>
                    <a style="color: dodgerblue">${message.tag}</a>
                    <div>
                        <#if message.filename??>
                            <img class="card-img-top" src="/img/${message.filename}" alt="Card image cap">
                        </#if>
                    </div>
                </div>

                <div class="content-icons">
                    <a href="/api/v1/message/messages/${message.id}/like">
                        <#if message.isLiked>
                            <i class="fa-solid fa-heart" style="color: red;">   ${message.likes}</i>
                        <#else>
                            <i class="fa-solid fa-heart red">   ${message.likes}</i>
                        </#if>
                    </a>
                    <a href="/api/v1/message/messages/${message.id}/saved">
                        <#if message.isSaved>
                            <i class="fa-solid fa-bookmark" style="color: rgba(29, 161,242,1.00)">   ${message.saves}</i>
                        <#else>
                            <i class="fa-solid fa-bookmark blue">   ${message.saves}</i>
                        </#if>
                    </a>
                    <a href="/api/v1/message/messages/${message.id}/repost">
                        <#if message.isReposted>
                            <i class="fa-solid fa-retweet" style="color: green;">   ${message.reposts}</i>
                        <#else>
                            <i class="fa-solid fa-retweet green">   ${message.reposts}</i>
                        </#if>
                    </a>
                </div>
            </div>
        </div>
    </#list>
<#else>
    <b>No messages to display.</b>
</#if>
<script>
    var textElement = document.getElementById('message-text');
    var text = textElement.innerText;

    if (text.length > 100) {
        text = text.substring(0, 100) + "\n" + text.substring(100);
    }

    textElement.innerText = text;
</script>