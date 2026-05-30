<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Minhas Tarefas</title>
</head>
<body>

<h2>Painel de Tarefas</h2>
<p>Bem-vindo(a), <strong>${sessionScope.usuarioLogado.nome}</strong>!</p>

<c:if test="${not empty mensagemErro}">
  <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>
<c:if test="${not empty mensagemSucesso}">
  <p style="color: green; font-weight: bold;">${mensagemSucesso}</p>
</c:if>

<hr>

<h3>Minhas Tarefas Cadastradas</h3>

<a href="cadastrartarefa"><button>+ Nova Tarefa</button></a>
<br><br>

<table border="1" cellpadding="8" cellspacing="0">
  <thead>
  <tr>
    <th>Título</th>
    <th>Descrição</th>
    <th>Data</th>
    <th>Categoria</th>
    <th>Ações</th>
  </tr>
  </thead>
  <tbody>
  <c:choose>
    <c:when test="${not empty listaTarefas}">
      <c:forEach var="tarefa" items="${listaTarefas}">
        <tr>
          <td>${tarefa.titulo}</td>
          <td>${tarefa.descricao}</td>
          <td>${tarefa.data}</td>

          <td>${tarefa.categoria}</td>

          <td>
            <a href="editartarefa?id=${tarefa.id}">Editar</a> |
            <a href="removertarefa?id=${tarefa.id}" onclick="return confirm('Tem certeza que deseja excluir esta tarefa?');">Excluir</a>
          </td>
        </tr>
      </c:forEach>
    </c:when>

    <c:otherwise>
      <tr>
        <td colspan="5" style="text-align: center;">Você ainda não possui tarefas cadastradas.</td>
      </tr>
    </c:otherwise>
  </c:choose>
  </tbody>
</table>

<hr>

<p>
  <a href="editarusuario"><button>Editar Meu Perfil</button></a>

  <c:if test="${sessionScope.usuarioLogado.perfil == 'ADMIN'}">
    <a href="gerenciarcategorias"><button>Gerenciar Categorias (Admin)</button></a>
  </c:if>

  <a href="sair"><button>Logout</button></a>
</p>

</body>
</html>