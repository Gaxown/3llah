<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord - Médecin Généraliste</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
        }
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .header h1 { font-size: 24px; margin-bottom: 5px; }
        .header p { font-size: 14px; opacity: 0.9; }
        .nav {
            background: white;
            padding: 15px 20px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            display: flex;
            gap: 20px;
        }
        .nav a {
            text-decoration: none;
            color: #667eea;
            font-weight: 500;
            transition: color 0.3s;
        }
        .nav a:hover { color: #764ba2; }
        .container { max-width: 1200px; margin: 30px auto; padding: 0 20px; }
        .welcome-box {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .logout-btn {
            background: #dc3545;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-left: auto;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>🏥 Système de Télé-Expertise Médicale</h1>
        <p>Tableau de bord - Médecin Généraliste</p>
    </div>

    <div class="nav">
        <a href="${pageContext.request.contextPath}/generalist/dashboard">Accueil</a>
        <a href="${pageContext.request.contextPath}/generalist/consultations">Consultations</a>
        <form action="${pageContext.request.contextPath}/logout" method="get" style="margin-left: auto;">
            <button type="submit" class="logout-btn">Déconnexion</button>
        </form>
    </div>

    <div class="container">
        <div class="welcome-box">
            <h2>Bienvenue, Dr. ${user.fullName} 👨‍⚕️</h2>
            <p style="margin-top: 10px; color: #666;">Tableau de bord médecin généraliste - En construction</p>
        </div>
    </div>
</body>
</html>

