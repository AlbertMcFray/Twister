<style>
    .search-box {
        border: none;
        border-radius: 25px;
        padding: 0.1rem 1rem;
        background: rgb(230, 236, 240);
        margin-top: 0.5rem;
        white-space: nowrap;
        margin-left: 40px;
        position: fixed;
    }
    .search-box i {
        color: rgb(101, 119, 134);
        font-size: 0.9rem;
    }
    .search-box .search-input {
        padding: 0.8rem 8rem 0.8rem 0.2rem;
        border-radius: 25px;
        outline: none;
        border: none;
        font-size: 0.9rem;
        background: rgb(230, 236, 240);
    }

    .search-input-group {
        display: flex;
        align-items: center;
    }

    .fa-solid.fa-magnifying-glass {
        margin-right: 6px;
    }

</style>

<div class="right-flex-container flex-item">
    <div class="search-box">
        <div class="search-input-group">
        <i class="fa-solid fa-magnifying-glass"></i>
        <form method="get" action="/api/v1/message/home" class="form-inline">
            <input type="text" name="filter" placeholder="Search by tag" value="${filter!}" class="search-input">
            <button type="submit" class="btn" style="display: none;"></button>
        </form>
        </div>
    </div>
</div>
