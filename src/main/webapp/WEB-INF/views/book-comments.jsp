<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="uk">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${book.title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            background: #f8f9fa;
        }
        h1, h2 {
            color: #333;
        }
        .book-info {
            background: white;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 2rem;
        }
        .book-title {
            font-size: 1.8rem;
            font-weight: bold;
            color: #007bff;
            margin-bottom: 0.5rem;
        }
        .book-meta {
            color: #666;
            font-style: italic;
        }
        .back-link {
            display: inline-block;
            margin-top: 1rem;
            color: #007bff;
            text-decoration: none;
            font-weight: 500;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        form {
            margin-bottom: 2rem;
            padding: 1.5rem;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 1rem;
        }
        label {
            display: block;
            margin-bottom: 0.3rem;
            color: #555;
            font-weight: 500;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 0.6rem;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }
        textarea {
            min-height: 100px;
            resize: vertical;
        }
        input:focus, textarea:focus {
            outline: none;
            border-color: #007bff;
        }
        button {
            padding: 0.6rem 1.5rem;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 15px;
            font-weight: 500;
        }
        button:hover {
            background: #0056b3;
        }
        button:active {
            transform: scale(0.98);
        }
        button.delete-btn {
            padding: 0.4rem 1rem;
            background: #dc3545;
            font-size: 13px;
        }
        button.delete-btn:hover {
            background: #c82333;
        }
        .comments-list {
            list-style: none;
            padding: 0;
        }
        .comment-item {
            padding: 1.2rem;
            margin: 0.8rem 0;
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .comment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 0.6rem;
        }
        .comment-author {
            font-weight: bold;
            color: #007bff;
            font-size: 16px;
        }
        .comment-date {
            font-size: 0.85em;
            color: #666;
            margin-left: 10px;
        }
        .comment-text {
            color: #333;
            line-height: 1.5;
        }
        .empty-state {
            text-align: center;
            color: #999;
            padding: 2rem;
            background: white;
            border-radius: 8px;
        }
        .comments-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 1rem;
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
    </style>
</head>
<body>
    <div class="book-info">
        <div class="book-title">📖 ${book.title}</div>
        <p class="book-meta">Автор: ${book.author}, Рік: ${book.pubYear}</p>
        <a href="${pageContext.request.contextPath}/books" class="back-link">← Повернутись до каталогу</a>
    </div>

    <div class="comments-header">
        <h2>💬 Коментарі</h2>
        <c:if test="${totalPages > 0}">
            <div class="pagination">
                <c:set var="prevPage" value="${page - 1}"/>
                <c:set var="nextPage" value="${page + 1}"/>
                <a class="${page <= 0 ? 'disabled' : ''}"
                   href="${pageContext.request.contextPath}/books/${book.id}?page=${prevPage}&size=${size}">← Попередня</a>
                <span class="current-page">Сторінка ${page + 1} з ${totalPages}</span>
                <a class="${page + 1 >= totalPages ? 'disabled' : ''}"
                   href="${pageContext.request.contextPath}/books/${book.id}?page=${nextPage}&size=${size}">Наступна →</a>
            </div>
        </c:if>
    </div>
    
    <c:choose>
        <c:when test="${empty comments}">
            <div class="empty-state">
                <p>Ще немає коментарів. Будьте першим!</p>
            </div>
        </c:when>
        <c:otherwise>
            <ul class="comments-list">
                <c:forEach var="c" items="${comments}">
                    <li class="comment-item">
                        <div class="comment-header">
                            <div>
                                <span class="comment-author">${c.author}</span>
                                <span class="comment-date">${c.createdAt}</span>
                            </div>
                            <form method="post" action="${pageContext.request.contextPath}/books/${book.id}" style="display:inline; padding:0; margin:0; background:none; box-shadow:none;">
                                <input type="hidden" name="commentId" value="${c.id}">
                                <input type="hidden" name="_method" value="delete">
                                <button type="submit" class="delete-btn">Видалити</button>
                            </form>
                        </div>
                        <div class="comment-text">${c.text}</div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>

    <h2>✍️ Додати коментар</h2>
    <form method="post" action="${pageContext.request.contextPath}/books/${book.id}">
        <div class="form-group">
            <label for="author">Ваше ім'я:</label>
            <input type="text" id="author" name="author" placeholder="Введіть ваше ім'я" required maxlength="64">
        </div>
        <div class="form-group">
            <label for="text">Коментар:</label>
            <textarea id="text" name="text" placeholder="Напишіть ваш коментар" required maxlength="1000"></textarea>
        </div>
        <button type="submit">Додати коментар</button>
    </form>
</body>
</html>

