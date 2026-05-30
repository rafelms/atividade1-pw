<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Editar Perfil</title>
</head>
<body>

<h2>Editar Meu Perfil</h2>

<c:if test="${not empty mensagemErro}">
  <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>
<c:if test="${not empty mensagemSucesso}">
  <p style="color: green; font-weight: bold;">${mensagemSucesso}</p>
</c:if>

<form action="editarusuario" method="post">
  <h3>OBS: Seu login (<strong>${sessionScope.usuarioLogado.login}</strong>) permanece o mesmo!</h3>
  <br>

  <label>Novo Nome:
    <input type="text" name="nome" value="${sessionScope.usuarioLogado.nome}" placeholder="Digite o novo nome" required>
  </label>
  <br><br>

  <label>Nova Senha:
    <input type="password" name="senha" placeholder="Digite a nova senha" required>
  </label>
  <br><br>

  <input type="submit" value="Salvar Alterações">
</form>

<br>
<hr>

<p>
  <a href="vertarefas"><button>Voltar para minhas tarefas</button></a>
  <a href="sair"><button>Logout</button></a>
</p>

</body>
</html>