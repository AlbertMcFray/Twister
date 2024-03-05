<style>
    /* Trending Nav Items */
    .nav-list {
        display: flex;
        flex-flow: row nowrap;
        justify-content: space-between;
        padding: 0.5rem 1rem;
        border-bottom: 1px solid rgb(230, 236, 240);
        width: 100%;
    }
    .nav-list:hover {
        background: rgba(0, 0, 0, 0.03);
        cursor: pointer;
    }
    .nav-list a {
        color: rgb(27, 149, 224);
    }
    .nav-list:last-child {
        font-size: 1rem;
        border-bottom: none;
    }
    .trend-list {
        display: flex;
        flex-flow: column nowrap;

    }

    .trends {
        background: rgb(245, 248, 250);
        display: flex;
        flex-flow: column nowrap;
        width: 189%;
        border-radius: 15px;
        margin-top: 6rem;
        margin-left: 40px;
    }
    .trends .header {
        display: flex;
        flex-flow: row nowrap;
        justify-content: space-between;
        align-items: center;
        padding-bottom: 0.3rem;
        border-bottom: 1px solid rgb(230, 236, 240);
        padding: 0.5rem 1rem;
        border-bottom: 1px solid rgb(230, 236, 240);
    }

    .header h2 {
        font-size: 1.2rem;
        font-weight: 800;
    }

    .header i {
        color: rgba(29,161,242,1.00);
        background: rgb(245, 248, 250);
        padding: 0.5rem;
        border-radius: 50%
    }
    .header i:hover {
        background: rgba(29, 161, 242, 0.1);
        padding: 0.5rem;
        border-radius: 50%;
        cursor: pointer;
    }

    .main-text {
        font-size: 15px;
        font-weight: bold;
        color: rgb(20, 23, 26);
    }

    .sub-text {
        font-size: 13px;
        color: rgb(101, 119, 134);
    }

    .nav-list .trend-icon {
        font-size: 13px;
        color:  rgb(101, 119, 134);
    }

    .footer-links a:hover {
        text-decoration: underline;
    }
    .footer-links i {
        padding: 0.4rem;
        border-radius: 50%;
    }
    .footer-links i:hover {
        background: rgb(245, 248, 250);
        padding: 0.4rem;
        border-radius: 50%;
        cursor: pointer;
    }
</style>

<#include "searchByTag.ftlh">

<div class="trends">
    <ul>
        <li class="nav-list header">
            <h2>Trends for you</h2>
            <i class="fas fa-cog">
            </i>
        <li class="nav-list">
            <div class="trend-list">
                <p class="sub-text">Trending</p>
                <p class="main-text">#TestBox</p>
                <p class="sub-text">274K testTag</p>
            </div>
            <div class="trend-icon">
                <i class="fas fa-chevron-down"></i>
            </div>
        </li>
    </ul>
</div>