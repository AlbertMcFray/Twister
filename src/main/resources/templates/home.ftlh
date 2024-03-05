<#import "parts/common.ftlh" as c>
<@c.page>
    <style>
        .side-left {
            padding-left: 315px;
        }

        .post-tweet {
            background: #fff;
            border-bottom: 10px solid rgb(230, 236, 240);
        }

        .form-group-1 {
            display: flex;
            flex-flow: row;
            padding: 0.3rem 1rem;
        }

        .post-tweet img {
            display: inline-block;
            margin-right: 5px;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            align-self: center;
        }

        .post-tweet input {
            width: 90%;
            height: 60px;
            padding: 0.5rem 1rem;
            border: none;
            font-size: 1.2rem;
            font-weight: 300;
        }

        .post-tweet input::placeholder {
            color: rgba(123, 124, 126, 0.719);
        }

        .post-tweet input:focus {
            outline: none;
        }

        .form-group-2 {
            display: flex;
            flex-flow: row;
            justify-content: space-between;
            align-items: center;
            padding-bottom: 0.8rem;
        }

        .post-icons {
            padding-left: 4rem;
            color: #1da1f2;
        }

        .post-icons i {
            padding-right: 10px;
            font-size: 1rem;
        }
        .post-icons label {
            padding-right: 26px;
            font-size: 1rem;
        }

        .post-icons i:hover {
            color: #1da0f2c2;
            cursor: pointer;
        }

        .form-group-2 .btn {
            border: none;
            font-weight: 600;
            font-size: 1rem;
            padding: 0.3rem 1rem;
            background: #8ed0f9;
            color: #fff;
            border-radius: 25px;
            margin-right: 0.8rem;
            width: 120px;
            height: 30px;
        }

        .form-group-2 .btn:hover {
            background: #1da1f2;
            cursor: pointer;

        }

        .btn:focus {
            outline: none;
        }

        hr {
            margin-left: 60px;
            margin-right: 20px;
            margin-bottom: 5px;
        }

    </style>

    <div class="side-left">
        <div class="post-tweet">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group-1">
                    <img class="tweet__author-logo" src="/images/man.jpg"/>
                    <input class="form-control ${(textError??)?string('is-invalid', '')}"
                           name="text" placeholder="What's happening?">
                    </input>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <hr>
                <div class="form-group-2">
                    <div class="post-icons">
                        <label for="file-upload" class="fa-solid fa-image"></label>
                        <input id="file-upload" type="file" name="file" style="display: none;">
                        <i class="fa-regular fa-face-smile"></i>
                        <i class="fa-solid fa-bars"></i>
                        <i class="fa-solid fa-calendar-days"></i>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button type="submit" class="btn">Publish post</button>
                </div>
            </form>
        </div>
        <br>
        <#include "parts/messageList.ftlh">
    </div>
</@c.page>
