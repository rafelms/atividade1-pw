<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Nova Tarefa</title>
</head>
<body>

<h2>Cadastrar Nova Tarefa</h2>

<c:if test="${not empty mensagemErro}">
  <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>

<form action="cadastrartarefa" method="post">
  <label>Título: <input type="text" name="titulo" required></label><br><br>
  <label>Descrição: <input type="text" name="descricao" required></label><br><br>
  <label>Data: <input type="date" name="data"></label><br><br>

  <label>Categoria:
    <select name="categoria" required>
      <option value="">-- Selecione uma Categoria --</option>

      <c:forEach var="c" items="${listaCategorias}">
        <option value="${c.id}">${c.nome}</option>
      </c:forEach>

    </select>
  </label><br><br>

  <input type="submit" value="Salvar Tarefa">
</form>

<hr>
<p>
  <a href="vertarefas"><button>Cancelar e Voltar</button></a>
  <a href="editarusuario"><button>Editar Usuario</button></a>
  <a href="sair"><button>Logout</button></a>
</p>

</body>
</html>