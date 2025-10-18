<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rechercher un Patient</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
        }
        .search-form {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 16px;
        }
        .btn-primary {
            background: #007bff;
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
        }
        .btn-primary:hover {
            background: #0056b3;
        }
        .btn-secondary {
            background: #6c757d;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            display: inline-block;
            margin-top: 15px;
        }
        .btn-secondary:hover {
            background: #5a6268;
        }
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            border: 1px solid #f5c6cb;
        }
        h1 {
            color: #333;
            margin-bottom: 10px;
        }
        .subtitle {
            color: #6c757d;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔍 Rechercher un Patient</h1>
        <p class="subtitle">Étape 1: Entrez le numéro de sécurité sociale du patient</p>

        <c:if test="${not empty error}">
            <div class="alert-error">
                ${error}
            </div>
        </c:if>

        <div class="search-form">
            <form method="post" action="${pageContext.request.contextPath}/nurse/patients/search">
                <div class="form-group">
                    <label for="ssn">Numéro de Sécurité Sociale *</label>
                    <input type="text"
                           id="ssn"
                           name="ssn"
                           required
                           placeholder="Ex: 1234567890123"
                           pattern="[0-9]{13,15}"
                           title="Le numéro de sécurité sociale doit contenir entre 13 et 15 chiffres">
                </div>

                <button type="submit" class="btn-primary">Rechercher</button>
            </form>

            <a href="${pageContext.request.contextPath}/nurse/dashboard" class="btn-secondary">
                ← Retour au tableau de bord
            </a>
        </div>
    </div>
</body>
</html>

