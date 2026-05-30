<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Login e Cadastro</title>
</head>
<body>

<c:if test="${not empty mensagemErro}">
  <p style="color: red; font-weight: bold;">${mensagemErro}</p>
</c:if>

<c:if test="${not empty mensagemSucesso}">
  <p style="color: green; font-weight: bold;">${mensagemSucesso}</p>
</c:if>

<c:if test="${empty sessionScope.usuarioLogado}">
  <h2>Cadastrar Usuário</h2>
  <form action="cadastrarusuario" method="post">
    <label>Nome: <input type="text" name="nome" placeholder="Nome" required></label><br><br>
    <label>Login: <input type="text" name="login" placeholder="Login" required></label><br><br>
    <label>Senha: <input type="password" name="senha" placeholder="Senha" required></label><br><br>
    <input type="submit" value="Cadastrar">
  </form>

  <hr>

  <h2>Efetuar Login</h2>
  <form action="logar" method="post">
    <label>Login: <input type="text" name="login" placeholder="Login" required></label><br><br>
    <label>Senha: <input type="password" name="senha" placeholder="Senha" required></label><br><br>
    <input type="submit" value="Logar">
  </form>
</c:if>

<c:if test="${not empty sessionScope.usuarioLogado}">
  <h2>Olá, ${sessionScope.usuarioLogado.nome}!</h2>
  <p>Você já está autenticado no sistema.</p>

  <a href='vertarefas'><button>Ir para Tarefas</button></a>
  <a href='sair'><button>Sair da Sessão (Logout)</button></a>
</c:if>

</body>
</html>