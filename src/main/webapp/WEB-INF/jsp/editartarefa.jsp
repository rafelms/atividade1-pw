<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Editar Tarefa</title>
</head>
<body>

<h2>Editar Tarefa: ${tarefaEditar.titulo}</h2>

<form action="editartarefa" method="post">
  <input type="hidden" name="id" value="${tarefaEditar.id}">

  <label>Título:
    <input type="text" name="titulo" value="${tarefaEditar.titulo}" required>
  </label><br><br>

  <label>Descrição:
    <input type="text" name="descricao" value="${tarefaEditar.descricao}" required>
  </label><br><br>

  <label>Data:
    <input type="date" name="data" value="${tarefaEditar.data}" required>
  </label><br><br>

  <label>Categoria:
    <select name="categoria" required>
      <c:forEach var="c" items="${listaCategorias}">
        <option value="${c.id}" <c:if test="${c.id == tarefaEditar.idCategoria}">selected</c:if>>
            ${c.nome}
        </option>
      </c:forEach>
    </select>
  </label><br><br>

  <input type="submit" value="Salvar Alterações">
</form>

<br>
<a href="vertarefas"><button>Cancelar</button></a>

</body>
</html>