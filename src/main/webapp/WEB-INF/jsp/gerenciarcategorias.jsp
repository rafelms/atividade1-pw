<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gerenciar Categorias</title>
</head>
<body>

<h1>Painel de Controle - Categorias</h1>
<p>Bem-vindo, ${sessionScope.usuarioLogado.nome}!</p>
<a href="index.jsp">Voltar para a Home</a>
<hr>

<c:if test="${not empty mensagemErro}">
    <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>

<h2>
    <c:choose>
        <c:when test="${not empty catParaEditar}">
            Editar Categoria: ${catParaEditar.nome}
        </c:when>
        <c:otherwise>
            Cadastrar Nova Categoria
        </c:otherwise>
    </c:choose>
</h2>

<form action="gerenciarcategorias" method="post">
    <input type="hidden" name="id" value="${catParaEditar.id}">

    <label>
        Nome da Categoria:
        <input type="text" name="nome" value="${catParaEditar.nome}" required placeholder="Ex: Trabalho, Estudos">
    </label>
    <input type="submit" value="${not empty catParaEditar ? 'Salvar Alterações' : 'Cadastrar'}">

    <c:if test="${not empty catParaEditar}">
        <a href="gerenciarcategorias">Cancelar Edição</a>
    </c:if>
</form>

<hr>

<h2>Categorias Cadastradas</h2>
<table border="1" cellpadding="8">
    <thead>
    <tr>
        <th>ID</th>
        <th>Nome</th>
        <th>Ações</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="categoria" items="${listaCategorias}">
        <tr>
            <td>${categoria.id}</td>
            <td>${categoria.nome}</td>
            <td>
                <a href="gerenciarcategorias?acao=editar&id=${categoria.id}">Editar</a> |
                <a href="gerenciarcategorias?acao=remover&id=${categoria.id}"
                   onclick="return confirm('Tem certeza que deseja remover esta categoria?');">Remover</a>
            </td>
        </tr>
    </c:forEach>
    <c:if test="${empty listaCategorias}">
        <tr>
            <td colspan="3" style="text-align: center;">Nenhuma categoria encontrada.</td>
        </tr>
    </c:if>
    </tbody>
</table>

</body>
</html>