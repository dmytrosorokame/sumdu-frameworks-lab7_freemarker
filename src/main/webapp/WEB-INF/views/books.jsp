<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Каталог книг</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background: #f8f9fa;
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 2rem;
        }
        .toolbar {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            gap: 1rem;
        }
        .search-form {
            display: flex;
            flex-wrap: wrap;
            gap: 0.5rem;
            align-items: center;
        }
        .search-form input[type="text"] {
            min-width: 260px;
            padding: 0.4rem 0.6rem;
            border-radius: 4px;
            border: 1px solid #ced4da;
            font-size: 0.9rem;
        }
        .search-form input[type="number"],
        .search-form select {
            padding: 0.4rem 0.6rem;
            border-radius: 4px;
            border: 1px solid #ced4da;
            font-size: 0.9rem;
        }
        .search-form button {
            padding: 0.4rem 0.9rem;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 0.9rem;
        }
        .search-form button:hover {
            background: #0056b3;
        }
        .pagination {
            display: flex;
            align-items: center;
            gap: 0.5rem;
            font-size: 0.9rem;
        }
        .pagination a {
            padding: 0.3rem 0.7rem;
            border-radius: 4px;
            border: 1px solid #ced4da;
            text-decoration: none;
            color: #007bff;
        }
        .pagination a.disabled {
            pointer-events: none;
            opacity: 0.5;
            cursor: default;
        }
        .pagination span.current-page {
            font-weight: bold;
        }
        .book-list {
            list-style: none;
            padding: 0;
        }
        .book-item {
            padding: 1.5rem;
            margin: 1rem 0;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .book-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.15);
        }
        .book-item a {
            text-decoration: none;
            color: inherit;
            display: block;
        }
        .book-title {
            font-size: 1.3rem;
            font-weight: bold;
            color: #007bff;
            margin-bottom: 0.5rem;
        }
        .book-author {
            color: #666;
            font-size: 1rem;
        }
        .book-year {
            color: #999;
            font-size: 0.9rem;
            margin-top: 0.3rem;
        }
        .empty-state {
            text-align: center;
            color: #999;
            padding: 3rem;
            background: white;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <h1>📚 Каталог книг</h1>

    <div class="toolbar">
        <form class="search-form" method="get" action="${pageContext.request.contextPath}/books">
            <input type="text" name="q" placeholder="Пошук за назвою або автором"
                   value="${fn:escapeXml(q)}"/>
            <select name="sort">
                <option value="" ${sort == null ? "selected" : ""}>Default</option>
                <option value="title" ${"title" == sort ? "selected" : ""}>By title</option>
                <option value="author" ${"author" == sort ? "selected" : ""}>By author</option>
                <option value="pub_year" ${"pub_year" == sort ? "selected" : ""}>By publication year</option>
            </select>
            <span>Size:</span>
            <select name="size">
                <option value="5" ${size == 5 ? "selected" : ""}>5</option>
                <option value="10" ${size == 10 ? "selected" : ""}>10</option>
                <option value="20" ${size == 20 ? "selected" : ""}>20</option>
                <option value="50" ${size == 50 ? "selected" : ""}>50</option>
            </select>
            <button type="submit">Застосувати</button>
        </form>

        <c:if test="${totalPages > 0}">
            <div class="pagination">
                <c:set var="prevPage" value="${page - 1}"/>
                <c:set var="nextPage" value="${page + 1}"/>
                <a class="${page <= 0 ? 'disabled' : ''}"
                   href="${pageContext.request.contextPath}/books?page=${prevPage}&size=${size}&sort=${sort}&q=${q}">← Попередня</a>
                <span class="current-page">Сторінка ${page + 1} з ${totalPages}</span>
                <a class="${page + 1 >= totalPages ? 'disabled' : ''}"
                   href="${pageContext.request.contextPath}/books?page=${nextPage}&size=${size}&sort=${sort}&q=${q}">Наступна →</a>
            </div>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${empty books}">
            <div class="empty-state">
                <p>Книг поки немає.</p>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="book-list">
            <c:forEach var="book" items="${books}">
                    <li class="book-item">
                        <a href="${pageContext.request.contextPath}/books/${book.id}">
                            <div class="book-title">${book.title}</div>
                            <div class="book-author">Автор: ${book.author}</div>
                            <div class="book-year">Рік видання: ${book.pubYear}</div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
</body>
</html>

