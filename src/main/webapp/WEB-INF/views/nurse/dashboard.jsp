<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de bord Infirmier</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .dashboard-container {
            max-width: 1200px;
            margin: 40px auto;
            padding: 20px;
        }
        .welcome-section {
            background: #f8f9fa;
            padding: 30px;
            border-radius: 8px;
            margin-bottom: 30px;
        }
        .action-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .card {
            background: white;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 25px;
            text-align: center;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .card h3 {
            color: #007bff;
            margin-bottom: 15px;
        }
        .card p {
            color: #6c757d;
            margin-bottom: 20px;
        }
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.2s;
        }
        .btn:hover {
            background: #0056b3;
        }
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
        }
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .logout-btn {
            background: #dc3545;
            padding: 10px 20px;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .logout-btn:hover {
            background: #c82333;
        }
    </style>
</head>
<body>
    <div class="dashboard-container">
        <div class="header">
            <h1>Tableau de bord Infirmier</h1>
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
        </div>

        <c:if test="${not empty sessionScope.successMessage}">
            <div class="alert alert-success">
                ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <div class="welcome-section">
            <h2>Bienvenue, ${sessionScope.userFullName}</h2>
            <p>Gérez l'accueil des patients et consultez la liste des patients enregistrés</p>
        </div>

        <div class="action-cards">
            <!-- US1: Accueil du patient -->
            <div class="card">
                <h3>📋 Accueil du Patient</h3>
                <p>Rechercher un patient existant ou enregistrer un nouveau patient avec ses signes vitaux</p>
                <a href="${pageContext.request.contextPath}/nurse/patients/search" class="btn">Commencer l'accueil</a>
            </div>

            <!-- US2: Liste des patients -->
            <div class="card">
                <h3>👥 Liste des Patients</h3>
                <p>Voir la liste des patients enregistrés aujourd'hui avec leurs informations</p>
                <a href="${pageContext.request.contextPath}/nurse/patients/list" class="btn">Voir la liste</a>
            </div>
        </div>
    </div>
</body>
</html>

